package com.cmsz.paas.common.model.k8s.v1;

public class DaemonEndpoint {
	
	private int Port;
	
	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

	@Override
	public String toString() {
		return "DaemonEndpoint"
				+ ", Port=" + Port + "]";
	}
}
