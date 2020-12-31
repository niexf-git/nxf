/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppImageVersion.java
 */
package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;


public class NodeEntity implements Serializable,Comparable<NodeEntity>{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long clusterId;

	private long deployId;

	private String hostIp;
	
	private String floatIp;
	
	private String isMain;    //master/slave

	private String passwd;

	private String status;
	
	private Date insertTime;
	
	private ClusterEntity cluster;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getClusterId() {
		return clusterId;
	}

	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}

	public long getDeployId() {
		return deployId;
	}

	public void setDeployId(long deployId) {
		this.deployId = deployId;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getFloatIp() {
		return floatIp;
	}

	public void setFloatIp(String floatIp) {
		this.floatIp = floatIp;
	}

	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public ClusterEntity getCluster() {
		return cluster;
	}

	public void setCluster(ClusterEntity cluster) {
		this.cluster = cluster;
	}

	@Override
	public String toString() {
		return "NodeEntity [id=" + id + ", clusterId=" + clusterId + ", deployId=" + deployId + ", hostIp=" + hostIp
				+ ", floatIp=" + floatIp + ", isMain=" + isMain + ", passwd=" + passwd + ", status=" + status
				+ ", insertTime=" + insertTime + ", cluster=" + cluster + "]";
	}

	@Override
	public int compareTo(NodeEntity paramT) {
		return this.getHostIp().compareTo(paramT.getHostIp());
	}

	

	
	

}
