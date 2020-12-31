package com.cmsz.paas.web.monitoroperation.action;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.jfree.util.Log;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.model.monitor.response.ResponseInfo;
import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.application.action.ApplicationWebSocket;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.log.OperationLogUtil;
import com.cmsz.paas.web.base.util.ParamsValidateUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;

@WebListener
@ServerEndpoint(value = "/websocket/host", configurator = GetHttpSessionConfigurator.class)
public class HostWebSocket {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationWebSocket.class);

	/** rest接口返回的对象类 */
	ResponseInfo responseInfo = null;

	/** rest接口buffer中的每行数据 */
	String sCurrentLine = "";

	/** rest接口的buffer */
	BufferedReader reader = null;

	/** rest接口基类对象类 */
	ResponseInfoRestDaoImpl restCliet = new ResponseInfoRestDaoImpl();

	/** rest接口返回的代码，如错误码或者中间码 */
	String retCode = "";

	/** rest接口返回的代码描述 */
	String msg = "";

	/** 长连接返回页面的数据 */
	String retMsg = "";

	private String userLoginName;

	private Session session;

	private HttpSession httpSession;
	
	private Object primary_user;


	public Object getPrimary_user() {
		return primary_user;
	}

	public void setPrimary_user(Object primary_user) {
		this.primary_user = primary_user;
	}

	/**
	 * On message.
	 * 
	 * @param message
	 *            页面传入参数
	 * @param session
	 *            the session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		Log.info("删除集群下所有主机长连接收到的请求消息：" + message);
		String clusterId = message;
		String restUrl = "";
		String errCode = "";

		try {
			if (ParamsValidateUtil.isIncludeWord(clusterId)) {
				String errorMsg = ParamsValidateUtil.sendFailResult();
				session.getBasicRemote().sendText(errorMsg);
				return;
			}

			restUrl = RestUtils.monitorOperationRestUrl("deleteAllHostUrl",
					clusterId);
			errCode = Constants.DELETE_ALLHOST_ERROR;
			deleteAllHost(restUrl, errCode, session, "删除主机", clusterId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteAllHost(String restUrl, String errCode, Session session,
			String operation, Object obj) throws IOException, JSONException {
		logger.debug("开始调用删除集群下所有主机的rest接口：" + restUrl);

		String operUrl = session.getRequestURI().toString();

		try {
			reader = restCliet.delete(restUrl);
			while ((sCurrentLine = reader.readLine()) != null) {
				JSONObject jsonObject = JSONObject.fromObject(sCurrentLine);
				ResponseInfo responseInfo = (ResponseInfo) JSONObject.toBean(
						jsonObject, ResponseInfo.class);
				retCode = responseInfo.getRetCode();
				msg = responseInfo.getMsg();
				logger.debug("调用操作应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);

				retMsg = sendResult(responseInfo);
				session.getBasicRemote().sendText(retMsg);
			}
			// 记录审计日志
			if (retCode.equals(Constants.REST_CODE_SUCCESS)
					|| retCode.equals(Constants.REST_CODE_COMPLETE)) {
				OperationLogUtil.insertOperationLog(userLoginName, operUrl,
						"监控运维", operation, "1", String.valueOf(obj), "",primary_user);
			} else if (!retCode.contains("PAAS-00")
					&& !retCode.equals(Constants.REST_CODE_SUCCESS)) {
				// 不是PAAS-00开头的都是错误码
				OperationLogUtil.insertOperationLog(userLoginName, operUrl,
						"监控运维", operation, "0", String.valueOf(obj), retMsg,primary_user);
				return;
			}

		} catch (Exception ex) {
			responseInfo.setRetCode(errCode);
			responseInfo.setMsg(ex.getMessage());
			retMsg = sendResult(responseInfo);
			session.getBasicRemote().sendText(retMsg);// 将异常显示到页面
			// 记录审计日志
			OperationLogUtil.insertOperationLog(userLoginName, operUrl, "监控运维",
					operation, "0", String.valueOf(obj), ex.toString(),primary_user);
			this.onClose();

		} finally {
			reader.close();
			this.onClose();
		}
	}

	/*
	 * 通过websocket发送返回消息
	 */
	private String sendResult(ResponseInfo responseInfo) throws JSONException {
		String retCode = responseInfo.getRetCode();
		String retCodeDes = PropertiesUtil.getValue(retCode);
		String errMsg = responseInfo.getMsg();
		String data="";
		if (responseInfo.getData()!=null) {
			data=responseInfo.getData().toString();
		}
		String retMsg = "";
		StringBuffer strBuf = new StringBuffer();

		if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
			if (retCode.contains("PAAS-00")) {// 中间码
				strBuf.append(retCodeDes);
				strBuf.append("<br/>");
				strBuf.append(errMsg);

				retMsg = strBuf.toString();

			} else {// 错误码，包括控制中心的paas-20和前台的paas-10错误码
					// retMsg = "操作失败！["+retCode+"]" +retCodeDes+"<br/>"+msg;
					// strBuf.append("操作失败！");
				strBuf.append("[");
				strBuf.append(retCode);
				strBuf.append("] ");
				strBuf.append(retCodeDes);
				strBuf.append("<br/>");
				strBuf.append(errMsg);

				retMsg = strBuf.toString();
			}
		} else {
			if (data!=null&&!data.isEmpty()) {
				strBuf.append("删除失败的节点："+data);
			}else {
				strBuf.append("操作成功!");
				strBuf.append("<br/>");
				strBuf.append(errMsg);
			}

			retMsg = strBuf.toString();
		}

		// return retMsg;
		JSONObject obj = new JSONObject();
		obj.put("resultCode", retCode);
		obj.put("resultMessage", retMsg);

		return obj.toString();
	}

	/**
	 * On open.
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		// System.out.println("Websocket Client connected");

		this.session = session;
		this.httpSession = (HttpSession) config.getUserProperties().get(
				HttpSession.class.getName());
		this.userLoginName = (String) httpSession.getAttribute("loginName");
		this.primary_user=httpSession.getAttribute("masterAccount");
		System.out.println("Websocket Client connected by " + userLoginName);
	}

	/**
	 * On close.
	 */
	@OnClose
	public void onClose() {
		System.out.println("Websocket Connection closed by ");
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

}
