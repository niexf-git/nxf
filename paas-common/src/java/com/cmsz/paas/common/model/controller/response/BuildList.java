/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.BuildEntity;

/**
 * @author hehm
 * 2016-3-23
 */
public class BuildList {

	private List<BuildEntity> buildList;

	public void setBuildList(List<BuildEntity> buildList) {
		this.buildList = buildList;
	}

	public List<BuildEntity> getBuildList() {
		return buildList;
	}
}
