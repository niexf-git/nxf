package com.cmsz.paas.web.build.model;

/**
 * 构建信息，主要用于构建列表展示
 * 
 * @author liaohw
 * @date 2016-4-1
 */
public class BuildInfo {
	/** 唯一标识 */
	private String id;

	/** 构建名称 */
	private String name;

	/** 应用id */
	private String appId;

	/** 应用名称 */
	private String appName;

	/** 状态 */
	private String status;

	/** 上次构建开始时间 */
	private String lastStartTime;

	/** 上次构建结束时间 */
	private String lastEndTime;

	/** 构建次数 */
	private String buildNumber;

	/** 镜像id */
	private String imageId;

	/** 镜像名称 */
	private String imageName;

	/** 创建人 */
	private String creator;

	/** 创建时间 */
	private String createTime;

	/** 构建类型 */
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastStartTime() {
		return lastStartTime;
	}

	public void setLastStartTime(String lastStartTime) {
		this.lastStartTime = lastStartTime;
	}

	public String getLastEndTime() {
		return lastEndTime;
	}

	public void setLastEndTime(String lastEndTime) {
		this.lastEndTime = lastEndTime;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BuildInfo [id=" + id + ", name=" + name + ", appId=" + appId
				+ ", appName=" + appName + ", status=" + status
				+ ", lastStartTime=" + lastStartTime + ", lastEndTime="
				+ lastEndTime + ", buildNumber=" + buildNumber + ", imageId="
				+ imageId + ", imageName=" + imageName + ", creator=" + creator
				+ ", createTime=" + createTime + ", type=" + type + "]";
	}

}
