package com.cmsz.paas.common.model.k8s.v1;

public class LoadBalancerIngress {

	private String ip;

	private String hostname;

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getHostname() {
		return hostname;
	}

	@Override
	public String toString() {
		return "LoadBalancerIngress "
				+ "[ip=" + ip 
				+ ", hostname=" + hostname + "]";
	}
}
