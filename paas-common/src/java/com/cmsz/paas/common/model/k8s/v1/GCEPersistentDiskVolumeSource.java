package com.cmsz.paas.common.model.k8s.v1;

public class GCEPersistentDiskVolumeSource {

	private String pdName;

	private String fsType;

	private int partition;

	private boolean readOnly;

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	public String getPdName() {
		return pdName;
	}

	public void setFsType(String fsType) {
		this.fsType = fsType;
	}

	public String getFsType() {
		return fsType;
	}

	public void setPartition(int partition) {
		this.partition = partition;
	}

	public int getPartition() {
		return partition;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	@Override
	public String toString() {
		return "GCEPersistentDiskVolumeSource"
				+ " [pdName=" + pdName 
				+ ", fsType=" + fsType 
				+ ", partition=" + partition 
				+ ", readOnly=" + readOnly + "]";
	}
}
