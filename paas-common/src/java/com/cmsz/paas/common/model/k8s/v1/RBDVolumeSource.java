package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class RBDVolumeSource {

	private String[] monitors;

	private String image;

	private String fsType;
	
	private String pool;

	private String user;

	private String keyring;

	private LocalObjectReference secretRef;

	private boolean readOnly;

	public String[] getMonitors() {
		return monitors;
	}

	public void setMonitors(String[] monitors) {
		this.monitors = monitors;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFsType() {
		return fsType;
	}

	public void setFsType(String fsType) {
		this.fsType = fsType;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getKeyring() {
		return keyring;
	}

	public void setKeyring(String keyring) {
		this.keyring = keyring;
	}

	public LocalObjectReference getSecretRef() {
		return secretRef;
	}

	public void setSecretRef(LocalObjectReference secretRef) {
		this.secretRef = secretRef;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public String toString() {
		return "RBDVolumeSource "
				+ "[monitors=" + Arrays.toString(monitors)
				+ ", image=" + image 
				+ ", fsType=" + fsType
				+ ", pool=" + pool
				+ ", user=" + user
				+ ", keyring=" + keyring 
				+ ", secretRef=" + secretRef
				+ ", readOnly=" + readOnly + "]";
	}
}
