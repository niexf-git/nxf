package com.cmsz.paas.common.model.k8s.v1;

public class DownwardAPIVolumeSource {

	private DownwardAPIVolumeFile[] items;
	
	private int defaultMode;
	
	@Override
	public String toString() {
		return "DownwardAPIVolumeSource "
				+ "[items=" + items 
				+ ", defaultMode=" + defaultMode  + "]";
	}
}
