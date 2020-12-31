/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.AppEntity;

/**
 * @author hehm
 * 2016-3-23
 */
public class AppList {

	private List<AppEntity> appList;

	public List<AppEntity> getAppList() {
		return appList;
	}

	public void setAppList(List<AppEntity> appList) {
		this.appList = appList;
	}

	@Override
	public String toString() {
		return "AppList [appList=" + appList + "]";
	}
}
