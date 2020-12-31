/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildRecordEntity.java
 */
package com.cmsz.paas.common.model.entity;

import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class BuildRecordEntity {

	@ID
	private long id;

	private long buildId;

	private String buildNumber;

	private String revision;

	private String tag;

	private String version;

	private int status;

	private Date startTime;

	private Date endTime;

	private String createBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBuildId() {
		return buildId;
	}

	public void setBuildId(long buildId) {
		this.buildId = buildId;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	public String toString() {
		return "BuildRecordEntity [id=" + id + ", buildId=" + buildId
				+ ", buildNumber=" + buildNumber + ", revision=" + revision
				+ ", tag=" + tag + ", version=" + version + ", status="
				+ status + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", createBy=" + createBy + "]";
	}
}
