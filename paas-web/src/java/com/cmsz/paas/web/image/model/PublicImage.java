package com.cmsz.paas.web.image.model;

import java.util.List;

/**
 * 公共镜像
 * 
 * @author lin.my 2016-4-7
 */
public class PublicImage {

	private String id;

	private String name;

	private String type;

	private String deployPath;

	private String startCmd;

	private String logDir;

	private String description;

	private String registryUrl;

	private String versionId; // 版本ID

	private String version; // 公共镜像列表需要显示版本

	private String status; // 公共镜像列表需要显示on/off状态

	private List<PublicImageVersion> versions;

	private PublicImageVersion runningVersion;

	private boolean checked = false;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeployPath() {
		return deployPath;
	}

	public void setDeployPath(String deployPath) {
		this.deployPath = deployPath;
	}

	public String getStartCmd() {
		return startCmd;
	}

	public void setStartCmd(String startCmd) {
		this.startCmd = startCmd;
	}

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}

	public List<PublicImageVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<PublicImageVersion> versions) {
		this.versions = versions;
	}

	public PublicImageVersion getRunningVersion() {
		return runningVersion;
	}

	public void setRunningVersion(PublicImageVersion runningVersion) {
		this.runningVersion = runningVersion;
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

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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

	@Override
	public String toString() {
		return "PublicImage [id=" + id + ", name=" + name + ", type=" + type
				+ ", deployPath=" + deployPath + ", startCmd=" + startCmd
				+ ", logDir=" + logDir + ", description=" + description
				+ ", registryUrl=" + registryUrl + ", versionId=" + versionId
				+ ", version=" + version + ", status=" + status + ", versions="
				+ versions + ", runningVersion=" + runningVersion
				+ ", checked=" + checked + ", configFilePath=" + configFilePath
				+ ", isImported=" + isImported + "]";
	}

}
