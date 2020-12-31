package com.cmsz.paas.common.model.k8s.v1;

public class EndpointPort {

	private String name;

	private int port;

	private String protocol;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public String toString() {
		return "EndpointPort "
				+ "[name=" + name 
				+ ", port=" + port 
				+ ", protocol=" + protocol + "]";
	}
}
