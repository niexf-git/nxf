package com.cmsz.paas.common.model.controller.entity;

import java.util.List;

public class PodEventInfo {

	private String nodeName;
	
	private String podIP;
	
	private String phase;
	
	private String startTime;
	
	private String runningTime;
	
	private String nodeSelector;
	
	private List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPodIP() {
		return podIP;
	}

	public void setPodIP(String podIP) {
		this.podIP = podIP;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	public String getNodeSelector() {
		return nodeSelector;
	}

	public void setNodeSelector(String nodeSelector) {
		this.nodeSelector = nodeSelector;
	}

	@Override
	public String toString() {
		return "PodEventInfo [nodeName=" + nodeName + ", podIP=" + podIP + ", phase=" + phase + ", startTime="
				+ startTime + ", runningTime=" + runningTime + ", nodeSelector=" + nodeSelector + ", events=" + events
				+ "]";
	}
}
