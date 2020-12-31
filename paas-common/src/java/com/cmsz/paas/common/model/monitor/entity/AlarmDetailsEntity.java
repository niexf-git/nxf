package com.cmsz.paas.common.model.monitor.entity;

import java.util.Date;

/**
 * @author jiayz
 * 2016-12-22
 */
public class AlarmDetailsEntity {
	private static final long serialVersionUID = 1L;
	
	private long dataCenterId;
	private long clusterId;
	private long nodeId;
	private String startTime;
	private String endTime;
	
	private String dataCenterName;
	private String clusterName;
	private String name;
	private String nodeIp;
	private Date time;
	private int type;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(long dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public long getClusterId() {
		return clusterId;
	}
	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDataCenterName() {
		return dataCenterName;
	}
	public void setDataCenterName(String dataCenterName) {
		this.dataCenterName = dataCenterName;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeIp() {
		return nodeIp;
	}
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "AlarmDetailsEntity [dataCenterId=" + dataCenterId
				+ ", clusterId=" + clusterId + ", nodeId=" + nodeId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", dataCenterName=" + dataCenterName + ", clusterName="
				+ clusterName + ", name=" + name + ", nodeIp=" + nodeIp
				+ ", time=" + time + ", type=" + type + "]";
	}
	
}
