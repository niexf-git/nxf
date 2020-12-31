package com.cmsz.paas.web.monitoroperation.model;
/**
 * 告警model.
 * 
 * @author ccl
 */
public class SysAlarmInfo {
	/** 数据中心名称 */
	private String dataCenterName;

	/** 集群名称 */
	private String clusterName;

	/** 节点ip */
	private String nodeip;

	/** 告警类型*/
	private String type;
	
	/** 名称 */
	private String name;
	
	/** 告警时间 */
	private String time;
	
	/** 告警描述 */
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getNodeip() {
		return nodeip;
	}
	public void setNodeip(String nodeip) {
		this.nodeip = nodeip;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
