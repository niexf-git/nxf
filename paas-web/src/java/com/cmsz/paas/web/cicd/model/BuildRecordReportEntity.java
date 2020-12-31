package com.cmsz.paas.web.cicd.model;

public class BuildRecordReportEntity {

	private static final long serialVersionUID = 1L;

	private String downloadTime;// 下载审查时间
	private String downloadMessage;// 下载审查结果
	private String buildTime;// 编译构建时间
	private String buildMessage;// 编译构建结果
	private String deployTime;// 部署扫描时间
	private String deployMessage;// 部署扫描结果
	private String autoTime;// 自动测试时间
	private String autoMessage;// 自动测试结果
	private String integrateTime;// 集成测试时间
	private String integrateMessage;// 集成测试结果
	private String releaseTime;// 发布测试时间
	private String releaseMessage;// 发布测试结果
	private String executionRecord;// 执行记录
	private String performanceTime;//性能测试时间
	public String getPerformanceTime() {
		return performanceTime;
	}

	public void setPerformanceTime(String performanceTime) {
		this.performanceTime = performanceTime;
	}

	public String getPerformanceMessage() {
		return performanceMessage;
	}

	public void setPerformanceMessage(String performanceMessage) {
		this.performanceMessage = performanceMessage;
	}

	private String performanceMessage;//性能测试结果
	public String getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}

	public String getDownloadMessage() {
		return downloadMessage;
	}

	public void setDownloadMessage(String downloadMessage) {
		this.downloadMessage = downloadMessage;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getBuildMessage() {
		return buildMessage;
	}

	public void setBuildMessage(String buildMessage) {
		this.buildMessage = buildMessage;
	}

	public String getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(String deployTime) {
		this.deployTime = deployTime;
	}

	public String getDeployMessage() {
		return deployMessage;
	}

	public void setDeployMessage(String deployMessage) {
		this.deployMessage = deployMessage;
	}

	public String getAutoTime() {
		return autoTime;
	}

	public void setAutoTime(String autoTime) {
		this.autoTime = autoTime;
	}

	public String getAutoMessage() {
		return autoMessage;
	}

	public void setAutoMessage(String autoMessage) {
		this.autoMessage = autoMessage;
	}

	public String getIntegrateTime() {
		return integrateTime;
	}

	public void setIntegrateTime(String integrateTime) {
		this.integrateTime = integrateTime;
	}

	public String getIntegrateMessage() {
		return integrateMessage;
	}

	public void setIntegrateMessage(String integrateMessage) {
		this.integrateMessage = integrateMessage;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseMessage() {
		return releaseMessage;
	}

	public void setReleaseMessage(String releaseMessage) {
		this.releaseMessage = releaseMessage;
	}

	public String getExecutionRecord() {
		return executionRecord;
	}

	public void setExecutionRecord(String executionRecord) {
		this.executionRecord = executionRecord;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BuildRecordReportEntity [downloadTime=" + downloadTime + ", downloadMessage=" + downloadMessage
				+ ", buildTime=" + buildTime + ", buildMessage=" + buildMessage + ", deployTime=" + deployTime
				+ ", deployMessage=" + deployMessage + ", autoTime=" + autoTime + ", autoMessage=" + autoMessage
				+ ", integrateTime=" + integrateTime + ", integrateMessage=" + integrateMessage + ", releaseTime="
				+ releaseTime + ", releaseMessage=" + releaseMessage + ", executionRecord=" + executionRecord + "]";
	}

}

