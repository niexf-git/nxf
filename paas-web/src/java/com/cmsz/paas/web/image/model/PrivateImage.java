package com.cmsz.paas.web.image.model;

/**
 * 私有镜像
 * @author lin.my 2016-4-1
 */
public class PrivateImage {
	
	private String id;

	private String appId;

	private String appName; //应用名称

	private String buildId;

	private String buildName; //构建名称

	private String name; //私有镜像名称

	private String description;

	private String createBy; //创建人

	private String createTime; //创建时间
	
	private String registryUrl; //仓库地址

	private String registryUser; //用户名

	private String registryPswd; //密码

	private String status;
	
	private boolean checked;
	
//	private boolean deployOrnot;
//	
//	public boolean isDeployOrnot() {
//		return deployOrnot;
//	}
//
//	public void setDeployOrnot(boolean deployOrnot) {
//		this.deployOrnot = deployOrnot;
//	}

	/**
	 * 下面两个没有确定下来 以及 toString方法
	 
	private AppEntity app;
	private List<PrivateImageVersionEntity> versions;
	*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}

	public String getRegistryUser() {
		return registryUser;
	}

	public void setRegistryUser(String registryUser) {
		this.registryUser = registryUser;
	}

	public String getRegistryPswd() {
		return registryPswd;
	}

	public void setRegistryPswd(String registryPswd) {
		this.registryPswd = registryPswd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
