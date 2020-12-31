package com.cmsz.paas.common.model.k8s.v1;

public class CinderVolumeSource {
	
	private String volumeID;
    
    private String fsType;
    
    private boolean readOnly;
    
    public String getVolumeID() {
		return volumeID;
	}

	public void setVolumeID(String volumeID) {
		this.volumeID = volumeID;
	}

	public String getFsType() {
		return fsType;
	}

	public void setFsType(String fsType) {
		this.fsType = fsType;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	@Override
	public String toString() {
		return "CinderVolumeSource "
				+ "[volumeID=" + volumeID 
				+ ", fsType=" + fsType 
				+", readOnly="+ readOnly +"]";
	}

}
