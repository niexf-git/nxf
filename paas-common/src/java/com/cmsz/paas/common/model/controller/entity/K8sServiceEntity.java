/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File ServiceEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

/**
 * ServiceEntity
 *
 * @author hehm
 *
 * @date 2016-3-23
 */
public class K8sServiceEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String serviceId;

	private String name;

	private int type;

	private int isExternal;

	/**
	 * 外部访问地址
	 */
	private String url;

	private int protocolType;

	/**
	 * 对应的service端口
	 */
	private List<ServicePortEntity> ports;

	@Override
	public String toString() {
		return "IpaasToAppServiceEnvEntity "
				+ "[id=" + id 
				+ ", serviceId=" + serviceId 
				+ ", name=" + name 
				+ ", type=" + type 
				+ ", isExternal=" + isExternal 
				+ ", url=" + url 
				+ ", protocolType=" + protocolType 
				+ ", ports=" + ports + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(int isExternal) {
		this.isExternal = isExternal;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public List<ServicePortEntity> getPorts() {
		return ports;
	}

	public void setPorts(List<ServicePortEntity> ports) {
		this.ports = ports;
	}
}
