/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppImageDetail.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.PrivateImageEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class PrivateImageDetail {

	private PrivateImageEntity privateImageDetail;

	public void setPrivateImageDetail(PrivateImageEntity privateImageDetail) {
		this.privateImageDetail = privateImageDetail;
	}

	public PrivateImageEntity getPrivateImageDetail() {
		return privateImageDetail;
	}
}
