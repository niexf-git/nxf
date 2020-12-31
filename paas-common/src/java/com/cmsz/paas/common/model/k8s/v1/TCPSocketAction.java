package com.cmsz.paas.common.model.k8s.v1;

public class TCPSocketAction {

	private String port;

	public void setPort(String port) {
		this.port = port;
	}

	public String getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "TCPSocketAction "
				+ "[port=" + port + "]";
	}
}
