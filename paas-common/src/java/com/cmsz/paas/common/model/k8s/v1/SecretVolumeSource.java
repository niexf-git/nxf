package com.cmsz.paas.common.model.k8s.v1;

public class SecretVolumeSource {

	private String secretName;
	
	private KeyToPath[] items;
	
	private int defaultMode;

	public String getSecretName() {
		return secretName;
	}



	public void setSecretName(String secretName) {
		this.secretName = secretName;
	}



	public KeyToPath[] getItems() {
		return items;
	}



	public void setItems(KeyToPath[] items) {
		this.items = items;
	}



	public int getDefaultMode() {
		return defaultMode;
	}



	public void setDefaultMode(int defaultMode) {
		this.defaultMode = defaultMode;
	}



	@Override
	public String toString() {
		return "SecretVolumeSource "
				+ "[secretName=" + secretName 
				+ ", items=" + items
				+ ", defaultMode=" + defaultMode +"]";
	}
}
