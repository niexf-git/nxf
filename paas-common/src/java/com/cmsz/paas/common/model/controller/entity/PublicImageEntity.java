/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File PublicImageEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class PublicImageEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	
	private String name;

	private String type;

	private String startCmd;

	private String logDir;
	
	private String configDir;

	private String description;
	
	/**
	 * 仓库地址
	 */
	private String registryUrl;

	/**
	 * 公有镜像的所有版本
	 */
	private List<PublicImageVersionEntity> versions;

	/**
	 * 所选运行版本
	 */
	private PublicImageVersionEntity runningVersion;

	@Override
	public String toString() {
		return "PublicImageEntity "
				+ "[id=" + id 
				+ ", name=" + name 
				+ ", type=" + type 
				+ ", startCmd=" + startCmd 
				+ ", logDir=" + logDir 
				+ ", configDir=" + configDir
				+ ", description=" + description 
				+ ", registryUrl=" + registryUrl 
				+ ", versions=" + versions 
				+ ", runningVersion="+runningVersion+"]";
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
		PublicImageEntity other = (PublicImageEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getConfigDir() {
		return configDir;
	}

	public void setConfigDir(String configDir) {
		this.configDir = configDir;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PublicImageVersionEntity> getVersions() {
		return versions;
	}

	public void setVersions(List<PublicImageVersionEntity> versions) {
		this.versions = versions;
	}

	public PublicImageVersionEntity getRunningVersion() {
		return runningVersion;
	}

	public void setRunningVersion(PublicImageVersionEntity runningVersion) {
		this.runningVersion = runningVersion;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}
	
}
