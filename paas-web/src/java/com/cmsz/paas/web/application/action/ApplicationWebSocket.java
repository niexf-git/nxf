package com.cmsz.paas.web.application.action;

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

import org.jfree.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.model.response.InstInfo;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.log.OperationLogUtil;
import com.cmsz.paas.web.base.util.ParamsValidateUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;

@WebListener
@ServerEndpoint(value = "/websocket/application", configurator = GetHttpSessionConfigurator.class)
public class ApplicationWebSocket {

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
	 *            页面传入参数，由_分隔，前者表示要处理的数据，后者表示要调用的方法标记，如appid_start-appService
	 * @param session
	 *            the session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		Log.info("应用迁移长连接收到的请求消息：" + message);
		String receivedMsg ="";
		String operation = "";
		if(message.indexOf("|") > -1) {
			receivedMsg = message.split("\\|")[0];
			operation = message.split("\\|")[1];
		}else {
			receivedMsg = message.split("%7C")[0];
			operation = message.split("%7C")[1];
		}
		
		String restUrl = "";
		String errCode = "";

		try {
			if (ParamsValidateUtil.isIncludeWord(receivedMsg)) {
				String errorMsg = ParamsValidateUtil.sendFailResult();
				session.getBasicRemote().sendText(errorMsg);
				return;
			}
			if ("migrate-application".equals(operation)) {
				restUrl = RestUtils.restUrlTransform("migrateApplicationUrl", receivedMsg);
				errCode = Constants.MIGRATE_APPLICATION_ERROR;
				operationAppService(restUrl,errCode,session,"应用迁移",receivedMsg);
			}else if("disasterRecovery".equals(operation)){
				if(receivedMsg.equals("true")){
					restUrl = RestUtils.restUrlTransform("disasterRecoveryUrl",0,true);
				}else{
					restUrl = RestUtils.restUrlTransform("disasterRecoveryUrl",receivedMsg,false);
				}
				errCode = Constants.DISASTER_RECOVERY;
				operationAppService(restUrl,errCode,session,"容灾",receivedMsg);
			}else if("unDisasterRecovery".equals(operation)){
				restUrl = RestUtils.restUrlTransform("unDisasterRecoveryUrl", receivedMsg);
				errCode = Constants.UN_DISASTER_RECOVERY;
				operationAppService(restUrl,errCode,session,"容灾恢复",receivedMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void operationAppService(String restUrl, String errCode,
			Session session, String operation, Object obj)
			throws IOException, JSONException {
		logger.debug("开始调用操作灰度服务的rest接口：" + restUrl);

		String operUrl = session.getRequestURI().toString();

		try {
			reader = restCliet.update(restUrl);
			while ((sCurrentLine = reader.readLine()) != null) {
				responseInfo = JsonUtil.json2ResponseInfoBean(sCurrentLine,InstInfo.class);

				retCode = responseInfo.getRetCode();
				msg = responseInfo.getMsg();
				logger.debug("调用操作应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);

				retMsg = sendResult(responseInfo);
				session.getBasicRemote().sendText(retMsg);

				// 记录审计日志
				if (retCode.equals(Constants.REST_CODE_SUCCESS) || retCode.equals(Constants.REST_CODE_COMPLETE) || retCode.equals("PAAS-20314")) {
					OperationLogUtil.insertOperationLog(userLoginName, operUrl,
							"应用管理", operation, "1", String.valueOf(obj),"",primary_user);

				} else if (!retCode.contains("PAAS-00")
						&& !retCode.equals(Constants.REST_CODE_SUCCESS)) {// 不是PAAS-00开头的都是错误码
					OperationLogUtil.insertOperationLog(userLoginName, operUrl,
							"应用管理", operation, "0", String.valueOf(obj),retMsg,primary_user);
					return;
				}
			}
			
		} catch (Exception ex) {
			responseInfo.setRetCode(errCode);
			responseInfo.setMsg(ex.getMessage());
			retMsg = sendResult(responseInfo);
			session.getBasicRemote().sendText(retMsg);// 将异常显示到页面
			// 记录审计日志
			OperationLogUtil.insertOperationLog(userLoginName, operUrl,
					"应用管理", operation, "0", String.valueOf(obj),ex.toString(),primary_user);
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
		String retMsg = "";
		InstInfo instInfo = new InstInfo();
		StringBuffer strBuf = new StringBuffer();

		if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
			if (retCode.contains("PAAS-00")) {// 中间码
				if (null != responseInfo.getData()) {// 返回中间码附带数据
					instInfo = (InstInfo) responseInfo.getData();

					if (null != instInfo) {// 操作应用，如启动、停止、重启应用服务
						strBuf.append(retCodeDes);
						strBuf.append("，");
						strBuf.append("待部署实例数目:");
						strBuf.append(instInfo.getUnassignedNum());
						strBuf.append("， ");
						strBuf.append("正在启动实例数目:");
						strBuf.append(instInfo.getWaitingNum());
						strBuf.append("， ");
						strBuf.append("运行的实例数目:");
						strBuf.append(instInfo.getRunningNum());
						strBuf.append("， ");
						strBuf.append("退出的实例数目:");
						strBuf.append(instInfo.getTerminationNum());
						strBuf.append("， ");
						strBuf.append("未知状态的实例数目:");
						strBuf.append(instInfo.getUnknownNum());
						retMsg = strBuf.toString();

					}
				} else {// 中间码，但data返回空
					retMsg = retCodeDes;
				}

			} else if(retCode.equals(Constants.REST_CODE_COMPLETE)){
				strBuf.append("操作完成！");
				strBuf.append("<br/>");
				strBuf.append(errMsg);// 特殊处理，如果是启动、停止和重启应用服务，会在最后一步返回实例的具体状态
				
				retMsg = strBuf.toString();
			} else if(retCode.equals("PAAS-20314")){
				strBuf.append(errMsg);// 特殊处理，如果是启动、停止和重启应用服务，会在最后一步返回实例的具体状态
				
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
			strBuf.append("操作成功！");
			strBuf.append("<br/>");
			strBuf.append(errMsg);// 特殊处理，如果是启动、停止和重启应用服务，会在最后一步返回实例的具体状态

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
