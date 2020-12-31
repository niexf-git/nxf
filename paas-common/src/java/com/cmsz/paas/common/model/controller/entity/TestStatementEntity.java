package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 自动化测试、集成测试报表
 * @author 
 *
 */
public class TestStatementEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ID
	private String uuid;
	
	private String stepRecordId;
	private Integer errorCount;//问题总数
	private Integer failNumber;//失败数
	private Double failReat;//失败率
	private Integer successNumber;//成功数
	private Double successReat;//成功率
	private Integer totals;//用例总数
	private String coder;//代码负责人
	private String flowName;//流水名称
	private Integer buildNum;//执行次数
	private String flowId;//流水id
	private String report;
	private String time;//执行记录 
	private List<RobotTestResultEntity> robotTestResults;// 测试结果集合
	
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<RobotTestResultEntity> getRobotTestResults() {
		return robotTestResults;
	}
	public void setRobotTestResults(List<RobotTestResultEntity> robotTestResults) {
		this.robotTestResults = robotTestResults;
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
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public Integer getFailNumber() {
		return failNumber;
	}
	public void setFailNumber(Integer failNumber) {
		this.failNumber = failNumber;
	}
	public Double getFailReat() {
		return failReat;
	}
	public void setFailReat(Double failReat) {
		this.failReat = failReat;
	}
	public Integer getSuccessNumber() {
		return successNumber;
	}
	public void setSuccessNumber(Integer successNumber) {
		this.successNumber = successNumber;
	}
	public Double getSuccessReat() {
		return successReat;
	}
	public void setSuccessReat(Double successReat) {
		this.successReat = successReat;
	}
	public Integer getTotals() {
		return totals;
	}
	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	public String getCoder() {
		return coder;
	}
	public void setCoder(String coder) {
		this.coder = coder;
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
}
