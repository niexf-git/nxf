package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

public class StepRecordEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ID
	private String uuid;
	private String flowRecordId;
	private String aliasName;
	private String stepName;
	private long buildNum;
	private Date beginTime;
	private double runTime;
	private int status;
	private String report;
	private String revision;
	private String jobStatus;
	private boolean isTime;
	private String coder;
	private String flowId;
	private long startTime;
	
	//自动化测试、集成测试
	private TestStatementEntity testStatements;
	//单元测试
	private UnitTestStatementEntity unitTestStatements;
	//性能测试
	private PerformanceTestStatementEntity performanceTestStatements;
	//代码质量
	private CodeCheckStatementEntity codeCheckStatements;
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
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
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public long getBuildNum() {
		return buildNum;
	}
	public void setBuildNum(long buildNum) {
		this.buildNum = buildNum;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
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
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public boolean isTime() {
		return isTime;
	}
	public void setTime(boolean isTime) {
		this.isTime = isTime;
	}
	public String getCoder() {
		return coder;
	}
	public void setCoder(String coder) {
		this.coder = coder;
	}
	public TestStatementEntity getTestStatements() {
		return testStatements;
	}
	public void setTestStatements(TestStatementEntity testStatements) {
		this.testStatements = testStatements;
	}
	public UnitTestStatementEntity getUnitTestStatements() {
		return unitTestStatements;
	}
	public void setUnitTestStatements(UnitTestStatementEntity unitTestStatements) {
		this.unitTestStatements = unitTestStatements;
	}
	public PerformanceTestStatementEntity getPerformanceTestStatements() {
		return performanceTestStatements;
	}
	public void setPerformanceTestStatements(PerformanceTestStatementEntity performanceTestStatements) {
		this.performanceTestStatements = performanceTestStatements;
	}
	public CodeCheckStatementEntity getCodeCheckStatements() {
		return codeCheckStatements;
	}
	public void setCodeCheckStatements(CodeCheckStatementEntity codeCheckStatements) {
		this.codeCheckStatements = codeCheckStatements;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "StepRecordEntity [uuid=" + uuid + ", flowRecordId=" + flowRecordId + ", aliasName=" + aliasName
				+ ", stepName=" + stepName + ", buildNum=" + buildNum + ", beginTime=" + beginTime + ", runTime="
				+ runTime + ", status=" + status + ", report=" + report + ", revision=" + revision + ", jobStatus="
				+ jobStatus + ", isTime=" + isTime + ", coder=" + coder + ", testStatements=" + testStatements
				+ ", unitTestStatements=" + unitTestStatements + ", performanceTestStatements="
				+ performanceTestStatements + ", codeCheckStatements=" + codeCheckStatements + "]";
	}
	
	
}
