package com.cmsz.paas.web.appservicegray.model;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.EnvConfigEntity;
import com.cmsz.paas.web.appservice.model.EnvConfig;

public class GrayEntity {
	private long id;

	// 所属应用id
	private String appId;

	private String appServiceName;

	// 总实例数
	private int totalInstanceNum;

	// 灰度版本实例数
	private int grayInstanceNum;

	// 运行版本信息
	// 类型 1-正式 2-灰度
	private int type;
	
	/** 部署类型(1.自动发布,2.自动灰度,3.手动灰度) */
	private int deploymentType;

	// 镜像类型，1-公共镜像，2-应用镜像
	private int imageType;

	// 镜像Id
	private long imageId;

	private long imageVersionId;

	// 运行版本id
	private long runningVersion;

	// 日志路径
	private String logDir;

	// 配置文件路径
	private String configFilePath;

	// 配置文件内容
	private String configFileInfo;

	// 应用服务状态
	private String state;

	// 运行版本
	private String runVersion;

	private int oper_type;
	/**
	 * 环境变量配置
	 */
	private List<EnvConfigEntity> envConfig;

	/** 配置的keys */
	private String[] appConfKey;
	/** 配置的values */
	private String[] appConfValue;
	
	/** 是否可以创建灰度 */
	private boolean isGray;
	
	

	public boolean isGray() {
		return isGray;
	}

	public void setGray(boolean isGray) {
		this.isGray = isGray;
	}

	public int getDeploymentType() {
		return deploymentType;
	}

	public void setDeploymentType(int deploymentType) {
		this.deploymentType = deploymentType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getTotalInstanceNum() {
		return totalInstanceNum;
	}

	public void setTotalInstanceNum(int totalInstanceNum) {
		this.totalInstanceNum = totalInstanceNum;
	}

	public int getGrayInstanceNum() {
		return grayInstanceNum;
	}

	public void setGrayInstanceNum(int grayInstanceNum) {
		this.grayInstanceNum = grayInstanceNum;
	}

	public int getType() {
		type = 2;
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public long getRunningVersion() {
		return runningVersion;
	}

	public void setRunningVersion(long runningVersion) {
		this.runningVersion = runningVersion;
	}

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public List<EnvConfigEntity> getEnvConfig() {
		return envConfig;
	}

	public void setEnvConfig(List<EnvConfigEntity> envConfig) {
		this.envConfig = envConfig;
	}

	public String[] getAppConfKey() {
		return appConfKey;
	}

	public void setAppConfKey(String[] appConfKey) {
		this.appConfKey = appConfKey;
	}

	public String[] getAppConfValue() {
		return appConfValue;
	}

	public void setAppConfValue(String[] appConfValue) {
		this.appConfValue = appConfValue;
	}

	public long getImageVersionId() {
		return imageVersionId;
	}

	public void setImageVersionId(long imageVersionId) {
		this.imageVersionId = imageVersionId;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public String getConfigFileInfo() {
		return configFileInfo;
	}

	public void setConfigFileInfo(String configFileInfo) {
		this.configFileInfo = configFileInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRunVersion() {
		return runVersion;
	}

	public void setRunVersion(String runVersion) {
		this.runVersion = runVersion;
	}

	public int getOper_type() {
		return oper_type;
	}

	public void setOper_type(int oper_type) {
		this.oper_type = oper_type;
	}

	public String getAppServiceName() {
		return appServiceName;
	}

	public void setAppServiceName(String appServiceName) {
		this.appServiceName = appServiceName;
	}

}
