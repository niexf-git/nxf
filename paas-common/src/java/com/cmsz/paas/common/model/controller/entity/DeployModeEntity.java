package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

public class DeployModeEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	@ID
	private int id;
	
	private int type;
	
	private int instanceNum;
	
	private String serviceId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(int instanceNum) {
		this.instanceNum = instanceNum;
	}

	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		return "InstanceNumEntity [id=" + id + ", type=" + type
				+ ", instanceNum=" + instanceNum + ", serviceId=" + serviceId
				+ "]";
	}
}
