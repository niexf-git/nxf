/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File IpaasToAppServiceEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class IpaasToAppServiceEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String ipaasServiceId;

	private String appServiceId;

	private String bindStr;

	/**
	 * 关联的基础服务
	 */
	private IpaasServiceEntity ipaas;

	/**
	 * 关联的应用服务
	 */
	private AppServiceEntity appService;

	/**
	 * 关联的环境变量
	 */
	private List<IpaasToAppServiceEnvEntity> envs;

	@Override
	public String toString() {
		return "IpaasToAppServiceEntity "
				+ "[id=" + id 
				+ ", ipaasServiceId=" + ipaasServiceId 
				+ ", appServiceId=" + appServiceId
				+ ", bindStr=" + bindStr 
				+ ", ipaas=" + ipaas 
				+ ", appService=" + appService 
				+ ", envs=" + envs + "]";
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

	public String getAppServiceId() {
		return appServiceId;
	}

	public void setAppServiceId(String appServiceId) {
		this.appServiceId = appServiceId;
	}

	public String getBindStr() {
		return bindStr;
	}

	public void setBindStr(String bindStr) {
		this.bindStr = bindStr;
	}

	public IpaasServiceEntity getIpaas() {
		return ipaas;
	}

	public void setIpaas(IpaasServiceEntity ipaas) {
		this.ipaas = ipaas;
	}

	public AppServiceEntity getAppService() {
		return appService;
	}

	public void setAppService(AppServiceEntity appService) {
		this.appService = appService;
	}

	public List<IpaasToAppServiceEnvEntity> getEnvs() {
		return envs;
	}

	public void setEnvs(List<IpaasToAppServiceEnvEntity> envs) {
		this.envs = envs;
	}
}
