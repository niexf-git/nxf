package com.cmsz.paas.common.model.agent.entity;

import java.util.List;

public class ComponentEntity {
	
	private String componentName;
	
	private int port;
	
	private String status;
	
	private HaEntity haentity;
	
	public HaEntity getHaentity() {
		return haentity;
	}
	
	public void setHaentity(HaEntity haentity) {
		this.haentity = haentity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
	
	@Override
	public String toString() {
		return "ComponentEntity [componentName=" + componentName + ", port="
				+ port + ", status=" + status + ", haentity=" + haentity + "]";
	}

}
