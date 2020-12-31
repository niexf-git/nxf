package com.cmsz.paas.web.image.model;
/**
 * 私有镜像版本
 * @author lin.my 2016-4-7
 */
public class PrivateImageVersion {

	private String id;

	private String privateImageId;

	private String buildRecordId;

	private String tag;

	private String version;

	private String status;

	private String description;

	private String url;
	
	private String logdir;

	private String createBy;

	private String createTime;
	
	private boolean checked;
	
	/** 配置文件完整路径 */
	private String configFilePath;
	
	/** 镜像是否导入 */
	private String isImported;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrivateImageId() {
		return privateImageId;
	}

	public void setPrivateImageId(String privateImageId) {
		this.privateImageId = privateImageId;
	}

	public String getBuildRecordId() {
		return buildRecordId;
	}

	public void setBuildRecordId(String buildRecordId) {
		this.buildRecordId = buildRecordId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLogdir() {
		return logdir;
	}

	public void setLogdir(String logdir) {
		this.logdir = logdir;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "PrivateImageVersion [id=" + id + ", privateImageId="
				+ privateImageId + ", buildRecordId=" + buildRecordId
				+ ", tag=" + tag + ", version=" + version + ", status="
				+ status + ", description=" + description + ", url=" + url
				+ ", logdir=" + logdir + ", createBy=" + createBy
				+ ", createTime=" + createTime + ", checked=" + checked
				+ ", configFilePath=" + configFilePath + ", isImported="
				+ isImported + "]";
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public String getIsImported() {
		return isImported;
	}

	public void setIsImported(String isImported) {
		this.isImported = isImported;
	}

}
