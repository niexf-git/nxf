package com.cmsz.paas.web.cicd.model;

public class AvgBuilds {
	
	/**
	 * 平均构建时间
	 */
	private String avgBuildTime;

	/**
	 * 构建日期
	 */
	private String buildDate; 
	
	public String getAvgBuildTime() {
		return avgBuildTime;
	}

	public void setAvgBuildTime(String avgBuildTime) {
		this.avgBuildTime = avgBuildTime;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

}
