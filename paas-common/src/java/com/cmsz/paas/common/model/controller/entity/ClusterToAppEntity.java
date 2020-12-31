/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File ClusterToAppEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class ClusterToAppEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long clusterId;

	private long appId;

	private int type;
	
	private int isDisasterTolerance;

	/**
	 * 对应的集群
	 */
	private ClusterEntity cluster;

	@Override
	public String toString() {
		return "ClusterToAppEntity [id=" + id + ", clusterId=" + clusterId
				+ ", appId=" + appId + ", type=" + type
				+ ", isDisasterTolerance=" + isDisasterTolerance + ", cluster="
				+ cluster + "]";
	}

	public int getIsDisasterTolerance() {
		return isDisasterTolerance;
	}

	public void setIsDisasterTolerance(int isDisasterTolerance) {
		this.isDisasterTolerance = isDisasterTolerance;
	}

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

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ClusterEntity getCluster() {
		return cluster;
	}

	public void setCluster(ClusterEntity cluster) {
		this.cluster = cluster;
	}
}
