/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File IpaasToAppServiceEnvEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class IpaasToAppServiceEnvEntity {

	@ID
	private long id;

	private long publishEnvId;

	private long ipaasToServiceId;

	private String appServiceKey;

	/**
	 * 对应发布的环境环境变量 
	 */
	private PublishEnvEntity publisEnv;

	@Override
	public String toString() {
		return "IpaasToAppServiceEnvEntity "
				+ "[id=" + id 
				+ ", publishEnvId=" + publishEnvId 
				+ ", ipaasToServiceId=" + ipaasToServiceId 
				+ ", appServiceKey=" + appServiceKey 
				+ ", publisEnv=" + publisEnv + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPublishEnvId() {
		return publishEnvId;
	}

	public void setPublishEnvId(long publishEnvId) {
		this.publishEnvId = publishEnvId;
	}

	public long getIpaasToServiceId() {
		return ipaasToServiceId;
	}

	public void setIpaasToServiceId(long ipaasToServiceId) {
		this.ipaasToServiceId = ipaasToServiceId;
	}

	public PublishEnvEntity getPublisEnv() {
		return publisEnv;
	}

	public void setPublisEnv(PublishEnvEntity publisEnv) {
		this.publisEnv = publisEnv;
	}
	
	public String getAppServiceKey() {
		return appServiceKey;
	}

	public void setAppServiceKey(String appServiceKey) {
		this.appServiceKey = appServiceKey;
	}
}
