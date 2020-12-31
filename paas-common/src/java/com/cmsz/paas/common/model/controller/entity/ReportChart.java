package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class ReportChart implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String flowName;//流水名称
	private Integer buildNum;//执行次数
	private Integer error;//每次问题数
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
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
}
