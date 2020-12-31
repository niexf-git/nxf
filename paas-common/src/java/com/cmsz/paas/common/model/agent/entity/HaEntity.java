package com.cmsz.paas.common.model.agent.entity;

public class HaEntity {
	private String ismain;    //master/slave
	private String floadIp;   //ip
	private String componentName;  //kubeMaster/haproxy
	public String getIsmain() {
		return ismain;
	}
	public void setIsmain(String ismain) {
		this.ismain = ismain;
	}
	public String getFloadIp() {
		return floadIp;
	}
	public void setFloadIp(String floadIp) {
		this.floadIp = floadIp;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	@Override
	public String toString() {
		return "HaEntity [ismain=" + ismain + ", floadIp=" + floadIp
				+ ", componentName=" + componentName + "]";
	}
	
}
