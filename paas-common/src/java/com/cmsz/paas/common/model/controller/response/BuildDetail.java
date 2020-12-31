/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildDetail.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.BuildEntity;

/**
 * @author lenovo
 * 2016-3-23
 */
public class BuildDetail {

	private BuildEntity buildDetail;

	public void setBuildDetail(BuildEntity buildDetail) {
		this.buildDetail = buildDetail;
	}

	public BuildEntity getBuildDetail() {
		return buildDetail;
	}
}
