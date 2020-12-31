package com.cmsz.paas.common.model.k8s.v1;

public class VolumeMount {

	private String name;

	private boolean readOnly;

	private String mountPath;
	
	private String subPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getMountPath() {
		return mountPath;
	}

	public void setMountPath(String mountPath) {
		this.mountPath = mountPath;
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

	@Override
	public String toString() {
		return "VolumeMount "
				+ "[name=" + name 
				+ ", readOnly=" + readOnly
				+ ", mountPath=" + mountPath 
				+ ", subPath="+ subPath +"]";
	}
}
