/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildingIntermedMsg.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.BuildRecordEntity;

/**
 * @author hehm
 * 2016-3-23
 */
public class BuildRecordDetail {

	private BuildRecordEntity buildRecord;
	
	public void setBuildRecord(BuildRecordEntity buildRecord) {
		this.buildRecord = buildRecord;
	}

	public BuildRecordEntity getBuildRecord() {
		return buildRecord;
	}
}
