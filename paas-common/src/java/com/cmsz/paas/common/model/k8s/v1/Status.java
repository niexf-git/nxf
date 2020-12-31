package com.cmsz.paas.common.model.k8s.v1;

public class Status {

	private String kind;

	private String apiVersion;

	private String status;

	private String message;

	private String reason;

	private int code;

	private StatusDetails details;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public StatusDetails getDetails() {
		return details;
	}

	public void setDetails(StatusDetails details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Status [kind=" + kind + ", apiVersion=" + apiVersion
				+ ", status=" + status + ", message=" + message + ", reason="
				+ reason + ", code=" + code + ", details=" + details + "]";
	}
}
