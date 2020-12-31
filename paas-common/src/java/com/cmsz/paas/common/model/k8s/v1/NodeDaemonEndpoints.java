package com.cmsz.paas.common.model.k8s.v1;

public class NodeDaemonEndpoints {
	
	private DaemonEndpoint kubeletEndpoint;
	
	public DaemonEndpoint getKubeletEndpoint() {
		return kubeletEndpoint;
	}

	public void setKubeletEndpoint(DaemonEndpoint kubeletEndpoint) {
		this.kubeletEndpoint = kubeletEndpoint;
	}

	@Override
	public String toString() {
		return "NodeDaemonEndpoints"
				+ ", kubeletEndpoint=" + kubeletEndpoint + "]";
	}

}
