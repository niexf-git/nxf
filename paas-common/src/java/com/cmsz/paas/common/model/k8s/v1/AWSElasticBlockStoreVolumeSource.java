package com.cmsz.paas.common.model.k8s.v1;

public class AWSElasticBlockStoreVolumeSource {
	private String volumeID;

	private String fsType;

	private int partition;

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

	public int getPartition() {
		return partition;
	}

	public void setPartition(int partition) {
		this.partition = partition;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public String toString() {
		return "AWSElasticBlockStoreVolumeSource "
				+ "[volumeID=" + volumeID
				+ ", fsType=" + fsType 
				+ ", partition=" + partition
				+ ", readOnly=" + readOnly + "]";
	}
}
