/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File ClusterList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.ClusterEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class ClusterList {

	private List<ClusterEntity> clusterList;

	public void setClusterList(List<ClusterEntity> clusterList) {
		this.clusterList = clusterList;
	}

	public List<ClusterEntity> getClusterList() {
		return clusterList;
	}
}
