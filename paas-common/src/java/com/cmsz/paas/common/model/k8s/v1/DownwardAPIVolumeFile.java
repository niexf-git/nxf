package com.cmsz.paas.common.model.k8s.v1;

public class DownwardAPIVolumeFile {

	private String path;
	
	private ObjectFieldSelector fieldRef;
	
	private ResourceFieldSelector resourceFieldRef;
	
	private int mode;
	
	@Override
	public String toString() {
		return "DownwardAPIVolumeFile "
				+ "[path=" + path 
				+ ", fieldRef=" + fieldRef
				+ ", resourceFieldRef=" + resourceFieldRef
				+ ", mode=" + mode  + "]";
	}
	
}
