package com.cmsz.paas.web.alarm.entity;

import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 告警详情实体类.
 * 
 * @author li.lv 2015-4-14
 */
public class AlarmDetail {


	@ID
	private Long id;

	/** 集群名称 */
	private String clusterName;

	/** 集群label */
	private String clusterLabel;

	/** 实例名称 */
	private String podName;

	/** 容器ID */
	private String containerId;

	/** 告警时间 */
	private Date insertTime;

	/** 告警类型 */
	private String alarmType;

	/** 资源类型 */
	private String eventItem;

	/** 描述 */
	private String description;

	/** 级别 */
	private String alarmLevel;

	/** 应用名称 */
	private String appName;

	/** 命名空间 */
	private String namespace;

	/** IP信息 */
	private String hostip;
	/** 操作类型 */
	private String operType;
	/** 服务名称 */
	private String serviceName;

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClusterLabel() {
		return clusterLabel;
	}

	public void setClusterLabel(String clusterLabel) {
		this.clusterLabel = clusterLabel;
	}

	public String getPodName() {
		return podName;
	}

	public void setPodName(String podName) {
		this.podName = podName;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getEventItem() {
		return eventItem;
	}

	public void setEventItem(String eventItem) {
		this.eventItem = eventItem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}


	public String getHostip() {
		return hostip;
	}

	public void setHostip(String hostip) {
		this.hostip = hostip;
	}
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public String toString() {
		return "AlarmDetail [id=" + id + ", clusterName=" + clusterName
				+ ", clusterLabel=" + clusterLabel + ", podName=" + podName
				+ ", containerId=" + containerId + ", insertTime=" + insertTime
				+ ", alarmType=" + alarmType + ", eventItem=" + eventItem
				+ ", description=" + description + ", alarmLevel=" + alarmLevel
				+ ", appName=" + appName + ", namespace=" + namespace
				+ ", hostip=" + hostip + "]";
	}

}
