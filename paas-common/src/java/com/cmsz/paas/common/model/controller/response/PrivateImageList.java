/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppImageList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.PrivateImageEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class PrivateImageList {

	private List<PrivateImageEntity> privateImageList;

	public void setPrivateImageList(List<PrivateImageEntity> privateImageList) {
		this.privateImageList = privateImageList;
	}

	public List<PrivateImageEntity> getPrivateImageList() {
		return privateImageList;
	}
}
