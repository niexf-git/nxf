package com.cmsz.paas.web.appserviceinst.model;

import java.util.List;


/**
 * 实例bean.
 * 
 * @author ccl
 * @date 2018-5-8
 */
public class InstanceEntity {

	/** 节点IP */
    private String nodeName;
	
    /** podip */
	private String podIP;
	
	/** 状态 */
	private String phase;
	
	/** 开始时间 */
	private String startTime;
	
	/** 运行时间 */
	private String runningTime;
	
	/** 所属集群 */
	private String nodeSelector;
	
	/** 事件集合 */
	private List<EventEntity> events;

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

	public List<EventEntity> getEvents() {
		return events;
	}

	public void setEvents(List<EventEntity> events) {
		this.events = events;
	}
	
	

	

}
