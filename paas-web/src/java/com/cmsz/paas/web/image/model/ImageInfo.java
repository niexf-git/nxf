package com.cmsz.paas.web.image.model;

/**
 * The Class ImageInfo.
 * 
 * @author li.lv 2015-4-11
 */
public class ImageInfo {

	/** 基础镜像Id */
	private Long id;
	/** 镜像名称 */
	private String name;
	/** 运行环境 */
	private String runningEnv;
	/** 部署路径 */
	private String deployPath;
	/** 启动命令 */
	private String startCommand;
	/** 创建时间 */
	private String createTime;
	/** 日志目录 */
	private String logDir;
	/** 描述信息 */
	private String description;
	/** 是否在用 */
	private String isUsed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRunningEnv() {
		return runningEnv;
	}

	public void setRunningEnv(String runningEnv) {
		this.runningEnv = runningEnv;
	}

	public String getDeployPath() {
		return deployPath;
	}

	public void setDeployPath(String deployPath) {
		this.deployPath = deployPath;
	}

	public String getStartCommand() {
		return startCommand;
	}

	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	@Override
	public String toString() {
		return "ImageInfo [id=" + id + ", name=" + name + ", runningEnv="
				+ runningEnv + ", deployPath=" + deployPath + ", startCommand="
				+ startCommand + ", createTime=" + createTime + ", logDir="
				+ logDir + ", description=" + description + ", isUsed="
				+ isUsed + "]";
	}

}
