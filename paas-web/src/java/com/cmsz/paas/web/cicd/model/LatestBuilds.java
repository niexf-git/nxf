package com.cmsz.paas.web.cicd.model;

/***
 * 最新构建信息
 * @author jiangwei
 *
 */
public class LatestBuilds {

	/**
	 * 构建状态  \成功\失败
	 */
	private String status;
	
	/**
	 * 构建次数
	 */
	private String buildNumber;
	
	/**
	 * 构建名称
	 */
	private String buildName;
	
	/**
	 * 构建开始时间
	 */
	private String buildTime;
	
	/**
	 * 构建耗时
	 */
	private String buildDuration;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getBuildDuration() {
		return buildDuration;
	}

	public void setBuildDuration(String buildDuration) {
		this.buildDuration = buildDuration;
	}
	
	
	
}
