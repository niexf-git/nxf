package com.cmsz.paas.web.cicd.model;

import java.util.Date;


/***
 * 单步骤执行记录实体类
 * @author jiangwei
 *
 */
public class StepRecodeEntity {

	/*** 步骤id */
	private String stepId;
	
	private String stepRecodeId;
	
	/** 步骤名称  */
	private String stepName;
	
	/** 开始时间   */
	private String startTime;
	
	/** 执行时间  */
	private String executeTime;
	
	/** 构建状态  */
	private String status;
	
	/** 步骤别名  job名 */
	private String asName;

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public String getStepRecodeId() {
		return stepRecodeId;
	}

	public void setStepRecodeId(String stepRecodeId) {
		this.stepRecodeId = stepRecodeId;
	}

	public String getAsName() {
		return asName;
	}

	public void setAsName(String asName) {
		this.asName = asName;
	}
	
	
	
}
