/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppIdList.java
 */
package com.cmsz.paas.common.model.controller.request;

import java.util.List;


/**
 * @author hehm
 * 2016-4-15
 */
public class AppIdList {

	private long clusterId;
	
	private List<AppId> appIdList;

	public long getClusterId() {
		return clusterId;
	}

	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}

	public void setAppIdList(List<AppId> appIdList) {
		this.appIdList = appIdList;
	}

	public List<AppId> getAppIdList() {
		return appIdList;
	}
}
