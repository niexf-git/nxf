/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File ServicePort.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-29
 */
public class ServicePortEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long k8sServiceId;

	private int containerPort;

	private int haproxyPort;

	private String name;
	
	private String label;


	@Override
	public String toString() {
		return "ServicePortEntity [id=" + id + ", k8sServiceId=" + k8sServiceId
				+ ", containerPort=" + containerPort + ", haproxyPort="
				+ haproxyPort + ", name=" + name + ", label=" + label + "]";
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getK8sServiceId() {
		return k8sServiceId;
	}


	public void setK8sServiceId(long k8sServiceId) {
		this.k8sServiceId = k8sServiceId;
	}


	public int getContainerPort() {
		return containerPort;
	}


	public void setContainerPort(int containerPort) {
		this.containerPort = containerPort;
	}


	public int getHaproxyPort() {
		return haproxyPort;
	}


	public void setHaproxyPort(int haproxyPort) {
		this.haproxyPort = haproxyPort;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
