package com.cmsz.paas.web.appservicegray.action;

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
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.io.Files;
import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.controller.entity.EnvConfigEntity;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.appservicegray.model.GrayEntity;
import com.cmsz.paas.web.appservicegray.model.GrayRelease;
import com.cmsz.paas.web.appservicegray.service.GrayReleaseService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.OrderedProperties;
import com.cmsz.paas.web.cicd.model.DepScanEntity;
import com.cmsz.paas.web.cicd.service.DeploymentService;
import com.cmsz.paas.web.constants.Constants;

public class GrayReleaseAction extends AbstractAction{

	@Autowired
	private GrayReleaseService grayReleaseService;
	
	@Autowired
	private DeploymentService deploymentService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GrayReleaseAction.class);

	private String appServiceId;
	
//	private String grayInstanceNum;
	
	private String deploymentType;
	
//	private String totalInstanceNum;
	
	private GrayEntity grayEntity;
	
	/** 输出文件流 */
	private InputStream file;

	/** 输出文件名称 */
	private String fileName;
	
	/** 配置文件. */
	private File configFile;

	/** 配置文件名称. */
	private String configFileName;
	
	/** 应用服务环境变量配置文件 */
	private File envsConfFile;
	
	private String type;
	
	/** 环境变量配置文件存放路径 */
	private final String TEMP_FILE_PATH = "tempfile/";
	
	private String envsConfFileName;
	
	private String exportEnvsFileName;
	
	@Autowired
	private ApplicationService appServiceService;
	/**
	 * 查询实例列表
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public void queryGray() throws Exception {
		logger.info("开始执行查询灰度版本，应用服务id：" + appServiceId);
		Page<GrayRelease> page = this.getJqGridPage("updateTime");
		try {
			List<GrayRelease> list = grayReleaseService.queryGrayList(appServiceId);
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询灰度版本完成！");
		} catch (PaasWebException ex) {
			logger.error("查询实例错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(),ex.toString());
		}
	}
	
	public String queryGrayById(){
		try {
			logger.info("开始查询灰度版本详情！");
			grayEntity = grayReleaseService.queryGrayById(appServiceId, type);
			} catch (Exception e) {
				logger.error("查询灰度版本错误", e);
			}
			return type;
	}
	
	/**
	 * 导入配置文件
	 */
	public void uploadConfigFile() throws Exception {
		String fileContent = "";
		BufferedReader br = null;
		try {
			InputStreamReader isr=new InputStreamReader(new FileInputStream(configFile),"GBK");
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
			this.fileName = fileName.replaceAll("_Gray", "");
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
			logger.info("开始查询灰度版本详情！");
			grayEntity = grayReleaseService.queryGrayById(appServiceId,"update");
			AppService appService = new AppService();
			appService.setEnvsConfFileName(this.getExportEnvsFileName());
			appService.setEnvConfig(getEvnConfig(grayEntity.getEnvConfig()));
			genEnvProperties(appService);
		} catch (Exception e) {
			logger.error("生成导出环境变量配置文件错误", e);
		}
	}
	
	private List<EnvConfig> getEvnConfig(List<EnvConfigEntity> envConfigEntities){
		
		List<EnvConfig> list = new ArrayList<EnvConfig>();
		if(envConfigEntities != null){
			for (EnvConfigEntity c : envConfigEntities) {
				EnvConfig e = new EnvConfig();
				e.setConfigKey(c.getConfigKey());
				e.setConfigValue(c.getConfigValue());
				e.setId(c.getId());
				list.add(e);
				
			}
		}
		
		return list;
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

	@UnLogging
	public void queryCheckResult(){
		try {
			String result = grayReleaseService.queryCheckResult(appServiceId);
			this.sendSuccessReslult(result);
		} catch (Exception e) {
			logger.error("查询灰度校验结果错误:", e);
			this.sendSuccessReslult(e.getMessage());
		}
	}
	
	/***
	 * 修改部署方式
	 */
	public void modifyDeploymentType(){
		logger.info("修改部署方式...");
		try {
//			deploymentService.modifyDeploymentType(appServiceId,deploymentType,grayInstanceNum,totalInstanceNum);
			deploymentService.modifyDeploymentType(appServiceId,deploymentType);
			sendSuccessReslult("SUCCESS");
		}catch(PaasWebException ex){
			logger.error("["+ex.getKey()+"]修改部署方式出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	
	public String getAppServiceId() {
		return appServiceId;
	}


	public void setAppServiceId(String appServiceId) {
		this.appServiceId = appServiceId;
	}


	public GrayEntity getGrayEntity() {
		return grayEntity;
	}


	public void setGrayEntity(GrayEntity grayEntity) {
		this.grayEntity = grayEntity;
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


	public GrayReleaseService getGrayReleaseService() {
		return grayReleaseService;
	}


	public void setGrayReleaseService(GrayReleaseService grayReleaseService) {
		this.grayReleaseService = grayReleaseService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnvsConfFileName() {
		return envsConfFileName+"_Gray";
	}

	public void setEnvsConfFileName(String envsConfFileName) {
		this.envsConfFileName = envsConfFileName;
	}

	public File getEnvsConfFile() {
		return envsConfFile;
	}

	public void setEnvsConfFile(File envsConfFile) {
		this.envsConfFile = envsConfFile;
	}

	public String getExportEnvsFileName() {
		return exportEnvsFileName +"_Gray";
	}

	public void setExportEnvsFileName(String exportEnvsFileName) {
		this.exportEnvsFileName = exportEnvsFileName;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

//	public String getGrayInstanceNum() {
//		return grayInstanceNum;
//	}
//
//	public void setGrayInstanceNum(String grayInstanceNum) {
//		this.grayInstanceNum = grayInstanceNum;
//	}

	public String getDeploymentType() {
		return deploymentType;
	}

	public void setDeploymentType(String deploymentType) {
		this.deploymentType = deploymentType;
	}

//	public String getTotalInstanceNum() {
//		return totalInstanceNum;
//	}
//
//	public void setTotalInstanceNum(String totalInstanceNum) {
//		this.totalInstanceNum = totalInstanceNum;
//	}

	
	
	
}
