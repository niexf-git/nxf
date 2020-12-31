package com.cmsz.paas.web.cicd.model;
/**
 * 自动化测试报表实体
 * 
 * @author ccl
 * @date 2017-9-1
 */
public class AutoTestReportInfo {
	/** 执行记录 */
	private String executionRecord;
	/** 用例总数 */
	private String totals;
	/** 成功数 */
	private int successNumber;
	/** 成功率 */
	private String successRate;
	/** 失败数 */
	private int failNumber;
	/** 失败率 */
	private String failRate;
	/** 负责人 */
	private String person;
	
	
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getExecutionRecord() {
		return executionRecord;
	}
	public void setExecutionRecord(String executionRecord) {
		this.executionRecord = executionRecord;
	}
	public String getTotals() {
		return totals;
	}
	public void setTotals(String totals) {
		this.totals = totals;
	}
	public int getSuccessNumber() {
		return successNumber;
	}
	public void setSuccessNumber(int successNumber) {
		this.successNumber = successNumber;
	}
	public int getFailNumber() {
		return failNumber;
	}
	public void setFailNumber(int failNumber) {
		this.failNumber = failNumber;
	}
	public String getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}
	public String getFailRate() {
		return failRate;
	}
	public void setFailRate(String failRate) {
		this.failRate = failRate;
	}
	

	
	
}
