package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;
/**
 * 性能测试报表
 * @author 
 *
 */
public class PerformanceTestStatementEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ID
	private String uuid;//
	private String stepRecordId;//
	
	private String time;//执行记录
	
	private Integer totals;//用例总数

	private Integer successNumber;//成功数

	private Integer failNumber;//失败数

	private Double shortestResponse;//最短响应时长

	private Double averageResponse;//平均响应时长

	private Double longestResponse;//最长响应时长
	
	private int isChoise;

	private int status;

	private double runTime;
	
	
	private String flowName;//流水名称
	private Integer buildNum;//执行次数
	private String flowId;//流水id
	
	private String coder; //代码负责人

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public Double getShortestResponse() {
		return shortestResponse;
	}

	public void setShortestResponse(Double shortestResponse) {
		this.shortestResponse = shortestResponse;
	}

	public Double getAverageResponse() {
		return averageResponse;
	}

	public void setAverageResponse(Double averageResponse) {
		this.averageResponse = averageResponse;
	}

	public Double getLongestResponse() {
		return longestResponse;
	}

	public void setLongestResponse(Double longestResponse) {
		this.longestResponse = longestResponse;
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

	public int getIsChoise() {
		return isChoise;
	}

	public void setIsChoise(int isChoise) {
		this.isChoise = isChoise;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getRunTime() {
		return runTime;
	}

	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

	public String getCoder() {
		return coder;
	}

	public void setCoder(String coder) {
		this.coder = coder;
	}

	@Override
	public String toString() {
		return "PerformanceTestStatementEntity [uuid=" + uuid
				+ ", stepRecordId=" + stepRecordId + ", time=" + time
				+ ", totals=" + totals + ", successNumber=" + successNumber
				+ ", failNumber=" + failNumber + ", shortestResponse="
				+ shortestResponse + ", averageResponse=" + averageResponse
				+ ", longestResponse=" + longestResponse + ", isChoise="
				+ isChoise + ", status=" + status + ", runTime=" + runTime
				+ ", flowName=" + flowName + ", buildNum=" + buildNum
				+ ", flowId=" + flowId + ", coder=" + coder + "]";
	}
	
}
