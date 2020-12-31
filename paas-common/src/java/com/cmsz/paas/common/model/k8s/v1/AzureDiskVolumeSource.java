package com.cmsz.paas.common.model.k8s.v1;

public class AzureDiskVolumeSource {
 
	private String diskName;
	
	private String diskURI;
	
	private String cachingMode;

	private String fsType;
	
	private boolean readOnly;
	
	public String getDiskName() {
		return diskName;
	}

	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	public String getDiskURI() {
		return diskURI;
	}

	public void setDiskURI(String diskURI) {
		this.diskURI = diskURI;
	}

	public String getCachingMode() {
		return cachingMode;
	}

	public void setCachingMode(String cachingMode) {
		this.cachingMode = cachingMode;
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
		return "AzureDiskVolumeSource"
				+ " [diskName=" + diskName 
				+ ", diskURI=" + diskURI 
				+ ", cachingMode="+ cachingMode
				+ ", fsType="+fsType
				+ ", readOnly="+readOnly+"]";
	}

}
