package com.cmsz.paas.common.model.k8s.v1;

public class AzureFileVolumeSource {

	private String secretName;
	
	private String shareName;
	
	private boolean readOnly;
     
	public String getSecretName() {
		return secretName;
	}

	public void setSecretName(String secretName) {
		this.secretName = secretName;
	}

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public String toString() {
		return "AzureFileVolumeSource "
				+ "[secretName=" + secretName 
				+ ", shareName=" + shareName 
				+", readOnly="+ readOnly +"]";
	}
}
