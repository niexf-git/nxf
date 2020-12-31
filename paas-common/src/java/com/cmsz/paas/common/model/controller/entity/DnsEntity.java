package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.List;

public class DnsEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String appName;
	
	private List<DisasterToleranceConfEntity> dnsInfo;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public List<DisasterToleranceConfEntity> getDnsInfo() {
		return dnsInfo;
	}
	public void setDnsInfo(List<DisasterToleranceConfEntity> dnsInfo) {
		this.dnsInfo = dnsInfo;
	}
	@Override
	public String toString() {
		return "DnsEntity [appName=" + appName + ", dnsInfo=" + dnsInfo + "]";
	}
}
