/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildRecordList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.BuildRecordEntity;

/**
 * @author hehm
 * 2016-3-23
 */
public class BuildRecordList {

	private List<BuildRecordEntity> buildRecordList;

	public void setBuildRecordList(List<BuildRecordEntity> buildRecordList) {
		this.buildRecordList = buildRecordList;
	}

	public List<BuildRecordEntity> getBuildRecordList() {
		return buildRecordList;
	}
}
