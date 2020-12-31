package com.cmsz.paas.web.application.model;

import java.util.List;

public class DNSModel {
	private long appId;
	
	private String appName;
	
	private String hostIpName;
	
	private String spareIpName;
	
	private List<DNSInfo> dnsList;

	
	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getHostIpName() {
		return hostIpName;
	}

	public void setHostIpName(String hostIpName) {
		this.hostIpName = hostIpName;
	}

	public String getSpareIpName() {
		return spareIpName;
	}

	public void setSpareIpName(String spareIpName) {
		this.spareIpName = spareIpName;
	}

	public List<DNSInfo> getDnsList() {
		return dnsList;
	}

	public void setDnsList(List<DNSInfo> dnsList) {
		this.dnsList = dnsList;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
}
