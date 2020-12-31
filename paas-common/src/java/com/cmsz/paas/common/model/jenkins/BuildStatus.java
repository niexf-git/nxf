/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildDetail.java
 */
package com.cmsz.paas.common.model.jenkins;

/**
 * @author hehm
 * 2016-4-8
 */
public class BuildStatus {

	private String revision;

	private String tag;

	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
