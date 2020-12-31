package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

public class PersistentVolumeClaimStatus {

	private String phase;
	
	private String[] accessModes;
	
	private Map<String,String> capacity;

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String[] getAccessModes() {
		return accessModes;
	}

	public void setAccessModes(String[] accessModes) {
		this.accessModes = accessModes;
	}

	public Map<String, String> getCapacity() {
		return capacity;
	}

	public void setCapacity(Map<String, String> capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "PersistentVolumeClaimStatus"
				+ " [phase=" + phase 
				+ ", accessModes=" + accessModes
				+ ", capacity=" + capacity + "]";
	}
}
