package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

public class FlexVolumeSource {

	private String driver;
	
	private String fsType;
	
	private LocalObjectReference secretRef;
	
	private boolean readOnly;
	
	private Map<String,String> options;
	
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getFsType() {
		return fsType;
	}

	public void setFsType(String fsType) {
		this.fsType = fsType;
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

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	
	@Override
	public String toString() {
		return "FlexVolumeSource "
				+ "[driver=" + driver 
				+ ", fsType=" + fsType 
				+", secretRef="+ secretRef 
				+", readOnly="+readOnly
				+", options"+options+"]";
	}
}
