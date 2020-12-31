package com.cmsz.paas.common.model.controller.request;

import com.cmsz.paas.common.model.hygieia.Options;

public class CodeRepertory {

	private String collectorId;
	
	private Options options;
	
	private String createTime;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "CodeRepertory [collectorId=" + collectorId + ", options=" + options + ", createTime=" + createTime
				+ "]";
	}
}
