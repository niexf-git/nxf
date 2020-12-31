package com.cmsz.paas.web.cicd.model;
/**
 * 性能测试报表实体（内）
 * 
 * @author ls
 * @date 2017-11-23
 */
public class PerformanceTestReportInfo {
	//
	private String executeRecord;  //执行记录
	private int totalNums; //总次数
	private int failNums; //失败次数
	private double shortestResponseTime; //最短响应时间
	private double averageResponseTime;//平均响应时间
	private double longestResponseTime; //最长响应时间
	public String getExecuteRecord() {
		return executeRecord;
	}
	public void setExecuteRecord(String executeRecord) {
		this.executeRecord = executeRecord;
	}
	public int getTotalNums() {
		return totalNums;
	}
	public void setTotalNums(int totalNums) {
		this.totalNums = totalNums;
	}
	public int getFailNums() {
		return failNums;
	}
	public void setFailNums(int failNums) {
		this.failNums = failNums;
	}
	public double getShortestResponseTime() {
		return shortestResponseTime;
	}
	public void setShortestResponseTime(double shortestResponseTime) {
		this.shortestResponseTime = shortestResponseTime;
	}
	public double getAverageResponseTime() {
		return averageResponseTime;
	}
	public void setAverageResponseTime(double averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}
	public double getLongestResponseTime() {
		return longestResponseTime;
	}
	public void setLongestResponseTime(double longestResponseTime) {
		this.longestResponseTime = longestResponseTime;
	}

	
}
