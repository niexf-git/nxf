package com.cmsz.paas.common.model.agent.entity;

public class LabelEntity {
	/**
	 * 主机ip
	 */
	private String ip;
	/**
	 * 该主机需要设置的label值
	 */
	private String label;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "HostnameEntity "
				+ "[ip=" + ip 
				+ ", name=" + label  + "]";
	}
}
