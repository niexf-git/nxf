package com.cmsz.paas.web.appservicegray.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;
import com.cmsz.paas.common.model.controller.entity.AppServiceVersionEntity;
import com.cmsz.paas.common.model.controller.entity.ConfigFileEntity;
import com.cmsz.paas.common.model.controller.entity.EnvConfigEntity;
import com.cmsz.paas.common.model.response.InstInfo;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.appservicegray.model.GrayEntity;
import com.cmsz.paas.web.appservicegray.service.GrayReleaseService;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.log.OperationLogUtil;
import com.cmsz.paas.web.base.util.ParamsValidateUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebListener
@ServerEndpoint(value = "/websocket/grayRelease", configurator = GetHttpSessionConfigurator.class)
public class GrayReleaseWebSocket {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(GrayReleaseWebSocket.class);
	
	@Autowired
	private GrayReleaseService grayReleaseService;

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
	private final String TEMP_FILE_PATH = "tempfile/";
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
		Log.info("灰度版本服务长连接收到的请求消息：" + message);
		System.out.println(message+":!!"+message.indexOf("|"));
		String receivedMsg = "";
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
			if ("create-grayRelease".equals(operation)) {//创建灰度版本
				GrayEntity grayEntity = null;
				String jsonString = doJsonString(receivedMsg);// 对环境变量和ipaas服务是单个时特殊处理
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				grayEntity = gson.fromJson(
						JsonUtil.parseTimeObjectToJson(jsonString),
						GrayEntity.class);
				restUrl = RestUtils.restUrlTransform("createGrayVersionUrl",
						grayEntity.getAppId());
				errCode = Constants.CREATE_GRAYRELEASE_ERROR;
				operationAppService(restUrl, errCode, session, "创建",
						grayEntity);
			} else if ("update-grayRelease".equals(operation)) {//修改灰度版本
				GrayEntity grayEntity = null;
				String jsonString = doJsonString(receivedMsg);// 对环境变量和ipaas服务是单个时特殊处理
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				grayEntity = gson.fromJson(
						JsonUtil.parseTimeObjectToJson(jsonString),
						GrayEntity.class);
				restUrl = RestUtils.restUrlTransform("createGrayVersionUrl",
						grayEntity.getAppId());
				errCode = Constants.CREATE_GRAYRELEASE_ERROR;
				operationAppService(restUrl, errCode, session, "修改",
						grayEntity);
			}else if ("start-stop-grayRelease".equals(operation)) {//启动\停止灰度版本
				String[] receivedMsgs = receivedMsg.split(",");
				GrayEntity grayEntity = new GrayEntity();
				grayEntity.setAppId(receivedMsgs[0]);
				String operationStr = "";
				String type = "";
				if(receivedMsgs[1].equals("1")){
					errCode = Constants.START_GRAYRELEASE_ERROR;
					operationStr = "启动";
					type = "start";
				}else{
					errCode = Constants.STOP_GRAYRELEASE_ERROR;
					operationStr = "停止";
					type = "stop";
				}
				restUrl = RestUtils.restUrlTransform("startAndStopGrayVersionUrl",
						grayEntity.getAppId(), type);
				operationAppService(restUrl, errCode, session, operationStr, grayEntity);
			}else if ("delete-grayRelease".equals(operation)) {//删除灰度版本
				GrayEntity grayEntity = new GrayEntity();
				grayEntity.setAppId(receivedMsg.split(",")[0]);
				restUrl = RestUtils.restUrlTransform("deleteGrayVersionUrl",
						grayEntity.getAppId());
				errCode = Constants.DELETE_GRAYRELEASE_ERROR;
				operationAppService(restUrl, errCode, session, "删除", grayEntity);
			}else if ("upgrade-grayRelease".equals(operation)) {//部署\升级灰度版本
				GrayEntity grayEntity = new GrayEntity();
				grayEntity.setAppId(receivedMsg.split(",")[0]);
				restUrl = RestUtils.restUrlTransform("upgradeGrayVersionUrl",
						grayEntity.getAppId());
				errCode = Constants.UPGRADE_GRAYRELEASE_ERROR;
				operationAppService(restUrl, errCode, session, "升级", grayEntity);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 转换ipaas集合
	 * 
	 * @param appServiceId
	 *            应用服务ID
	 * @param appConfKey
	 *            配置keys
	 * @param appConfValue
	 *            配置values
	 * @return the app config list
	 */
	private List<EnvConfigEntity> getEnvConfigList(
			String[] appConfKey, String[] appConfValue) {
		logger.info("开始执行转换配置信息...");
		List<EnvConfigEntity> configs = new ArrayList<EnvConfigEntity>();
		EnvConfigEntity conf;
		if (appConfKey != null && appConfKey.length > 0 && appConfValue != null
				&& appConfValue.length > 0) {
			for (int i = 0; i < appConfKey.length; i++) {
				if (StringUtils.isNotBlank(appConfKey[i])) {
					conf = new EnvConfigEntity();
					conf.setConfigKey(appConfKey[i].trim());
					conf.setConfigValue(appConfValue[i].trim());
					configs.add(conf);
				}
			}
		}
		logger.info("执行转换配置信息成功！");
		return configs;
	}
	
	private String receivedMsg(String[] str){
		if(str.length>1){
			return str[1];
		} 
		return "";
	}
	
	/**
	 * 环境变量配置转换，包括应用服务和ipaas服务的
	 * 
	 * @param listconfig
	 *            配置集合
	 * @return
	 */
	private List<EnvConfigEntity> genConfigList(List<?> listconfig) {
		logger.debug("开始转换环境变量信息...");
		List<EnvConfigEntity> list = null;
		if (listconfig != null && listconfig.size() != 0) {
			EnvConfigEntity envConfig;
			list = new ArrayList<EnvConfigEntity>();
			if (listconfig.get(0) instanceof EnvConfigEntity) {// 应用服务的环境变量
				for (Object e : listconfig) {
					envConfig = new EnvConfigEntity();

					envConfig
							.setConfigKey(((EnvConfigEntity) e).getConfigKey());
					envConfig.setConfigValue(((EnvConfigEntity) e)
							.getConfigValue());
					list.add(envConfig);
				}
			}
		}
		logger.debug("转换环境变量信息成功！");
		return list;
	}
	
	
	
	private AppServiceEntity genAppEntity(GrayEntity entity){
		AppServiceEntity appServiceEntity = new AppServiceEntity();
		appServiceEntity.setTotalInstanceNum(entity.getTotalInstanceNum());//总实例数
		appServiceEntity.setGrayInstanceNum(entity.getGrayInstanceNum());//灰度实例数

		List<AppServiceVersionEntity> versions = new ArrayList<AppServiceVersionEntity>();
		AppServiceVersionEntity appServiceVersionEntity = new AppServiceVersionEntity();
		appServiceVersionEntity.setAppServiceId(entity.getAppId());//应用id
		appServiceVersionEntity.setRunningVersion(entity.getImageVersionId());//运行版本
		appServiceVersionEntity.setType(entity.getType());//类型 1-正式 2-灰度
		appServiceVersionEntity.setImageType(entity.getImageType());//镜像类型
		appServiceVersionEntity.setImageId(entity.getImageId());//镜像ID
		appServiceVersionEntity.setLogDir(entity.getLogDir());//日志路径
		
		//环境变量
		appServiceVersionEntity.setEnvs(genConfigList(entity.getEnvConfig()));
		
		//配置文件信息
		ConfigFileEntity configFile = new ConfigFileEntity();
		configFile.setConfigDir(entity.getConfigFilePath());
		configFile.setContent(entity.getConfigFileInfo());
		
		appServiceVersionEntity.setConfigFile(configFile);
		versions.add(appServiceVersionEntity);
		appServiceEntity.setVersions(versions);
		return appServiceEntity;
	}
	
	
	private void operationAppService(String restUrl, String errCode,
			Session session, String operation, GrayEntity grayEntity)
			throws IOException, JSONException {
		logger.debug("开始调用操作灰度服务的rest接口：" + restUrl);

		String operUrl = session.getRequestURI().toString();
		if(grayEntity.getAppConfKey()!=null){
			grayEntity.setEnvConfig(getEnvConfigList(grayEntity.getAppConfKey(),grayEntity.getAppConfValue()));
		}

		try {
			if("创建".equals(operation)){
				reader = restCliet.create(restUrl, genAppEntity(grayEntity));
			}else if("修改".equals(operation)){
				reader = restCliet.update(restUrl, genAppEntity(grayEntity));
			}else if("删除".equals(operation)){
				reader = restCliet.delete(restUrl);
			}else{
				reader = restCliet.update(restUrl);
			}
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
							"灰度版本", operation, "1", String.valueOf(grayEntity.getAppId()),"",primary_user);

				} else if (!retCode.contains("PAAS-00")
						&& !retCode.equals(Constants.REST_CODE_SUCCESS)) {// 不是PAAS-00开头的都是错误码
					OperationLogUtil.insertOperationLog(userLoginName, operUrl,
							"灰度版本", operation, "0", String.valueOf(grayEntity.getAppId()),retMsg,primary_user);
					return;
				}
			}
			/*if("停止灰度版本".equals(operation) || "启动灰度版本".equals(operation) || "删除灰度版本".equals(operation) || "升级灰度版本".equals(operation)){
				responseInfo = new ResponseInfo();
				responseInfo.setMsg(operation);
				responseInfo.setRetCode("0");
				retMsg = sendResult(responseInfo);
				session.getBasicRemote().sendText(retMsg);
			}*/
			
		} catch (Exception ex) {
			responseInfo.setRetCode(errCode);
			responseInfo.setMsg(ex.getMessage());
			retMsg = sendResult(responseInfo);
			session.getBasicRemote().sendText(retMsg);// 将异常显示到页面
			// 记录审计日志
			OperationLogUtil.insertOperationLog(userLoginName, operUrl,
					"灰度版本", operation, "0", String.valueOf(grayEntity.getAppId()),ex.toString(),primary_user);
			this.onClose();

		} finally {
			reader.close();
			this.onClose();
		}
	}
	
	/**
	 * json 字符串处理
	 * 
	 * @param srcJsonStr
	 * @return
	 */
	private String doJsonString(String srcJsonStr) {
		if (!StringUtils.isNotBlank(srcJsonStr)) {
			return srcJsonStr;
		}
		// 特殊处理：当页面只有一个环境变量时
		if (!srcJsonStr.contains("\"appConfKey\":[")) {
			String[] arrStr = srcJsonStr.split(",");
			for (int i = 0; i < arrStr.length; i++) {
				String str = arrStr[i];
				// 第一个多一个{
				if (i == 0) {
					str = str.substring(1, str.length());
				}
				if (str.startsWith("\"appConfKey\"")
						|| str.startsWith("\"appConfValue\"")) {
					String[] arr = arrStr[i].split(":\"");
					// 如果是最后多一个}
					if (i == arrStr.length - 1) {
						String s = arr[1].substring(0, arr[1].length() - 1);
						srcJsonStr = srcJsonStr.replace(arrStr[i], arr[0]
								+ ":[\"" + s + "]}");
					} else {
						srcJsonStr = srcJsonStr.replace(arrStr[i], arr[0]
								+ ":[\"" + arr[1] + "]");
					}
				}
			}
		}
		// 特殊处理：当页面只有一个ipaas服务时
				if (!srcJsonStr.contains("\"ipaasName\":[")) {
					String[] arrStr = srcJsonStr.split(",");
					for (int i = 0; i < arrStr.length; i++) {
						String str = arrStr[i];
						// 第一个多一个{
						if (i == 0) {
							str = str.substring(1, str.length());
						}
						if (str.startsWith("\"ipaasName\"")
								|| str.startsWith("\"ipaasBindStr\"")) {
							String[] arr = arrStr[i].split(":");
							// 如果是最后多一个}
							if (i == arrStr.length - 1) {
								String s = arr[1].substring(0, arr[1].length() - 1);
								srcJsonStr = srcJsonStr.replace(arrStr[i], arr[0]
										+ ":[" + s + "]}");
							} else {
								srcJsonStr = srcJsonStr.replace(arrStr[i], arr[0]
										+ ":[" + arr[1] + "]");
							}
						}
					}
				}

				return srcJsonStr;
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

	public String getTEMP_FILE_PATH() {
		return TEMP_FILE_PATH;
	}
	
	
}
