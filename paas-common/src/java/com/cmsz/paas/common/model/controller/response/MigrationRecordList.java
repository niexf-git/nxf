/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.MigrationRecordEntity;

public class MigrationRecordList {
	
	private List<MigrationRecordEntity> migrationRecordList;

	public List<MigrationRecordEntity> getMigrationRecordList() {
		return migrationRecordList;
	}

	public void setMigrationRecordList(
			List<MigrationRecordEntity> migrationRecordList) {
		this.migrationRecordList = migrationRecordList;
	}

	@Override
	public String toString() {
		return "MigrationRecordList [migrationRecordList="
				+ migrationRecordList + "]";
	}
}
