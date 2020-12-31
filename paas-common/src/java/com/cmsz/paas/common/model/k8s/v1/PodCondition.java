package com.cmsz.paas.common.model.k8s.v1;

public class PodCondition {

	private String type;

	private String status;
	
	private String lastProbeTime;
	
	private String lastTransitionTime;
	
	private String reason;
	
	private String message;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastProbeTime() {
		return lastProbeTime;
	}

	public void setLastProbeTime(String lastProbeTime) {
		this.lastProbeTime = lastProbeTime;
	}

	public String getLastTransitionTime() {
		return lastTransitionTime;
	}

	public void setLastTransitionTime(String lastTransitionTime) {
		this.lastTransitionTime = lastTransitionTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PodCondition"
				+ " [type=" + type 
				+ ", status=" + status
				+ ", lastProbeTime=" + lastProbeTime
				+ ", lastTransitionTime=" + lastTransitionTime
				+ ", reason=" + reason
				+ ", message=" + message + "]";
	}
}
