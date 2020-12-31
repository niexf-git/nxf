/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppDetail.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.AppEntity;

/**
 * @author hehm
 * 2016-3-23
 */
public class AppDetail {

	private AppEntity appDetail;

	public void setAppDetail(AppEntity appDetail) {
		this.appDetail = appDetail;
	}

	public AppEntity getAppDetail() {
		return appDetail;
	}

	@Override
	public String toString() {
		return "AppDetail [appDetail=" + appDetail + "]";
	}
}
