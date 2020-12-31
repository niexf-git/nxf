package com.cmsz.paas.web.build.model;

import java.util.List;

/**
 * 构建实体，主要用于构建的创建和修改
 * 
 * @author liaohw
 * @date 2016-4-1
 */
public class BuildEntity {

	/** 唯一标识 */
	private String id;

	/** 应用id,从session中取数据 */
	private String appId;

	/** 创建人,从session中取数据 */
	private String creator;

	/** 构建名称 */
	private String name;

	/** Dockerfile文件路径 */
	private String dockerFilePath;

	/** 描述 */
	private String description;

	/** svn库列表 */
	private List<RepositoryInfo> repositoryInfo;

	/** 执行命令列表 */
	private List<String> executeCommand;

	/** 构建类型 1-svn，2-git */
	private String type;

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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDockerFilePath() {
		return dockerFilePath;
	}

	public void setDockerFilePath(String dockerFilePath) {
		this.dockerFilePath = dockerFilePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RepositoryInfo> getRepositoryInfo() {
		return repositoryInfo;
	}

	public void setRepositoryInfo(List<RepositoryInfo> repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}

	public List<String> getExecuteCommand() {
		return executeCommand;
	}

	public void setExecuteCommand(List<String> executeCommand) {
		this.executeCommand = executeCommand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BuildEntity [id=" + id + ", appId=" + appId + ", creator="
				+ creator + ", name=" + name + ", dockerFilePath="
				+ dockerFilePath + ", description=" + description
				+ ", repositoryInfo=" + repositoryInfo + ", executeCommand="
				+ executeCommand + ", type=" + type + "]";
	}

}
