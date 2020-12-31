package com.cmsz.paas.common.model.k8s.v1;

public class GlusterfsVolumeSource {

	private String endpoints;

	private String path;

	private boolean readOnly;

	public String getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(String endpoints) {
		this.endpoints = endpoints;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public String toString() {
		return "GlusterfsVolumeSource "
				+ "[endpoints=" + endpoints 
				+ ", path=" + path 
				+ ", readOnly=" + readOnly + "]";
	}

}
