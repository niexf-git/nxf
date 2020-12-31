package com.cmsz.paas.common.model.k8s.v1;

public class FlockerVolumeSource {

	private String datasetName;
	
	private String datasetUUID;
	
	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getDatasetUUID() {
		return datasetUUID;
	}

	public void setDatasetUUID(String datasetUUID) {
		this.datasetUUID = datasetUUID;
	}

	@Override
	public String toString() {
		return "FlockerVolumeSource"
				+ " [datasetName=" + datasetName
				+ ", datasetUUID=" + datasetUUID + "]";
	}
}
