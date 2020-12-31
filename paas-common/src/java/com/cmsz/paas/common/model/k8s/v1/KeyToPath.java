package com.cmsz.paas.common.model.k8s.v1;

public class KeyToPath {

	private String key;
	
	private String path;
	
	private int mode;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return "KeyToPath "
				+ "[key=" + key
				+ ", path=" + path
				+ ", mode=" + mode+ "]";
	}
	
}
