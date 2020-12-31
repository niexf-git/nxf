/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File PublicImageList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.PublicImageEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class PublicImageList {

	private List<PublicImageEntity> publicImageList;

	public void setPublicImageList(List<PublicImageEntity> publicImageList) {
		this.publicImageList = publicImageList;
	}

	public List<PublicImageEntity> getPublicImageList() {
		return publicImageList;
	}
}
