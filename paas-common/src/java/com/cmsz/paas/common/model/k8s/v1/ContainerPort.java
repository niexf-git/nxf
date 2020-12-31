package com.cmsz.paas.common.model.k8s.v1;

public class ContainerPort {

	private String name;

	private int hostPort;

	private int containerPort;

	private String protocol;

	private String hostIP;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}

	public int getHostPort() {
		return hostPort;
	}

	public void setContainerPort(int containerPort) {
		this.containerPort = containerPort;
	}

	public int getContainerPort() {
		return containerPort;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public String getHostIP() {
		return hostIP;
	}
	
	@Override
	public String toString() {
		return "ContainerPort"
				+ " [name=" + name 
				+ ", hostPort=" + hostPort 
				+ ", containerPort="+ containerPort 
				+ ", protocol="+ protocol 
				+ ", hostIP="+ hostIP + "]";
	}
}
