/**
 * Copyright (c) 2015 cmsz, Inc. All Rights Reserved
 * File Minion.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.util.Date;

/**
 * Minion
 *
 * @author hehm
 *
 * @date 2015-4-8
 */
public class Node {
	private Date createTime;

	private String hostIP;

	private int cpuCapacity;

	private double memoryCapacity;

	private String kind;

	private String status;

	private Date lastProbeTime;

	private Date lastTransitionTime;

	private String reason;

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param hostIP the hostIP to set
	 */
	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	/**
	 * @return the hostIP
	 */
	public String getHostIP() {
		return hostIP;
	}

	/**
	 * @param cpuCapacity the cpuCapacity to set
	 */
	public void setCpuCapacity(int cpuCapacity) {
		this.cpuCapacity = cpuCapacity;
	}

	/**
	 * @return the cpuCapacity
	 */
	public int getCpuCapacity() {
		return cpuCapacity;
	}

	/**
	 * @param memoryCapacity the memoryCapacity to set
	 */
	public void setMemoryCapacity(double memoryCapacity) {
		this.memoryCapacity = memoryCapacity;
	}

	/**
	 * @return the memoryCapacity
	 */
	public double getMemoryCapacity() {
		return memoryCapacity;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param lastProbeTime the lastProbeTime to set
	 */
	public void setLastProbeTime(Date lastProbeTime) {
		this.lastProbeTime = lastProbeTime;
	}

	/**
	 * @return the lastProbeTime
	 */
	public Date getLastProbeTime() {
		return lastProbeTime;
	}

	/**
	 * @param lastTransitionTime the lastTransitionTime to set
	 */
	public void setLastTransitionTime(Date lastTransitionTime) {
		this.lastTransitionTime = lastTransitionTime;
	}

	/**
	 * @return the lastTransitionTime
	 */
	public Date getLastTransitionTime() {
		return lastTransitionTime;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

}
