package com.cmsz.paas.common.model.k8s.v1;

public class VsphereVirtualDiskVolumeSource {

	private String volumePath;
	
	private String fsType;
     
	public String getVolumePath() {
		return volumePath;
	}

	public void setVolumePath(String volumePath) {
		this.volumePath = volumePath;
	}

	public String getFsType() {
		return fsType;
	}

	public void setFsType(String fsType) {
		this.fsType = fsType;
	}
	
	@Override
	public String toString() {
		return "VsphereVirtualDiskVolumeSource "
				+ "[volumePath=" + volumePath 
				+ ", fsType=" + fsType +"]";
	}


}
