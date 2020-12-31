package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

public class ConfigFileEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ID
	private long id;
	
	private String appServiceVersionId;
	
	private String configmapName;
	
	private String configDir;
	
	private String content;
	
	@Override
	public String toString() {
		return "ConfigFileEntity "
				+ "[id=" + id 
				+ ", appServiceVersionId=" + appServiceVersionId
				+ ", configmapName=" + configmapName 
				+ ", configDir=" + configDir
				+ ", content=" + content + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppServiceVersionId() {
		return appServiceVersionId;
	}

	public void setAppServiceVersionId(String appServiceVersionId) {
		this.appServiceVersionId = appServiceVersionId;
	}

	public String getConfigmapName() {
		return configmapName;
	}

	public void setConfigmapName(String configmapName) {
		this.configmapName = configmapName;
	}

	public String getConfigDir() {
		return configDir;
	}

	public void setConfigDir(String configDir) {
		this.configDir = configDir;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	} 
}
