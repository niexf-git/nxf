package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

public class AppServiceVersionEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ID
	private String id;
	
	private String appServiceId;
	
	private int type;
	
	private int imageType;

	private long imageId;

	private long runningVersion;
	
	private String logDir;
	
	private Date createTime;
	
	/**
	 * 关联的环境变量
	 */
	private List<EnvConfigEntity> envs;

	/**
	 * 版本使用的私有镜像
	 */
	private PrivateImageEntity privateImage;

	/**
	 * 版本使用的公有镜像
	 */
	private PublicImageEntity publicImage;

	/**
	 * 配置文件
	 */
	private ConfigFileEntity configFile;

	@Override
	public String toString() {
		return "AppServiceEntity "
				+ "[id=" + id 
				+ ", appServiceId="+appServiceId
				+ ", type=" + type 
				+ ", imageType="+imageType
				+ ", imageId=" + imageId
				+ ", runningVersion=" + runningVersion 
				+ ", logDir=" + logDir
				+ ", createTime=" + createTime 
				+ ", envs=" + envs 
				+ ", privateImage=" + privateImage
				+ ", publicImage=" + publicImage 
				+ ", configFile=" + configFile
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppServiceId() {
		return appServiceId;
	}

	public void setAppServiceId(String appServiceId) {
		this.appServiceId = appServiceId;
	}

	public int getType() {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<EnvConfigEntity> getEnvs() {
		return envs;
	}

	public void setEnvs(List<EnvConfigEntity> envs) {
		this.envs = envs;
	}

	public PrivateImageEntity getPrivateImage() {
		return privateImage;
	}

	public void setPrivateImage(PrivateImageEntity privateImage) {
		this.privateImage = privateImage;
	}

	public PublicImageEntity getPublicImage() {
		return publicImage;
	}

	public void setPublicImage(PublicImageEntity publicImage) {
		this.publicImage = publicImage;
	}

	public ConfigFileEntity getConfigFile() {
		return configFile;
	}

	public void setConfigFile(ConfigFileEntity configFile) {
		this.configFile = configFile;
	}
}
