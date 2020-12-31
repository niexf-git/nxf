package com.cmsz.paas.web.cicd.model;

import java.util.List;

public class BuildsPerDayEntity {

	/**
	 * 成功次数
	 */
	private String successCount;
	
	/**
	 * 失败次数
	 */
	private String failuresCount;
	
	/**
	 * 构建日期
	 */
	private String buildDate;
	
	
	/**
	 * 一天内构建的所有信息
	 */
	private List<LatestBuilds> latestBuilds;


	public String getSuccessCount() {
		return successCount;
	}


	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}


	public String getFailuresCount() {
		return failuresCount;
	}


	public void setFailuresCount(String failuresCount) {
		this.failuresCount = failuresCount;
	}


	public String getBuildDate() {
		return buildDate;
	}


	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}


	public List<LatestBuilds> getLatestBuilds() {
		return latestBuilds;
	}


	public void setLatestBuilds(List<LatestBuilds> latestBuilds) {
		this.latestBuilds = latestBuilds;
	}
	
	
	
	
}
