package com.cmsz.paas.web.monitoroperation.model;
/**
 * 告警model.
 * 
 * @author ccl
 */
public class SysAlarm {
	/** 数据中心ID */
	private long dataCenterId;

	/** 集群ID */
	private long clusterId;

	/** 节点ID */
	private long nodeId;

	/** 开始时间*/
	private String startTime;
	
	/** 结束时间 */
	private String endTime;






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
}
