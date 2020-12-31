package com.cmsz.paas.common.model.k8s.v1;

public class FCVolumeSource {
	
	private String targetWWNs;
	
	private int lun;
	
	private String fsType;
	
	private boolean readOnly;
     
	public String getTargetWWNs() {
		return targetWWNs;
	}

	public void setTargetWWNs(String targetWWNs) {
		this.targetWWNs = targetWWNs;
	}

	public int getLun() {
		return lun;
	}

	public void setLun(int lun) {
		this.lun = lun;
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
		return "FCVolumeSource "
				+ "[targetWWNs=" + targetWWNs 
				+ ", lun=" + lun 
				+", fsType="+ fsType 
				+", readOnly="+readOnly+"]";
	}

}
