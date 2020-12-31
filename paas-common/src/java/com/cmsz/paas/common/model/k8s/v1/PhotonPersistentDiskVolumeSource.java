package com.cmsz.paas.common.model.k8s.v1;

public class PhotonPersistentDiskVolumeSource {

	private String pdID;
	
	private String fsType;
	
	public String getPdID() {
		return pdID;
	}

	public void setPdID(String pdID) {
		this.pdID = pdID;
	}

	public String getFsType() {
		return fsType;
	}

	public void setFsType(String fsType) {
		this.fsType = fsType;
	}

	@Override
	public String toString() {
		return "PhotonPersistentDiskVolumeSource"
				+ " [pdID=" + pdID 
				+ ", fsType=" + fsType + "]";
	}
}
