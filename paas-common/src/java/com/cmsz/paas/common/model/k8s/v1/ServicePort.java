package com.cmsz.paas.common.model.k8s.v1;

public class ServicePort {

	private String name;

	private String protocol;

	private int port;

	private String targetPort;

	private int nodePort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(String targetPort) {
		this.targetPort = targetPort;
	}

	public int getNodePort() {
		return nodePort;
	}

	public void setNodePort(int nodePort) {
		this.nodePort = nodePort;
	}

	@Override
	public String toString() {
		return "ServicePort"
				+ " [name=" + name 
				+ ", protocol=" + protocol
				+ ", port=" + port 
				+ ", targetPort=" + targetPort
				+ ", nodePort=" + nodePort + "]";
	}

}
