package com.cmsz.paas.common.model.k8s.v1;

public class AttachedVolume {

	private String name;
	
	private String devicePath;
	
	@Override
	public String toString() {
		return "AttachedVolume"
				+ " [name=" + name
				+ ", devicePath=" + devicePath + "]";
	}
}
