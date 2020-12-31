package com.cmsz.paas.common.model.k8s.v1;

public class ContainerStateWaiting {
	private String reason;

	private String message;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ContainerStateWaiting "
				+ "[reason=" + reason 
				+ ", message=" + message + "]";
	}
}
