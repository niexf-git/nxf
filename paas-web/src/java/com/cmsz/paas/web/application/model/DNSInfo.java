package com.cmsz.paas.web.application.model;

public class DNSInfo {

	//域名
	private String domainName;
	
	//主IP
	private String hostIp;
	
	//备IP
	private String spareIp;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getSpareIp() {
		return spareIp;
	}

	public void setSpareIp(String spareIp) {
		this.spareIp = spareIp;
	}
	
	
	
}
