package com.cmsz.paas.web.cicd.model;

/**
 * 流水执行记录
 * 
 * @author lin.my
 * @version 创建时间：2017年9月4日 下午2:18:51
 */
public class FlowStepRecordEntity {

	private String uuid;
	private String flowRecordId;
	private String aliasName;
	private String stepName;
	private long buildNum;
	private String beginTime;
	private double runTime;
	private int status;
	private String report;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(String flowRecordId) {
		this.flowRecordId = flowRecordId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public long getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(long buildNum) {
		this.buildNum = buildNum;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public double getRunTime() {
		return runTime;
	}

	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

}
