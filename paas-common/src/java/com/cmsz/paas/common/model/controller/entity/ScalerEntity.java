/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File ScalerEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class ScalerEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String appServiceId;

	private int cpuTarget;

	private int instMin;

	private int instMax;

	@Override
	public String toString() {
		return "ScalerEntity "
				+ "[id=" + id 
				+ ", appServiceId=" + appServiceId
				+ ", cpuTarget=" + cpuTarget 
				+ ", instMin=" + instMin
				+ ", instMax=" + instMax + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppServiceId() {
		return appServiceId;
	}

	public void setAppServiceId(String appServiceId) {
		this.appServiceId = appServiceId;
	}

	public int getCpuTarget() {
		return cpuTarget;
	}

	public void setCpuTarget(int cpuTarget) {
		this.cpuTarget = cpuTarget;
	}

	public int getInstMin() {
		return instMin;
	}

	public void setInstMin(int instMin) {
		this.instMin = instMin;
	}

	public int getInstMax() {
		return instMax;
	}

	public void setInstMax(int instMax) {
		this.instMax = instMax;
	}
}
