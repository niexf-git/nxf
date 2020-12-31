package com.cmsz.paas.common.model.k8s.v1;

public class ISCSIVolumeSource {

	private String targetPortal;

	private String iqn;

	private int lun;
	
	private String iscsiInterface;

	private String fsType;

	private boolean readOnly;

	public String getTargetPortal() {
		return targetPortal;
	}

	public void setTargetPortal(String targetPortal) {
		this.targetPortal = targetPortal;
	}

	public String getIqn() {
		return iqn;
	}

	public void setIqn(String iqn) {
		this.iqn = iqn;
	}

	public int getLun() {
		return lun;
	}

	public void setLun(int lun) {
		this.lun = lun;
	}

	public String getIscsiInterface() {
		return iscsiInterface;
	}

	public void setIscsiInterface(String iscsiInterface) {
		this.iscsiInterface = iscsiInterface;
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
		return "ISCSIVolumeSource "
				+ "[targetPortal=" + targetPortal 
				+ ", iqn=" + iqn 
				+ ", lun=" + lun 
				+ ", iscsiInterface=" + iscsiInterface 
				+ ", fsType=" + fsType 
				+ ", readOnly=" + readOnly + "]";
	}

}
