/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File PublishEnvEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-24
 */
public class PublishEnvEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String ipaasServiceId;

	private String configKey;

	private String configValue;

	@Override
	public String toString() {
		return "PublishEnvEntity "
				+ "[id=" + id 
				+ ", ipaasServiceId=" + ipaasServiceId 
				+ ", configKey=" + configKey
				+ ", configValue=" + configValue + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIpaasServiceId() {
		return ipaasServiceId;
	}

	public void setIpaasServiceId(String ipaasServiceId) {
		this.ipaasServiceId = ipaasServiceId;
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
