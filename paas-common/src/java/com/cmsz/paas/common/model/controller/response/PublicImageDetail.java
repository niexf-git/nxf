/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File PublicImageDetail.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.PublicImageEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class PublicImageDetail {

	private PublicImageEntity publicImageDetail;

	public void setPublicImageDetail(PublicImageEntity publicImageDetail) {
		this.publicImageDetail = publicImageDetail;
	}

	public PublicImageEntity getPublicImageDetail() {
		return publicImageDetail;
	}
}
