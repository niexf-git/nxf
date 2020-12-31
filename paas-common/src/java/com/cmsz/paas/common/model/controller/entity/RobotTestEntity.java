package com.cmsz.paas.common.model.controller.entity;

import java.util.List;

/**
 * 存放测试报告
 * 
 * @author Administrator
 *
 */
public class RobotTestEntity {
	
	private String stepRecordId;
	
	private int errorCount;// 错误记录数
	
	private int totals;// 测试案例总数
	
	private int failNumber;// 缺陷数
	
	private String time;//执行记录
	
	private int successNumber;//成功数
	
    private String successReat;//成功率
    
    private String failReat;//失败率
    
    private String report;//报告结果
	
	private List<RobotTestResultEntity> robotTestResults;// 测试结果集合

	public String getStepRecordId() {
		return stepRecordId;
	}

	public void setStepRecordId(String stepRecordId) {
		this.stepRecordId = stepRecordId;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public List<RobotTestResultEntity> getRobotTestResults() {
		return robotTestResults;
	}

	public void setRobotTestResults(List<RobotTestResultEntity> robotTestResults) {
		this.robotTestResults = robotTestResults;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public int getFailNumber() {
		return failNumber;
	}

	public void setFailNumber(int failNumber) {
		this.failNumber = failNumber;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSuccessNumber() {
		return successNumber;
	}

	public void setSuccessNumber(int successNumber) {
		this.successNumber = successNumber;
	}

	public String getSuccessReat() {
		return successReat;
	}

	public void setSuccessReat(String successReat) {
		this.successReat = successReat;
	}

	public String getFailReat() {
		return failReat;
	}

	public void setFailReat(String failReat) {
		this.failReat = failReat;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	@Override
	public String toString() {
		return "RobotTestEntity [errorCount=" + errorCount + ", totals="
				+ totals + ", failNumber=" + failNumber + ", time=" + time
				+ ", successNumber=" + successNumber + ", successReat="
				+ successReat + ", failReat=" + failReat + ", report=" + report
				+ ", robotTestResults=" + robotTestResults + "]";
	}
	
}
