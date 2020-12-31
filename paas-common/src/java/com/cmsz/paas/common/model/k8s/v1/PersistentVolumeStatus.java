package com.cmsz.paas.common.model.k8s.v1;

public class PersistentVolumeStatus {

	private String phase;
	
	private String message;
	
	private String reason;

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String toString() {
		return "PersistentVolumeStatus "
				+ "[phase=" + phase 
				+ ", message=" + message 
				+ ", reason=" + reason + "]";
	}
}
