/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

public class MigrationRecordEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ID
	private String id;
	
	private long appId;

	private long recordType;

	private long recordStatus;
	
	private String originalApaasClusterName;
	
	private String originalIpaasClusterName;
	
	private String migrationApaasClusterName;
	
	private String migrationIpaasClusterName;
	
	private String originalDataCenterName;
	
	private String migrationDataCenterName;
	
	private Date migrationDate;
	
	public String getOriginalApaasClusterName() {
		return originalApaasClusterName;
	}

	public void setOriginalApaasClusterName(String originalApaasClusterName) {
		this.originalApaasClusterName = originalApaasClusterName;
	}

	public String getOriginalIpaasClusterName() {
		return originalIpaasClusterName;
	}

	public void setOriginalIpaasClusterName(String originalIpaasClusterName) {
		this.originalIpaasClusterName = originalIpaasClusterName;
	}

	public String getMigrationApaasClusterName() {
		return migrationApaasClusterName;
	}

	public void setMigrationApaasClusterName(String migrationApaasClusterName) {
		this.migrationApaasClusterName = migrationApaasClusterName;
	}

	public String getMigrationIpaasClusterName() {
		return migrationIpaasClusterName;
	}

	public void setMigrationIpaasClusterName(String migrationIpaasClusterName) {
		this.migrationIpaasClusterName = migrationIpaasClusterName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getOriginalDataCenterName() {
		return originalDataCenterName;
	}

	public void setOriginalDataCenterName(String originalDataCenterName) {
		this.originalDataCenterName = originalDataCenterName;
	}

	public String getMigrationDataCenterName() {
		return migrationDataCenterName;
	}

	public void setMigrationDataCenterName(String migrationDataCenterName) {
		this.migrationDataCenterName = migrationDataCenterName;
	}

	public Date getMigrationDate() {
		return migrationDate;
	}

	public void setMigrationDate(Date migrationDate) {
		this.migrationDate = migrationDate;
	}
	
	public long getRecordType() {
		return recordType;
	}

	public void setRecordType(long recordType) {
		this.recordType = recordType;
	}

	public long getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(long recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public String toString() {
		return "MigrationRecordEntity [id=" + id + ", appId=" + appId
				+ ", recordType=" + recordType + ", recordStatus="
				+ recordStatus + ", originalApaasClusterName="
				+ originalApaasClusterName + ", originalIpaasClusterName="
				+ originalIpaasClusterName + ", migrationApaasClusterName="
				+ migrationApaasClusterName + ", migrationIpaasClusterName="
				+ migrationIpaasClusterName + ", originalDataCenterName="
				+ originalDataCenterName + ", migrationDataCenterName="
				+ migrationDataCenterName + ", migrationDate=" + migrationDate
				+ "]";
	}

}
