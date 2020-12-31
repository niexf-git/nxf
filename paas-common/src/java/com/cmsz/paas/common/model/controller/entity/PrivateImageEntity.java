/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppImageEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class PrivateImageEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	
	private long appId;

	/**
	 * 应用名
	 */
	private String appName;

	private String buildId;

	/**
	 * 构建名
	 */
	private String buildName;
	
	/**
	 * 仓库账号
	 */
	private String registryUser;

	/**
	 * 仓库密码
	 */
	private String registryPswd;

	private String name;

	private String description;

	private String createBy;

	private Date createTime;
	
	/**
	 * 仓库地址
	 */
	private String registryUrl;

	/**
	 * 所属应用
	 */
	private AppEntity app;

	/**
	 * 所有私有镜像版本
	 */
	private List<PrivateImageVersionEntity> versions;

	/**
	 * 所选运行版本
	 */
	private PrivateImageVersionEntity runningVersion;
	
	/**
	 * 镜像id
	 */
	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "PrivateImageEntity "
				+ "[id=" + id 
				+ ",pid=" + pid
				+ ", appId=" + appId
				+ ", appName=" + appName 
				+ ", buildId=" + buildId
				+ ", buildName=" + buildName 
				+ ", registryUser=" + registryUser
				+ ", registryPswd=" + registryPswd
				+ ", name=" + name
				+ ", description=" + description 
				+ ", createBy=" + createBy 
				+ ", createTime=" + createTime 
				+ ", registryUrl=" + registryUrl
				+ ", app=" + app 
				+ ", versions=" + versions 
				+ ", runningVersion="+runningVersion+"]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public AppEntity getApp() {
		return app;
	}

	public void setApp(AppEntity app) {
		this.app = app;
	}

	public List<PrivateImageVersionEntity> getVersions() {
		return versions;
	}

	public void setVersions(List<PrivateImageVersionEntity> versions) {
		this.versions = versions;
	}

	public PrivateImageVersionEntity getRunningVersion() {
		return runningVersion;
	}

	public void setRunningVersion(PrivateImageVersionEntity runningVersion) {
		this.runningVersion = runningVersion;
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

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrivateImageEntity other = (PrivateImageEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
