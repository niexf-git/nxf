package com.cmsz.paas.web.appserviceinst.model;

public class EventEntity {
	
	/** 事件类型 */
	private  String type;
	
	/** 原因 */
	private String reason;
	
	/** 详细描述  */
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
