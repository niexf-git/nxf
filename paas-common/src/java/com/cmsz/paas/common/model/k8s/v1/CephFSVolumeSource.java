package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class CephFSVolumeSource {
	
	private String [] monitors;
    
    private String path;
    
    private String user;
    
    private String secretFile;
    
    private LocalObjectReference secretRef;
    
    private boolean readOnly; 
    
    public String[] getMonitors() {
		return monitors;
	}

	public void setMonitors(String[] monitors) {
		this.monitors = monitors;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSecretFile() {
		return secretFile;
	}

	public void setSecretFile(String secretFile) {
		this.secretFile = secretFile;
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
		return "CephFSVolumeSource "
				+ "[monitors=" +Arrays.toString(monitors) 
				+ ", path=" + path 
				+ ", user="+ user 
				+ ", secretFile="+secretFile
				+ ", secretRef"+secretRef
				+ ", readOnly="+readOnly+"]";
	}
	
}
