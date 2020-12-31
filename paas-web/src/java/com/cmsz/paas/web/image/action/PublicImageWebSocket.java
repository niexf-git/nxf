package com.cmsz.paas.web.image.action;

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

import com.cmsz.paas.common.model.response.PublishIntermedMsg;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;

@WebListener
@ServerEndpoint(value = "/websocket/publicImage", configurator = GetHttpSessionConfigurator.class)
public class PublicImageWebSocket {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(PublicImageWebSocket.class);

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

	/** 环境变量配置文件存放路径 */
//	private final String TEMP_FILE_PATH = "tempfile/";

	@OnMessage
	public void onMessage(String message, Session session) {
		Log.info("应用服务长连接收到的请求消息：" + message);
		String publicImageId = "";
		String versionId = "";
//		String dataCenterIds = message.split("\\|")[2];
		if(message.indexOf("|") > -1) {
			publicImageId = message.split("\\|")[0];
			versionId = message.split("\\|")[1];
		}else {
			publicImageId = message.split("%7C")[0];
			versionId = message.split("%7C")[1];
		}
		BufferedReader reader = null;
		ResponseInfo responseInfo = null;
		try {
			//组装访问控制中心的链接，以及参数
//			String url = RestUtils.restUrlTransform("publicImagePublish2Product", publicImageId, versionId, dataCenterIds);
			String url = RestUtils.restUrlTransform("publicImagePublish2Product", publicImageId, versionId);
			logger.debug("发布生产rest接口：" + url);
			
			String currentLine = null;
			reader = restCliet.update(url);
			while((currentLine = reader.readLine()) != null){
				//长连接
				//中间消息data用common下的实体解析
				//paas-common\src\java\com\cmsz\paas\common\model\responsev3\PublishIntermedMsg.java
				responseInfo = JsonUtil.json2ResponseInfoBean(currentLine, PublishIntermedMsg.class);
				retCode = responseInfo.getRetCode();
				msg = responseInfo.getMsg();
				
				// 发送数据
				retMsg = sendResult(responseInfo);
				session.getBasicRemote().sendText(retMsg);
				
				logger.info("发布生产rest接口返回码：" + retCode + ", 返回信息：" + msg);
			}
			
			if (!retCode.contains("PAAS-00") && !retCode.equals(Constants.REST_CODE_SUCCESS)){//不是PAAS-00开头的都是错误码
				throw new PaasWebException(retCode, msg);
			}
			
		} catch (Exception ex) {
			logger.error("发布生产异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.PUBLIC_IMAGE_PUBLISHED2PRODUCT_ERROR, ex.getMessage());
			}
		} finally {
			try {
				if(reader != null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new PaasWebException(Constants.PUBLISHED2PRODUCT_WEB_SOCKET_STREAM_CLOSE_ERROR, e.getMessage());
			}
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
//		InstInfo instInfo = new InstInfo();
		StringBuffer strBuf = new StringBuffer();

		if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
			if (retCode.contains("PAAS-00")) {// 中间码
				
				strBuf.append(retCodeDes);
				strBuf.append("<br/>");
				strBuf.append(errMsg);
				
				retMsg = strBuf.toString();
				
//				if (null != responseInfo.getData()) {// 返回中间码附带数据
//					instInfo = (InstInfo) responseInfo.getData();
//
//					if (null != instInfo) {// 操作应用，如启动、停止、重启应用服务
//						strBuf.append(retCodeDes);
//						strBuf.append("，");
//						strBuf.append("待部署实例数目:");
//						strBuf.append(instInfo.getUnassignedNum());
//						strBuf.append("， ");
//						strBuf.append("正在启动实例数目:");
//						strBuf.append(instInfo.getWaitingNum());
//						strBuf.append("， ");
//						strBuf.append("运行的实例数目:");
//						strBuf.append(instInfo.getRunningNum());
//						strBuf.append("， ");
//						strBuf.append("退出的实例数目:");
//						strBuf.append(instInfo.getTerminationNum());
//						strBuf.append("， ");
//						strBuf.append("未知状态的实例数目:");
//						strBuf.append(instInfo.getUnknownNum());
//					}
//				} else {// 中间码，但data返回空
//					retMsg = retCodeDes;
//				}

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
		System.out.println("Websocket Client connected by " + userLoginName);
	}

	/**
	 * On close.
	 */
	@OnClose
	public void onClose() {
		System.out.println("Websocket Connection closed by ");
	}
}
