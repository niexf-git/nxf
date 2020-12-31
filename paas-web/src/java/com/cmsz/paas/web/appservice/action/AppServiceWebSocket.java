package com.cmsz.paas.web.appservice.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;
import com.cmsz.paas.common.model.response.InstInfo;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.common.spring.Springs;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.appservice.model.Ipaas;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.log.OperationLogUtil;
import com.cmsz.paas.web.base.util.OrderedProperties;
import com.cmsz.paas.web.base.util.ParamsValidateUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebListener
@ServerEndpoint(value = "/websocket/appService", configurator = GetHttpSessionConfigurator.class)
public class AppServiceWebSocket {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(AppServiceWebSocket.class);

	private AppService appService;

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
	@OnMessage(maxMessageSize = 600000)
	public void onMessage(String message, Session session) {
		Log.info("应用服务长连接收到的请求消息：" + message);
		System.out.println(message);
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
			/*if ("start-appService".equals(operation)) {// 启动应用服务
				restUrl = RestUtils.restUrlTransform("operationAppServiceUrl",
						receivedMsg, Constant.START);
				errCode = Constants.START_APPSERVICE_ERROR;
				operationAppService(restUrl, errCode, session, "启动应用服务",
						receivedMsg);
			} else*/ if ("restart-appService".equals(operation)) {
				restUrl = RestUtils.restUrlTransform("operationAppServiceUrl",
						receivedMsg, Constant.RESTART);
				errCode = Constants.RESTART_APPSERVICE_ERROR;
				operationAppService(restUrl, errCode, session, "重启",
						receivedMsg);
			} else if ("allrestart-appService".equals(operation)) {
				restUrl = RestUtils.restUrlTransform("operationAppServiceUrl",
						receivedMsg, Constant.ALL_RESTART);
				errCode = Constants.ALLRESTART_APPSERVICE_ERROR;
				operationAppService(restUrl, errCode, session, "一键重启",
						receivedMsg);
			} 
//			else if ("selfrestart-appService".equals(operation)) {
//				restUrl = RestUtils.restUrlTransform("operationAppServiceUrl",
//						receivedMsg, Constant.SELF_RESTART);
//				errCode = Constants.SELFRESTART_APPSERVICE_ERROR;
//				operationAppService(restUrl, errCode, session, "重启自身",
//						receivedMsg);
//			} 
			else if ("stop-appService".equals(operation)) {
				restUrl = RestUtils.restUrlTransform("operationAppServiceUrl",
						receivedMsg, Constant.STOP);
				errCode = Constants.STOP_APPSERVICE_ERROR;
				operationAppService(restUrl, errCode, session, "停止",
						receivedMsg);
			} else if (operation.equals("config-app")) {// 配置应用

				String jsonString = doJsonString(receivedMsg);// 对环境变量和ipaas服务是单个时特殊处理
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				appService = gson.fromJson(
						JsonUtil.parseTimeObjectToJson(jsonString),
						AppService.class);

				// Object object = ParamsValidateUtil.doMyFilter(appService);
				// if (object instanceof AppVersion) {
				// appService = (AppService) object;
				// } else {
				// session.getBasicRemote().sendText(String.valueOf(object));
				// return;
				// }

				restUrl = RestUtils.restUrlTransform("modifyOneApplication",
						appService.getId());
				errCode = Constants.MODIFY_APPSERVICE_CONF_ERROR;

				configAppService(restUrl, errCode, appService, session);
			}else{
				String errorMsg = ParamsValidateUtil.sendFailResult();
				session.getBasicRemote().sendText(errorMsg);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void operationAppService(String restUrl, String errCode,
			Session session, String operation, String appServiceId)
			throws IOException, JSONException {
		logger.debug("开始调用操作应用服务的rest接口：" + restUrl);

		String operUrl = session.getRequestURI().toString();

		try {
			reader = restCliet.update(restUrl);
			while ((sCurrentLine = reader.readLine()) != null) {
				responseInfo = JsonUtil.json2ResponseInfoBean(sCurrentLine,
						InstInfo.class);

				retCode = responseInfo.getRetCode();
				msg = responseInfo.getMsg();
				logger.debug("调用操作应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);

				retMsg = sendResult(responseInfo);
				session.getBasicRemote().sendText(retMsg);

				// 记录审计日志
				if (retCode.equals(Constants.REST_CODE_SUCCESS) || retCode.equals(Constants.REST_CODE_COMPLETE) || retCode.equals("PAAS-20314")) {
					OperationLogUtil.insertOperationLog(userLoginName, operUrl,
							"服务管理", operation, "1", appServiceId,"",primary_user);

				} else if (!retCode.contains("PAAS-00")
						&& !retCode.equals(Constants.REST_CODE_SUCCESS)) {// 不是PAAS-00开头的都是错误码
					OperationLogUtil.insertOperationLog(userLoginName, operUrl,
							"服务管理", operation, "0", appServiceId,retMsg,primary_user);
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
					"服务管理", operation, "0", appServiceId,ex.toString(),primary_user);

			this.onClose();

		} finally {
			reader.close();
			this.onClose();
		}
	}

	private void configAppService(String restUrl, String errCode, AppService a,
			Session session) throws IOException, JSONException {
		logger.debug("开始调用配置应用服务的rest接口：" + restUrl);

		// String exportEnvsFileName = (String) httpSession
		// .getAttribute("exportEnvsFileName");

		// 设置环境变量
		a.setEnvConfig(getEnvConfigList(a.getId(), a.getAppConfKey(),
				a.getAppConfValue()));
		// 设置ipaas
		a.setIpaas(getIpaasList(a.getId(), a.getIpaasName(),
				a.getIpaasBindStr()));

		AppServiceEntity e = ((ApplicationService) Springs
				.getBean("appServiceService")).genAppServiceEntity(a);
		String operUrl = session.getRequestURI().toString();

		try {
			// 1. 配置应用服务
			reader = restCliet.update(restUrl, e);
			while ((sCurrentLine = reader.readLine()) != null) {
				responseInfo = JsonUtil.json2ResponseInfoBean(sCurrentLine,
						InstInfo.class);

				retCode = responseInfo.getRetCode();
				msg = responseInfo.getMsg();
				logger.debug("调用配置应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);

				retMsg = sendResult(responseInfo);
				session.getBasicRemote().sendText(retMsg);

				// 记录审计日志
				if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
					OperationLogUtil.insertOperationLog(userLoginName, operUrl,
							"服务管理", "修改", "1", a.getId(),"",primary_user);

				} else if (!retCode.contains("PAAS-00")
						&& !retCode.equals(Constants.REST_CODE_SUCCESS)) {// 不是PAAS-00开头的都是错误码
					OperationLogUtil.insertOperationLog(userLoginName, operUrl,
							"服务管理", "修改", "0", a.getId(),retMsg,primary_user);
					return;
				}
			}

			// 2. 生成一个环境变量配置文件，后缀.properties
			deleteFile(a.getEnvsConfFileName());// 先删除之前的旧文件
			genEnvProperties(a);

		} catch (Exception ex) {
			responseInfo.setRetCode(errCode);
			responseInfo.setMsg(ex.getMessage());
			retMsg = sendResult(responseInfo);

			session.getBasicRemote().sendText(retMsg);// 将异常显示到页面

			// 记录审计日志
			OperationLogUtil.insertOperationLog(userLoginName, operUrl,
					"服务管理", "修改", "0", a.getId(),ex.toString(),primary_user);

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
	 * 生成一个环境变量文件，以.properties为后缀
	 * 
	 * @param appService
	 */
	private void genEnvProperties(AppService appService) {
		// Properties pro = new Properties();
		Properties pro = new OrderedProperties();// 对properties文件中的字段按照原始顺序输出
		List<EnvConfig> envConfList = appService.getEnvConfig();
		if (envConfList != null) {
			for (EnvConfig envconf : envConfList) {
				pro.put(envconf.getConfigKey(), envconf.getConfigValue());
			}
		}
		String configName = appService.getEnvsConfFileName() + "_"
				+ Long.valueOf(System.currentTimeMillis()) + ".properties";
		String propertyPath = getFilePath() + configName;// 文件路径，到文件名
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propertyPath);
			System.setProperty("line.separator", "\r\n");
			pro.store(fos, "Init properties");// 向新文件存储
			
		} catch (FileNotFoundException ex) {
			logger.error("["
					+ Constants.CREATE_APPSERVICE_ENVSCONF_NOTFOUND_ERROR
					+ "]创建应用服务时生成的环境变量文件路径错误", ex);
			throw new PaasWebException(
					Constants.CREATE_APPSERVICE_ENVSCONF_NOTFOUND_ERROR,
					ex.getMessage());

		} catch (IOException ex) {
			logger.error("[" + Constants.CREATE_APPSERVICE_ENVSCONF_IO_ERROR
					+ "]创建应用服务时生成的环境变量文件路径错误", ex);
			throw new PaasWebException(
					Constants.CREATE_APPSERVICE_ENVSCONF_IO_ERROR,
					ex.getMessage());
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除环境变量文件
	 * 
	 * @param path
	 */
	private void deleteFile(String appServiceName) {
		String path = getFilePath();

		File folder = new File(path);
		File[] files = folder.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			int index = fileName.indexOf(appServiceName);
			if (index > -1) {
				logger.info("删除环境变量文件的名称:" + fileName);
				file.delete();
			}
		}
	}

	/**
	 * 获取完整的文件名，文件放在项目部署路径/tempfile文件夹下 文件名组成:时间+实例id
	 * 
	 * @return
	 */
	private String getFilePath() {
		String path = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		path = path.substring(0, path.lastIndexOf("WEB-INF"));
		return path + TEMP_FILE_PATH;
	}

	/**
	 * 转换配置集合
	 * 
	 * @param appServiceId
	 *            应用服务ID
	 * @param ipaasType
	 *            ipaas类型
	 * @param ipaasName
	 *            ipaas名称
	 * @param ipaasBindStr
	 *            ipaas绑定名称
	 * @return the app config list
	 */
	private List<Ipaas> getIpaasList(String appServiceId, String[] ipaasName,
			String[] ipaasBindStr) {
		logger.info("开始执行转换ipaas信息...");
		List<Ipaas> ipaasList = new ArrayList<Ipaas>();
		Ipaas ipaas;
		if (ipaasName != null && ipaasName.length > 0 && ipaasBindStr != null
				&& ipaasBindStr.length > 0) {
			for (int i = 0; i < ipaasName.length; i++) {
				if (StringUtils.isNotBlank(ipaasName[i])) {
					ipaas = new Ipaas();
					ipaas.setName(ipaasName[i].trim());
					ipaas.setId(ipaasName[i].trim());
					ipaas.setBindStr(ipaasBindStr[i].trim());
					ipaasList.add(ipaas);
				}
			}
		}
		logger.info("执行转换ipaas信息成功！");
		return ipaasList;
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
	private List<EnvConfig> getEnvConfigList(String appServiceId,
			String[] appConfKey, String[] appConfValue) {
		logger.info("开始执行转换配置信息...");
		List<EnvConfig> configs = new ArrayList<EnvConfig>();
		EnvConfig conf;
		if (appConfKey != null && appConfKey.length > 0 && appConfValue != null
				&& appConfValue.length > 0) {
			for (int i = 0; i < appConfKey.length; i++) {
				if (StringUtils.isNotBlank(appConfKey[i])) {
					conf = new EnvConfig();
					if (appServiceId == null) {
						conf.setRelatedId(null);
					} else {
						conf.setRelatedId(appServiceId);
					}
					conf.setConfigKey(appConfKey[i].trim());
					conf.setConfigValue(appConfValue[i].trim());
					configs.add(conf);
				}
			}
		}
		logger.info("执行转换配置信息成功！");
		return configs;
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

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}
}
