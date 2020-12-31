package com.cmsz.paas.common.model.k8s.v1;

public class HostPathVolumeSource {

	private String path;

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "HostPathVolumeSource "
				+ "[path=" + path + "]";
	}
}
