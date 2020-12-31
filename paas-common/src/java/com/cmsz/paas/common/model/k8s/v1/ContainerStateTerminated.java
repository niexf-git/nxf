package com.cmsz.paas.common.model.k8s.v1;

public class ContainerStateTerminated {
	private int exitCode;

	private int signal;

	private String reason;

	private String message;

	private String startedAt;

	private String finishedAt;

	private String containerID;

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	public int getSignal() {
		return signal;
	}

	public void setSignal(int signal) {
		this.signal = signal;
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

	public String getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}

	public String getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(String finishedAt) {
		this.finishedAt = finishedAt;
	}

	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}

	public String getContainerID() {
		return containerID;
	}

	@Override
	public String toString() {
		return "ContainerStateTerminated "
				+ "[exitCode=" + exitCode 
				+ ", signal=" + signal 
				+ ", reason=" + reason 
				+ ", message=" + message
				+ ", startedAt=" + startedAt 
				+ ", finishedAt=" + finishedAt
				+ ", containerID=" + containerID + "]";
	}

}
