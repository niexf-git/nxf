package com.cmsz.paas.common.model.k8s.v1;

public class EmptyDirVolumeSource {
	private String medium;

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getMedium() {
		return medium;
	}

	@Override
	public String toString() {
		return "EmptyDirVolumeSource "
				+ "[medium=" + medium + "]";
	}
}
