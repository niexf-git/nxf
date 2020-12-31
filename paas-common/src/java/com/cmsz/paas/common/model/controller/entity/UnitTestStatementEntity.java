package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;
/**
 * 单元测试报表
 * @author 
 *
 */
public class UnitTestStatementEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ID
	private String uuid;//
	
	private String time ; //执行记录
	
	private String stepRecordId;//
	private Integer totals;//用例总数

	private Integer successNumber;//成功数

	private Integer failNumber;//失败数

	private Integer errorCount;//错误数

	private Double failReat;//失败率

	private String successReat;//成功率

	private String commandCoverage;//指令覆盖率
	
	private String branchConverage;//分支覆盖率

	private String complexity;//圈复杂度

	private String lineCoverage;//行覆盖率
	
	private String methodCoverage;//方法覆盖率

	private String classCoverage;//类覆盖率
	
	private String flowName;//流水名称
	private Integer buildNum;//执行次数
	private String flowId;//流水id
	
	private String coder; //代码负责人
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStepRecordId() {
		return stepRecordId;
	}

	public void setStepRecordId(String stepRecordId) {
		this.stepRecordId = stepRecordId;
	}

	public Integer getTotals() {
		return totals;
	}

	public void setTotals(Integer totals) {
		this.totals = totals;
	}

	public Integer getSuccessNumber() {
		return successNumber;
	}

	public void setSuccessNumber(Integer successNumber) {
		this.successNumber = successNumber;
	}

	public Integer getFailNumber() {
		return failNumber;
	}

	public void setFailNumber(Integer failNumber) {
		this.failNumber = failNumber;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public Double getFailReat() {
		return failReat;
	}

	public void setFailReat(Double failReat) {
		this.failReat = failReat;
	}

	public String getSuccessReat() {
		return successReat;
	}

	public void setSuccessReat(String successReat) {
		this.successReat = successReat;
	}

	public String getCommandCoverage() {
		return commandCoverage;
	}

	public void setCommandCoverage(String commandCoverage) {
		this.commandCoverage = commandCoverage;
	}

	public String getBranchConverage() {
		return branchConverage;
	}

	public void setBranchConverage(String branchConverage) {
		this.branchConverage = branchConverage;
	}

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	public String getLineCoverage() {
		return lineCoverage;
	}

	public void setLineCoverage(String lineCoverage) {
		this.lineCoverage = lineCoverage;
	}

	public String getMethodCoverage() {
		return methodCoverage;
	}

	public void setMethodCoverage(String methodCoverage) {
		this.methodCoverage = methodCoverage;
	}

	public String getClassCoverage() {
		return classCoverage;
	}

	public void setClassCoverage(String classCoverage) {
		this.classCoverage = classCoverage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Integer getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(Integer buildNum) {
		this.buildNum = buildNum;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getCoder() {
		return coder;
	}

	public void setCoder(String coder) {
		this.coder = coder;
	}

	@Override
	public String toString() {
		return "UnitTestStatementEntity [uuid=" + uuid + ", time=" + time
				+ ", stepRecordId=" + stepRecordId + ", totals=" + totals
				+ ", successNumber=" + successNumber + ", failNumber="
				+ failNumber + ", errorCount=" + errorCount + ", failReat="
				+ failReat + ", successReat=" + successReat
				+ ", commandCoverage=" + commandCoverage + ", branchConverage="
				+ branchConverage + ", complexity=" + complexity
				+ ", lineCoverage=" + lineCoverage + ", methodCoverage="
				+ methodCoverage + ", classCoverage=" + classCoverage
				+ ", flowName=" + flowName + ", buildNum=" + buildNum
				+ ", flowId=" + flowId + ", coder=" + coder + "]";
	}

}
