package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class StatusDetails {

	private String name;

	private String kind;

	private StatusCause[] causes;

	private int retryAfterSeconds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public StatusCause[] getCauses() {
		return causes;
	}

	public void setCauses(StatusCause[] causes) {
		this.causes = causes;
	}

	public int getRetryAfterSeconds() {
		return retryAfterSeconds;
	}

	public void setRetryAfterSeconds(int retryAfterSeconds) {
		this.retryAfterSeconds = retryAfterSeconds;
	}

	@Override
	public String toString() {
		return "StatusDetails [name=" + name + ", kind=" + kind + ", causes="
				+ Arrays.toString(causes) + ", retryAfterSeconds="
				+ retryAfterSeconds + "]";
	}
}
