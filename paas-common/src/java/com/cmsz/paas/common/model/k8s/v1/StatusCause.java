package com.cmsz.paas.common.model.k8s.v1;

public class StatusCause {

	private String reason;

	@Override
	public String toString() {
		return "StatusCause [reason=" + reason + ", message=" + message
				+ ", field=" + field + "]";
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

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	private String message;

	private String field;
}
