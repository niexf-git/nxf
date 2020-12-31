package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class PodStatus {

	private String phase;

	private PodCondition[] conditions;

	private String message;

	private String reason;

	private String hostIP;

	private String podIP;

	private String startTime;

	private ContainerStatus[] containerStatuses;

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public PodCondition[] getConditions() {
		return conditions;
	}

	public void setConditions(PodCondition[] conditions) {
		this.conditions = conditions;
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

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public String getPodIP() {
		return podIP;
	}

	public void setPodIP(String podIP) {
		this.podIP = podIP;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public ContainerStatus[] getContainerStatuses() {
		return containerStatuses;
	}

	public void setContainerStatuses(ContainerStatus[] containerStatuses) {
		this.containerStatuses = containerStatuses;
	}

	@Override
	public String toString() {
		return "PodStatus "
				+ "[phase=" + phase
				+ ", conditions=" + Arrays.toString(conditions) 
				+ ", message=" + message
				+ ", reason=" + reason 
				+ ", hostIP=" + hostIP 
				+ ", podIP=" + podIP 
				+ ", startTime=" + startTime 
				+ ", containerStatuses=" + Arrays.toString(containerStatuses) + "]";
	}

}
