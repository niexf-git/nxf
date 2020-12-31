/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppServiceDetail.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class AppServiceDetail {

	private AppServiceEntity appServiceDetail;

	public AppServiceEntity getAppServiceDetail() {
		return appServiceDetail;
	}

	public void setAppServiceDetail(AppServiceEntity appServiceDetail) {
		this.appServiceDetail = appServiceDetail;
	}

	@Override
	public String toString() {
		return "AppServiceDetail [appServiceDetail=" + appServiceDetail + "]";
	}
}
