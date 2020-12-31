package com.cmsz.paas.common.model.monitor.entity;


/**
 * @author jiayz
 * 2016-12-21
 */
public class GlobalMonitorEntity {
	private static final long serialVersionUID = 1L;
	
	private String dataCenterId;
	private String clusterId;
	private String nodeId;
	private String clusterName;
	private String nodeIp;
	private String totalMemory;
	private String usageMemory;
	private String cpu;
	private String totalfs;
	private String usagefs;
	private String nodeStatus;
	private String component_status;
	
	
	private String dataCenterName;
	public String getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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
	public String getNodeIp() {
		return nodeIp;
	}
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}
	public String getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}
	public String getUsageMemory() {
		return usageMemory;
	}
	public void setUsageMemory(String usageMemory) {
		this.usageMemory = usageMemory;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getTotalfs() {
		return totalfs;
	}
	public void setTotalfs(String totalfs) {
		this.totalfs = totalfs;
	}
	public String getUsagefs() {
		return usagefs;
	}
	public void setUsagefs(String usagefs) {
		this.usagefs = usagefs;
	}
	public String getNodeStatus() {
		return nodeStatus;
	}
	public String getComponent_status() {
		return component_status;
	}
	public void setComponent_status(String component_status) {
		this.component_status = component_status;
	}
	@Override
	public String toString() {
		return "GlobalMonitorEntity [dataCenterId=" + dataCenterId
				+ ", clusterId=" + clusterId + ", nodeId=" + nodeId
				+ ", clusterName=" + clusterName + ", nodeIp=" + nodeIp
				+ ", totalMemory=" + totalMemory + ", usageMemory="
				+ usageMemory + ", cpu=" + cpu + ", totalfs=" + totalfs
				+ ", usagefs=" + usagefs + ", nodeStatus=" + nodeStatus
				+ ", component_status=" + component_status
				+ ", dataCenterName=" + dataCenterName + "]";
	}
	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	
	
}
