package com.cmsz.paas.common.model.controller.entity;

public class Event {
	private  String type;
	
	private String reason;
	
	private String message;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "Event [type=" + type + ", reason=" + reason + ", message=" + message + "]";
	}
}
