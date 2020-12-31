package com.cmsz.paas.web.build.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.model.entity.BuildRecordEntity;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.model.response.BuildRecordDetail;
import com.cmsz.paas.common.spring.Springs;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.build.dao.BuildDao;
import com.cmsz.paas.web.build.model.BuildRecordInfo;
import com.cmsz.paas.web.constants.Constants;

/**
 * 构建记录状态长连接
 * 
 * @author liaohw
 * @date 2016-4-12
 */
@ServerEndpoint(value = "/websocket/buildStatus", configurator = GetHttpSessionConfigurator.class)
public class BuildStatusWebSocket {

	private static final Logger logger = LoggerFactory
			.getLogger(BuildStatusWebSocket.class);

	private BuildDao buildDao = Springs.getBean("buildDao");

	/** rest接口的buffer */
	private BufferedReader reader = null;

	/** rest接口返回的对象类 */
	private ResponseInfo responseInfo = null;

	/** rest接口返回的代码，如错误码或者中间码 */
	private String retCode = "";

	/** 长连接返回页面的数据 */
	private String retMsg = "";

	/** 页面Session */
	private HttpSession httpSession;

	/** 登录用户名 */
	private String userLoginName;

	@OnMessage(maxMessageSize = 600000)
	public void onMessage(String message, Session session) throws IOException,
			InterruptedException, ParseException {
		logger.info("接收到的字符串：" + message);
		try {
			if (StringUtils.isNotBlank(message)) {
				String[] split = message.split("_");
				String buildId = split[0];
				String buildRecordId = split[1];
				String restUrl = RestUtils.restUrlTransform(
						"queryBuildRecordStatusUrl", buildId, buildRecordId);
				this.queryBuildStatus(restUrl, session);
			} else {
				session.getBasicRemote().sendText("接收的参数为空 ！");
			}
		} catch (Exception e) {
			logger.error("查询构建记录状态长连接错误", e);
			this.onClose();
		} finally {
			session.close();
		}
	}

	/**
	 * 查询构建状态
	 * 
	 * @param restUrl
	 * @param session
	 * @throws IOException
	 * @throws JSONException
	 */
	private void queryBuildStatus(String restUrl, Session session)
			throws IOException, JSONException {
		logger.info("开始调用Rest接口查询构建记录状态:" + restUrl);
		try {
			String currentLine = null;
			reader = buildDao.get(restUrl);
			while ((currentLine = reader.readLine()) != null) {
				responseInfo = JsonUtil.json2ResponseInfoBean(currentLine, BuildRecordDetail.class);
				retCode = responseInfo.getRetCode();
				// 发送数据
				retMsg = sendResult(responseInfo);
				session.getBasicRemote().sendText(retMsg);
				logger.info("查询构建记录状态的rest接口返回码：" + retCode + ", 返回信息：" + retMsg);
				// 出错就断开长连接
				if (!retCode.contains("PAAS-00") && !retCode.equals(Constants.REST_CODE_SUCCESS)) {// 不是PAAS-00开头的都是错误码
					return;
				}
			}
		} catch (Exception ex) {
			logger.info("查询构建记录状态错误", ex);
			responseInfo.setRetCode(Constants.QUERY_BUILD_RECORD_STATUS_ERROR);
			responseInfo.setMsg(ex.getMessage());
			retMsg = sendResult(responseInfo);
			session.getBasicRemote().sendText(retMsg);// 将异常显示到页面
		} finally {
			reader.close();
		}
	}

	/*
	 * 通过websocket发送返回消息
	 */
	private String sendResult(ResponseInfo responseInfo) throws JSONException {
		String retCode = responseInfo.getRetCode();// 错误码
		String retCodeDes = PropertiesUtil.getValue(retCode);// 错误码对应的描述
		String msg = responseInfo.getMsg();
		StringBuffer strBuf = new StringBuffer(); // 用于拼接
		String resultMessage = ""; // 返回结果
		// 中间码或者错误码
		if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
			// 中间码
			if (retCode.contains("PAAS-00")) {
				if (responseInfo.getData() != null) {
					BuildRecordDetail buildRecordDetail = (BuildRecordDetail) responseInfo.getData();
					if (buildRecordDetail != null && buildRecordDetail.getBuildRecord() != null) {
						BuildRecordEntity buildRecordEntity = buildRecordDetail.getBuildRecord();
						//只把需要的数据转换
						BuildRecordInfo buildRecordInfo = new BuildRecordInfo();
						buildRecordInfo.setStatus(buildRecordEntity.getStatus()+"");
						buildRecordInfo.setImageVersion(buildRecordEntity.getVersion());
						buildRecordInfo.setSvnVersion(buildRecordEntity.getRevision());
						if(buildRecordEntity.getEndTime().getTime() != 0){
							buildRecordInfo.setEndTime(DateUtil.tranformDate(buildRecordEntity.getEndTime().toString()));
						}else{
							buildRecordInfo.setEndTime("-");
						}
						resultMessage = JackJson.fromObjectToJson(buildRecordInfo);
					}
				}
			} else {// 错误码
				strBuf.append("[");
				strBuf.append(retCode);
				strBuf.append("] ");
				strBuf.append(retCodeDes);
				strBuf.append("<br/>");
				strBuf.append(msg);
				resultMessage = strBuf.toString();
				logger.info("查询构建记录状态错误", resultMessage);
			}
		} else {// 成功
			resultMessage = "查询构建记录状态完成！";
			logger.info(resultMessage);
		}

		JSONObject obj = new JSONObject();
		obj.put("resultCode", retCode);
		obj.put("resultMessage", resultMessage);

		return obj.toString();
	}

	/**
	 * On open.
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
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
		System.out.println("Websocket Connection closed by " + userLoginName);
	}

}
