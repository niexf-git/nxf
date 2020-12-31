/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File EnvConfigEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * EnvConfigEntity
 *
 * @author hehm
 *
 * @date 2016-3-23
 */
public class EnvConfigEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String appServiceVersionId;

	private String configKey;

	private String configValue;

	@Override
	public String toString() {
		return "EnvConfigEntity "
				+ "[id=" + id 
				+ ", appServiceVersionId=" + appServiceVersionId
				+ ", configKey=" + configKey 
				+ ", configValue=" + configValue + "]";
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

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
}
