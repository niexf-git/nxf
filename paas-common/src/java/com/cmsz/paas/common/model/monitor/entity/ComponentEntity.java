package com.cmsz.paas.common.model.monitor.entity;

public class ComponentEntity {
	
	private String componentName;
	private String status;
	
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ComponentEntity [componentName=" + componentName + ", status="
				+ status + "]";
	}

	
}
