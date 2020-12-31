package com.cmsz.paas.common.model.k8s.v1;

public class PersistentVolumeClaimVolumeSource {
	private String claimName;

	private boolean readOnly;

	public String getClaimName() {
		return claimName;
	}

	public void setClaimName(String claimName) {
		this.claimName = claimName;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public String toString() {
		return "PersistentVolumeClaimVolumeSource "
				+ "[claimName=" + claimName
				+ ", readOnly=" + readOnly + "]";
	}

}
