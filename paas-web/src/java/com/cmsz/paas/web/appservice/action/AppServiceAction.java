package com.cmsz.paas.web.appservice.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.io.Files;
import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.controller.request.AppId;
import com.cmsz.paas.common.model.controller.request.AppIdList;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.appservice.model.Ipaas;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.service.InstanceService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.GrafanaJsonFileUtil;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.OrderedProperties;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.grafana.service.GrafanaService;
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;

/**
 * 应用服务Action.
 * 
 * @author fubl
 */

public class AppServiceAction extends AbstractAction {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(AppServiceAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The application service. */
	@Autowired
	private ApplicationService appServiceService;
	/** The instance service. */
	@Autowired
	private InstanceService instanceService;

	@Autowired
	private GrafanaService grafanaService;
	/** 应用服务页面model **/
	private AppService appService;

	/** 基础服务页面model **/
	private Ipaas ipaas;

	/** 应用服务环境变量配置页面model **/
	private EnvConfig envConfig;

	/** 应用服务模糊查询条件 */
	private String token = "";

	/** 应用服务ID */
	private String appServiceId = "";

	/** 应用ID */
	private String appId = "";

	/** 操作类型 */
	private String operType = "";

	/** 应用服务环境变量配置文件 */
	private File envsConfFile;

	/** 环境变量配置文件名称 */
	private String envsConfFileName;

	/** 环境变量配置文件存放路径 */
	private final String TEMP_FILE_PATH = "tempfile/";

	/** 输出文件流 */
	private InputStream file;

	/** 输出文件名称 */
	private String fileName;

	/** 导出环境变量文件名称 */
	private String exportEnvsFileName;

	/** 错误信息. */
	private String errorMsg;

	/** 错误编码. */
	private String errorCode;

	/** 信号量 */
	private String signal;

	/** 用于区分是操作区还是配置页面请求查询服务详情 */
	private String functionModule;
	/** grafana url */
	private String url;
	
	/** 配置文件. */
	private File configFile;

	/** 配置文件名称. */
	private String configFileName;
	
	/** 节点ip */
	private String minionIp;
	
	/** 监控类型 */
	private String type;
	
	private long cluserId;
	
	private String ip;
	
	
	//CPU数值
	private String cpuNumber;
	
	//内存数值
	private String memNumber;
	
	//实例数值
	private String instNumber;
	
	//校验形式
	private String optionTypes;

	/**
	 * 查询应用服务列表. 查询条件：token
	 */
	@SuppressWarnings("unchecked")
	public void queryAppServiceList() {
		logger.info("开始执行查询应用服务列表");
		Page<AppService> page = this.getJqGridPage("create_time");

		try {
			List<AppService> appServieList = new ArrayList<AppService>();
			List<RolePermissionRelation> dataPermission = (List<RolePermissionRelation>) getSessionMap()
					.get("dataPermission");
			AppIdList appIdList = getAppListFromPermission(dataPermission);
			if(appIdList.getAppIdList().size()>0){
				appServieList = appServiceService.queryAppServiceList(appIdList, token);
			}
			page.setResult(appServieList);
			page.setTotalCount(appServieList.size());
			this.renderText(JackJson.fromObjectToJson(page));

			logger.info("查询应用服务列表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询应用服务出错", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}
	/**
	 * 根据minionIp查询应用服务列表
	 * 
	 * @throws Exception
	 */
	public void queryAppService() throws Exception {
		Page<AppService> page = this.getJqGridPage("createTime");
		logger.info("开始执行系统监控-右侧列表-查询服务，节点Ip：" + minionIp);
		try {
			List<AppService> appServiceDetail = appServiceService.queryAppServiceAndInstance(minionIp);
			page.setResult(appServiceDetail);
			page.setTotalCount(appServiceDetail.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("系统监控-右侧列表-查询服务完成 ，服务数：" + appServiceDetail.size());
		} catch (PaasWebException ex) {
			logger.error("系统监控-右侧列表-查询服务错误", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}
	
	
	
	/***
	 * 校验Ip是否冲突
	 */
	public void queryIsCheckIP() {
		logger.info("开始执行校验Ip是否冲突接口：" + ip);
		try {
			boolean flag = appServiceService.isCheckIp(appServiceId,ip) ;
			logger.info("校验IP是否冲突结果：" + flag);
			sendSuccessReslult(flag);
		} catch (PaasWebException e) {
			logger.error("校验Ip是否冲突错误",e);
			this.sendFailReslutl(e.toString());
		}
	}
	
	/***
	 * 校验是否超额
	 */
	public void queryIsExcess() {
		logger.info("开始执行校验CPU内存是否超额接口！");
		try {
			String appSelectedId = (String) getSessionMap().get("appPerSelectedId");
			String selectedOpertype = (String) getSessionMap().get("selectedOpertype");
			Object result = appServiceService.isExcess(optionTypes, instNumber, appSelectedId, selectedOpertype, cpuNumber, memNumber,appServiceId);
			sendSuccessReslult(result);
		} catch (PaasWebException e) {
			logger.error("校验Ip是否冲突错误",e);
			this.sendFailReslutl(e.toString());
		}
	}
	
	/**
	 * 根据minionIp,appIds,operType查询应用服务列表
	 * 
	 * @throws Exception
	 */
	public void queryNewAppService() throws Exception {
		Page<AppService> page = this.getJqGridPage("createTime");
		logger.info("开始执行系统监控-右侧列表-查询服务，节点Ip：" + minionIp);
		try {
			String appIds = getSessionMap().get("appPermissionId").toString();
			String operType = getSessionMap().get("opertype").toString();
			List<AppService> appServiceDetail = appServiceService.queryNewAppServiceAndInstance(minionIp,appIds,operType);
			page.setResult(appServiceDetail);
			page.setTotalCount(appServiceDetail.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("系统监控-右侧列表-查询服务完成 ，服务数：" + appServiceDetail.size());
		} catch (PaasWebException ex) {
			logger.error("系统监控-右侧列表-查询服务错误", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/*
	 * 根据session中的数据权限解析出应用列表
	 */
	private AppIdList getAppListFromPermission(
			List<RolePermissionRelation> dataPermission) {

		List<AppId> list = new ArrayList<AppId>();
		AppIdList appIdList = new AppIdList();
		AppId appId;
		String appSelectedId = (String) getSessionMap().get("appPerSelectedId");
		String selectedOpertype = (String) getSessionMap().get("selectedOpertype");
		
		
		for (RolePermissionRelation roleDataPer : dataPermission) {
			appId = new AppId();
			appId.setId(Long.valueOf(roleDataPer.getPermissionId()));
			appId.setType(roleDataPer.getOpertype());

			list.add(appId);
		}

		appIdList.setAppIdList(list);
		appIdList.setClusterId(cluserId);
		
		if(appSelectedId.indexOf(",")==-1){
			List<AppId> tempAppIds = new ArrayList<AppId>();
			try {
				for (AppId appIds : appIdList.getAppIdList()) {
					if(appSelectedId.equals(String.valueOf(appIds.getId()))){
						tempAppIds.add(appIds);
					}
				}
			} catch (Exception e) {
				logger.error(new StringBuffer().append("应用服务过滤应用名称异常:").append(e.getMessage()).toString());
			}
			appIdList.setAppIdList(tempAppIds);
		}
		if(selectedOpertype.indexOf(",")==-1){
			List<AppId> tempAppIds = new ArrayList<AppId>();
			try {
				for (AppId appIds : appIdList.getAppIdList()) {
					if(selectedOpertype.equals(String.valueOf(appIds.getType()))){
						tempAppIds.add(appIds);
					}
				}
			} catch (Exception e) {
				logger.error(new StringBuffer().append("应用服务过滤应用名称异常:").append(e.getMessage()).toString());
			}
			appIdList.setAppIdList(tempAppIds);
		}
		
		return appIdList;
	}

	/**
	 * 查询应用服务详情. 查询条件：应用服务id
	 */
	public String queryAppServiceById() {
		logger.info("开始执行查询应用服务详情，应用服务ID：" + appServiceId);
		try {
			appService = appServiceService.queryAppServiceById(appServiceId);

			logger.info("执行查询应用服务详情成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询应用服务详情出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}

		return functionModule;
	}

	/**
	 * 查询所有集群列表.
	 */
	@UnLogging
	public void queryClusterList() {
		logger.info("开始执行查询集群列表...");
		@SuppressWarnings("unchecked")
		Page<Cluster> page = this.getJqGridPage("name");

		try {

			if (appId.equals("")) {
				appId = getSessionMap().get("appPerSelectedId").toString();
			}

			if (operType.equals("")) {
				operType = getSessionMap().get("selectedOpertype").toString();
			}
			
			if(appId.indexOf(",") != -1){
				appId = "0";
			}
			
			if(operType.indexOf(",") != -1){
				operType = "0";
			}
			
			List<Cluster> clusterList = appServiceService.queryClusterList(appId, operType ,"app","","");
			
			if (clusterList != null) {
				List<Cluster> apaasClusterList = new ArrayList<Cluster>();
				Cluster apaasCluster = null;
				for(int i = 0; i < clusterList.size(); i++){
					if("2".equals(clusterList.get(i).getType())){ //type 类型值  为 2 的 就是 apaas集群
						apaasCluster = new Cluster();
						apaasCluster.setDataCenterId(clusterList.get(i).getDataCenterId());
						apaasCluster.setDescription(clusterList.get(i).getDescription());
						apaasCluster.setId(clusterList.get(i).getId());
						apaasCluster.setInsertTime(clusterList.get(i).getInsertTime());
						apaasCluster.setLabel(clusterList.get(i).getLabel());
						apaasCluster.setName(clusterList.get(i).getName());
						apaasCluster.setType(clusterList.get(i).getType());
						apaasClusterList.add(clusterList.get(i));
					}
				}
				page.setResult(apaasClusterList);
				page.setTotalCount(apaasClusterList.size());
				this.renderText(JackJson.fromObjectToJson(page));
//				page.setResult(clusterList);
//				page.setTotalCount(clusterList.size());
//				this.renderText(JackJson.fromObjectToJson(page));
			} else {
				page.setResult(clusterList);
				page.setTotalCount(0);
				this.renderText(JackJson.fromObjectToJson(page));
			}
			logger.info("执行查询集群列表成功！");
		} catch (PaasWebException ex) {
			this.sendFailResult(ex.toString());
			logger.error("[" + ex.getKey() + "]获取集群列表出错", ex);
		} catch (Exception ex) {
			logger.error("获取集群列表出错", ex);
			this.sendFailResult(ex.toString());
		}
	}

	/**
	 * 创建应用服务，并生成环境变量文件和granfana的Json文件
	 */
	public void createAppService() {
		try {
			logger.info("开始创建应用服务！");

			// 1. 新增应用
			String appId = (String) getSessionMap().get("appPerSelectedId");
			appService.setApp_id(appId);

			String operType = (String) getSessionMap().get("selectedOpertype");
			appService.setOper_type(operType);

			String loginName = (String) getSessionMap().get("loginName");
			appService.setUser_id(loginName);

			IdValue idValue = appServiceService.createAppService(appService);
			
			// 2. 生成一个环境变量配置文件，后缀.properties
			deleteFile(appService.getEnvsConfFileName());// 先删除之前的旧文件
			genEnvProperties(appService);


			sendSuccessReslult(idValue.getId() + "");

			logger.info("创建应用服务成功 , 应用服务id: " + idValue.getId());
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]创建应用服务出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
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
				if(envconf==null)continue;
				pro.put(envconf.getConfigKey(), envconf.getConfigValue());
			}
		}
		String configName = appService.getEnvsConfFileName() + "_"
				+ Long.valueOf(System.currentTimeMillis()) + ".properties";
		String propertyPath = getFilePath() + configName;// 文件路径，到文件名
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(propertyPath);
			System.setProperty("line.separator", "\r\n");
			pro.store(fos, "Init properties");// 向新文件存储

			// getSessionMap().put("exportEnvsFileName", configName);//
			// 将保存的环境变量文件写入session，供修改服务时直接导出

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
		}
	}
	
	/***
	 * 查询集群下的主机IP
	 */
	public void queryCluserHostIp(){
		logger.info("开始执行查询集群下的主机ip...");
		@SuppressWarnings("unchecked")
		Page<String> page = this.getJqGridPage("name");

		try {

			List<String> clusterList = appServiceService.queryCluserHostIpList(cluserId);
			
			if (clusterList != null) {
				page.setResult(clusterList);
				page.setTotalCount(clusterList.size());
				this.renderText(JackJson.fromObjectToJson(page));
			} else {
				page.setResult(clusterList);
				page.setTotalCount(0);
				this.renderText(JackJson.fromObjectToJson(page));
			}
			logger.info("执行查询集群下的主机列表成功！");
		} catch (PaasWebException ex) {
			this.sendFailResult(ex.toString());
			logger.error("[" + ex.getKey() + "]获取集群下的主机列表出错", ex);
		} catch (Exception ex) {
			logger.error("获取集群下的主机列表出错", ex);
			this.sendFailResult(ex.toString());
		}
	
	}

	/*
	 * 导入环境变量配置文件
	 */
	public void importEnvsFile() {
		logger.info("开始上传服务的环境变量文件");
		Properties prop = new OrderedProperties();// 对properties文件中的字段按照原始顺序输出
		JSONObject jsonObject = new JSONObject();
		try {
			deleteFile(getEnvsConfFileName());// 先删除之前的旧文件

			// 上传环境变量文件存放位置
			String configName = this.getEnvsConfFileName() + "_"
					+ Long.valueOf(System.currentTimeMillis()) + ".properties";
			// String configName = this.getEnvsConfFileName();
			jsonObject.put("fileName", this.getEnvsConfFileName());
			String dstPath = getFilePath() + configName;
			File dstFile = new File(dstPath);
			try {
				Files.copyFile(this.envsConfFile, dstFile);
			} catch (IOException ex) {
				logger.error("上传环境变量配置文件", ex);
				throw new PaasWebException(
						Constants.USER_UPLOAD_FILE_IO_EXCEPTION,
						ex.getMessage());
			}
			try {
				prop.load(new InputStreamReader(new FileInputStream(dstPath),
						"GBK"));// 乱码处理
				@SuppressWarnings("rawtypes")
				// Enumeration en = prop.propertyNames();// 得到资源文件中的所有key值
				Enumeration<Object> en = prop.keys();// 得到资源文件中的所有key值

				JSONObject confobj = new JSONObject();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					String value = prop.getProperty(key);
					// 输出资源文件中的key与value值
					confobj.put(key, value);
				}
				jsonObject.put("conf", confobj);

			} catch (Exception ex) {
				logger.error("读取配置文件", ex);
				this.sendFailResult("读取配置文件", ex.toString());
			}
			// dstFile.delete();
		} catch (PaasWebException ex) {
			logger.error(ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
		this.sendSuccessReslult(jsonObject.toString());
	}

	/*
	 * 导出环境变量配置文件
	 */
	public String exportEnvsFile() {
		logger.info("开始导出服务的环境变量文件");
		try {
			// 删除某个时间点以前的文件
			// String path = getFilePath();
			// deleteFile(path);

			String fileName = getFileName(this.getExportEnvsFileName());
			// if (!fileName.equals("")) {// 修改应用服务时直接导出环境变量文件
			// getSessionMap().put("exportEnvsFileName", fileName);//
			// 如果之前有导入过文件，则覆盖session中的导入文件名
			// }
			// fileName =
			// getSessionMap().get("exportEnvsFileName").toString();//
			// 导出的文件名统一从session中获取
			
			//机器上未找到环境变量文件则生成一份
			if("".equals(fileName)){
				generateEnvsFile();
				fileName = getFileName(this.getExportEnvsFileName());
			}

			// 返回流下载到本地
			file = ServletActionContext.getServletContext()
					.getResourceAsStream(TEMP_FILE_PATH + fileName);
			this.fileName = fileName.replaceAll("_AppService", "");
			return SUCCESS;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return ERROR;
		}
	}
	
	/***
	 * 生成环境变量配置文件
	 */
	private void generateEnvsFile(){
		logger.info("开始执行查询应用服务详情，应用服务ID：" + appServiceId);
		try {
			appService = appServiceService.queryAppServiceById(appServiceId);
			//未生成过环境变量文件 FileName为空  为文件名赋值
			if("".equals(appService.getEnvsConfFileName()) ||
				null == appService.getEnvsConfFileName()){
				appService.setEnvsConfFileName(this.getExportEnvsFileName());
			}
			genEnvProperties(appService);
		} catch (Exception e) {
			logger.error("生成导出环境变量配置文件错误", e);
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
	 * 获取保存的环境变量文件名
	 * 
	 * @param path
	 */
	private String getFileName(String appServiceName) {
		String path = getFilePath();

		File folder = new File(path);
		File[] files = folder.listFiles();
		if (files == null) {
			return "";
		} else {
			String fileName = "";
			for (File file : files) {
				fileName = file.getName();
				int index = fileName.indexOf(appServiceName);
				if (index > -1) {
					logger.info("获取环境变量文件的名称:" + fileName);
					break;
				}else{
					fileName = "";
				}
			}
			return fileName;
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

	// private void deleteFile(String path) {
	// String fileName = null;
	// Long fileCreateTime = Long.MAX_VALUE;
	// Long currentTime = System.currentTimeMillis();
	// // 保护时间
	// String guardTime = PropertiesUtil.getValue("guardTime");
	// Long guardTimeMillis = 60 * 60 * 1000L;// 1小时
	// if (StringUtils.isNotBlank(guardTime) && !"[]".equals(guardTime)) {
	// guardTimeMillis = Long.valueOf(guardTime) * guardTimeMillis;
	// }
	// File folder = new File(path);
	// File[] files = folder.listFiles();
	// if (files == null) {
	// return;
	// }
	// for (File file : files) {
	// fileName = file.getName();
	// int index = fileName.indexOf("_");
	// if (index > -1) {
	// fileCreateTime = Long.valueOf(fileName.substring(0, index));
	// }
	// // 当前时间与文件生成时间的差大于某个配置值就删除该文件
	// if (currentTime - fileCreateTime > guardTimeMillis) {
	// logger.info("删除日志文件的名称:" + file.getName());
	// file.delete();
	// }
	// }
	// }
	
	/***
	 * 检查是否有绑定基础服务或者基础服务全部为运行
	 */
	@UnLogging
	public void checkAppServiceRelaIpaas() {
		logger.info("开始执行检查是否有绑定基础服务或者基础服务全部为运行，应用服务ID：" + appServiceId);
		try {
			appServiceService.checkAppServiceRelaIpaas(appServiceId);
			logger.info("执行检查成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]检查出错", ex);
			if(ex.getKey().equals("PAAS-20314")){
				this.sendFailResult(ex.getErrorCode(), ex.getMessage());
			}else{
				this.sendFailResult(ex.getErrorCode(), ex.toString());
			}
		}
	}
	
	/***
	 * 启动应用服务
	 */
	public void startAppService() {
		logger.info("开始执行启动应用服务，应用服务ID：" + appServiceId);
		try {
			appServiceService.startAppService(appServiceId);
			logger.info("执行启动应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]启动应用服务出错", ex);
			if(ex.getKey().equals("PAAS-20314")){
				this.sendFailResult(ex.getErrorCode(), ex.getMessage());
			}else{
				this.sendFailResult(ex.getErrorCode(), ex.toString());
			}
		}
	}
	
	/***
	 * 一键启动应用服务和绑定的基础服务
	 */
	public void allStartAppService() {
		logger.info("开始执行一键启动应用服务，应用服务ID：" + appServiceId);
		try {
			appServiceService.allStartAppService(appServiceId);
			logger.info("执行一键启动应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]一键启动应用服务出错", ex);
			if(ex.getKey().equals("PAAS-20314")){
				this.sendFailResult(ex.getErrorCode(), ex.getMessage());
			}else{
				this.sendFailResult(ex.getErrorCode(), ex.toString());
			}
		}
	}
	
	/***
	 * 启动应用服务自身
	 */
	public void selfStartAppService() {
		logger.info("开始执行启动应用服务，应用服务ID：" + appServiceId);
		try {
			appServiceService.selfStartAppService(appServiceId);
			logger.info("执行启动应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]启动应用服务出错", ex);
			if(ex.getKey().equals("PAAS-20314")){
				this.sendFailResult(ex.getErrorCode(), ex.getMessage());
			}else{
				this.sendFailResult(ex.getErrorCode(), ex.toString());
			}
		}
	}
	
	/***
	 * 一键升级服务版本
	 */
	public void batchUpgradeServiceVersions() {
		logger.info("开始执行一键升级服务版本，应用服务Ids:"+appServiceId);
		try {
			if(!"".equals(appServiceId)) {
				appServiceService.batchUpgradeServiceVersions(appServiceId);
				logger.info("执行一键升级服务版本成功！");
				sendSuccessReslult("SUCCESS");
			}
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]一键升级服务版本出错", e);
			this.sendFailResult(e.getErrorCode(), e.toString());
		}
	}

	/***
	 * 批量启动应用服务
	 */
	public void startAppServices() {
		logger.info("开始执行批量启动应用服务，应用服务ID：" + appServiceId);
		try {
			appServiceService.batchStartAppServices(appServiceId);
			logger.info("执行批量启动应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]批量启动应用服务出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/***
	 * 批量停止应用服务
	 */
	public void stopAppServices() {
		logger.info("开始执行批量停止应用服务，应用服务ID：" + appServiceId);
		try {
			appServiceService.batchStopAppServices(appServiceId);
			logger.info("执行批量停止应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]批量停止应用服务出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/***
	 * 批量重启应用服务
	 */
	public void forcedRestartAppServices() {
		logger.info("开始执行批量重启应用服务，应用服务ID：" + appServiceId);
		try {
			appServiceService.forcedRestartAppServices(appServiceId);
			logger.info("执行批量重启应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]批量重启应用服务出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/***
	 * 批量删除应用服务
	 */
	public void deleteAppServices() {
		logger.info("开始执行批量删除应用服务，应用服务ID：" + appServiceId);
		String[] id=appServiceId.split(",");
		for (int i = 0; i < id.length; i++) {
			try{
				appService = appServiceService.queryAppServiceById(id[i]);
			}catch (Exception ex) {
				//不处理异常
			}
			try {
				//删除grafana dashboards
				String title = GrafanaJsonFileUtil.getServiceType("2") + "_"+appService.getApp_name()+"_"+GrafanaJsonFileUtil.getOperType(appService.getOper_type())+"_"+ appService.getName();
				grafanaService.deleteDashboard(title);
			} catch (Exception e) {
				logger.error("删除grafana dashboard错误", e);
			}
		}
		try {
			appServiceService.batchDeleteAppServices(appServiceId);
			logger.info("执行批量删除应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]批量删除应用服务出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	public void sendSignal2AppService() throws Exception {
		logger.info("开始执行发送信号量，应用服务ID：" + appServiceId + "，信号量：" + signal);
		try {
			RestResult result = appServiceService.sendSignal2AppService(
					appServiceId, signal);
			if (!RestResult.SUCCESS_CODE.equals(result.getRetCode())) {
				String errorMsg = PropertiesUtil.getValue(result.getRetCode());
				logger.info("执行发送信号量出错，code：" + result.getRetCode() + "，msg："
						+ errorMsg);
				this.sendFailReslutl(errorMsg);
			} else {
				logger.info("执行发送信号量成功！");
				sendSuccessReslult("SUCCESS");
			}
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]发送信号出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/***
	 * 删除应用服务
	 */
	public void deleteAppService() {
		logger.info("开始执行删除应用服务，应用服务ID：" + appServiceId);
		try{
			appService = appServiceService.queryAppServiceById(appServiceId);
		}catch (Exception ex) {
			//不处理异常
		}
		try {
			appServiceService.deleteAppService(appServiceId);
			try {
				//删除grafana dashboards
				String title = GrafanaJsonFileUtil.getServiceType("2") + "_"+appService.getApp_name()+"_"+GrafanaJsonFileUtil.getOperType(appService.getOper_type())+"_"+ appService.getName();
				grafanaService.deleteDashboard(title);
			} catch (Exception e) {
				logger.error("删除grafana dashboard错误", e);
			}
			logger.info("执行删除应用服务成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]删除应用服务出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	@UnLogging
	public void queryGrafana() {
		logger.info("开始执行查询Grafana路径");
		try {
			appService = appServiceService.queryAppServiceById(appServiceId);
			String title = GrafanaJsonFileUtil.getServiceType("2")
					+ "_"
					+ appService.getApp_name()
					+ "_"
					+ GrafanaJsonFileUtil
							.getOperType(appService.getOper_type()) + "_"
					+ appService.getName();
				List<Instance> list = instanceService
						.queryInstByAppServiceId(appServiceId);
				String json = GrafanaJsonFileUtil.getDashboardsJson(
						appService.getApp_name(), appService.getOper_type(),
						appService.getName(), "2", list,type);
				grafanaService.createDashboard(json);
			url = RestUtils.grafanaUrl("queryGrafanaDashboardsUrl",
					title.toLowerCase());
			sendSuccessReslult(url);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询Grafana路径出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}
	
	/**
	 * 导入配置文件
	 */
	public void uploadConfigFile() throws Exception {
		String fileContent = "";
		BufferedReader br = null;
		try {
			InputStreamReader isr=new InputStreamReader(new FileInputStream(configFile),"UTF-8");
			br = new BufferedReader(isr);
			String lineSeparator = System.getProperty("line.separator");
			String line = null;
			while ((line = br.readLine()) != null) {
				fileContent += line + lineSeparator;
			}
			this.sendSuccessReslult(fileContent);
			logger.info("配置文件：" + configFileName + " 导入成功！ ");
		} catch (Exception e) {
			logger.error("配置文件导入错误！", e);
			this.sendFailResult(Constants.USER_UPLOAD_FILE_IO_EXCEPTION,
					e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("流关闭错误！", e);
				}
			}
		}
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public EnvConfig getEnvConfig() {
		return envConfig;
	}

	public void setEnvConfig(EnvConfig envConfig) {
		this.envConfig = envConfig;
	}

	public Ipaas getIpaas() {
		return ipaas;
	}

	public void setIpaas(Ipaas ipaas) {
		this.ipaas = ipaas;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppServiceId() {
		return appServiceId;
	}

	public void setAppServiceId(String appServiceId) {
		this.appServiceId = appServiceId;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public String getFunctionModule() {
		return functionModule;
	}

	public void setFunctionModule(String functionModule) {
		this.functionModule = functionModule;
	}

	public File getEnvsConfFile() {
		return envsConfFile;
	}

	public void setEnvsConfFile(File envsConfFile) {
		this.envsConfFile = envsConfFile;
	}

	public String getEnvsConfFileName() {
		return envsConfFileName + "_AppService";
	}

	public void setEnvsConfFileName(String envsConfFileName) {
		this.envsConfFileName = envsConfFileName;
	}

	public String getExportEnvsFileName() {
		return exportEnvsFileName+"_AppService";
	}

	public void setExportEnvsFileName(String exportEnvsFileName) {
		this.exportEnvsFileName = exportEnvsFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}
	public String getMinionIp() {
		return minionIp;
	}
	public void setMinionIp(String minionIp) {
		this.minionIp = minionIp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getCluserId() {
		return cluserId;
	}
	public void setCluserId(long cluserId) {
		this.cluserId = cluserId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCpuNumber() {
		return cpuNumber;
	}
	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}
	public String getMemNumber() {
		return memNumber;
	}
	public void setMemNumber(String memNumber) {
		this.memNumber = memNumber;
	}
	public String getInstNumber() {
		return instNumber;
	}
	public void setInstNumber(String instNumber) {
		this.instNumber = instNumber;
	}
	public String getOptionTypes() {
		return optionTypes;
	}
	public void setOptionTypes(String optionTypes) {
		this.optionTypes = optionTypes;
	}

	
}
