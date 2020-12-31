package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class BulidReportEntity implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String time;//执行记录
	private String downloadTime;//下载审查时间
	private int downloadResult;//下载审查结果
    private String stringbuildTime;//编译构建时间
    private int  buildResult;//编译构建结果
    private String scanTime;//部署扫描时间
    private int scanResult;//部署扫描结果
    private String automaticTime;//自动测试时间
    private int  automaticResult;//自动测试结果
    private String integrateTime;//集成测试时间
    private int integrateResult;//集成测试结果
    private String releaseTime;//发布测试时间
    private int releaseResult;//发布测试结果
    private String populationTime;//总体时间
    private String performanceTime;//性能测试时间
    private float performanceMessage;//性能测试结果
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	public int getDownloadResult() {
		return downloadResult;
	}
	public void setDownloadResult(int downloadResult) {
		this.downloadResult = downloadResult;
	}
	
	public String getStringbuildTime() {
		return stringbuildTime;
	}
	public void setStringbuildTime(String stringbuildTime) {
		this.stringbuildTime = stringbuildTime;
	}
	public int getBuildResult() {
		return buildResult;
	}
	public void setBuildResult(int buildResult) {
		this.buildResult = buildResult;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	public int getScanResult() {
		return scanResult;
	}
	public void setScanResult(int scanResult) {
		this.scanResult = scanResult;
	}
	public String getAutomaticTime() {
		return automaticTime;
	}
	public void setAutomaticTime(String automaticTime) {
		this.automaticTime = automaticTime;
	}
	public int getAutomaticResult() {
		return automaticResult;
	}
	public void setAutomaticResult(int automaticResult) {
		this.automaticResult = automaticResult;
	}
	public String getIntegrateTime() {
		return integrateTime;
	}
	public void setIntegrateTime(String integrateTime) {
		this.integrateTime = integrateTime;
	}
	public int getIntegrateResult() {
		return integrateResult;
	}
	public void setIntegrateResult(int integrateResult) {
		this.integrateResult = integrateResult;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public int getReleaseResult() {
		return releaseResult;
	}
	public void setReleaseResult(int releaseResult) {
		this.releaseResult = releaseResult;
	}
	public String getPopulationTime() {
		return populationTime;
	}
	public void setPopulationTime(String populationTime) {
		this.populationTime = populationTime;
	}
	
	public String getPerformanceTime() {
		return performanceTime;
	}
	public void setPerformanceTime(String performanceTime) {
		this.performanceTime = performanceTime;
	}
	public float getPerformanceMessage() {
		return performanceMessage;
	}
	public void setPerformanceMessage(float performanceMessage) {
		this.performanceMessage = performanceMessage;
	}
	@Override
	public String toString() {
		return "BulidReportEntity [time=" + time + ", downloadTime="
				+ downloadTime + ", downloadResult=" + downloadResult
				+ ", StringbuildTime=" + stringbuildTime + ", buildResult="
				+ buildResult + ", scanTime=" + scanTime + ", scanResult="
				+ scanResult + ", automaticTime=" + automaticTime
				+ ", automaticResult=" + automaticResult + ", integrateTime="
				+ integrateTime + ", integrateResult=" + integrateResult
				+ ", releaseTime=" + releaseTime + ", releaseResult="
				+ releaseResult + ", populationTime=" + populationTime
				+ ", performanceTime=" + performanceTime + ", performanceMessage=" + performanceMessage + "]";
	}
    
}
