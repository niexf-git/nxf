package com.cmsz.paas.common.model.k8s.v1;

public class NFSVolumeSource {
	private String server;

	private String path;

	private boolean readOnly;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
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
		return "NFSVolumeSource "
				+ "[server=" + server 
				+ ", path=" + path
				+ ", readOnly=" + readOnly + "]";
	}

}
