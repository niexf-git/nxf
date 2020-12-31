package com.cmsz.paas.common.model.k8s.v1;

public class EventSource {

	private String component;
	
	private String host;

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "EventSource [component=" + component + ", host=" + host + "]";
	}
}
