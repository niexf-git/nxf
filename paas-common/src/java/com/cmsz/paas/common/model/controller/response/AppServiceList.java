/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppServiceList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class AppServiceList {

	private List<AppServiceEntity> appServiceList;

	public List<AppServiceEntity> getAppServiceList() {
		return appServiceList;
	}

	public void setAppServiceList(List<AppServiceEntity> appServiceList) {
		this.appServiceList = appServiceList;
	}

	@Override
	public String toString() {
		return "AppServiceList [appServiceList=" + appServiceList + "]";
	}
}
