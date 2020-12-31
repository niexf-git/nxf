package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class BuildKpiEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int compSuccNum; // 编译成功次数
	private int compTotalNum; // 编译总次数
	private int buildSuccNum; // 构建成功次数
	private int buildTotalNum; // 构建总次数
	private String flowId; //流水id
	private String flowName; //流水名 
	
	public int getCompSuccNum() {
		return compSuccNum;
	}
	public void setCompSuccNum(int compSuccNum) {
		this.compSuccNum = compSuccNum;
	}
	
	public int getBuildSuccNum() {
		return buildSuccNum;
	}
	public void setBuildSuccNum(int buildSuccNum) {
		this.buildSuccNum = buildSuccNum;
	}
	
	public int getCompTotalNum() {
		return compTotalNum;
	}
	public void setCompTotalNum(int compTotalNum) {
		this.compTotalNum = compTotalNum;
	}
	public int getBuildTotalNum() {
		return buildTotalNum;
	}
	public void setBuildTotalNum(int buildTotalNum) {
		this.buildTotalNum = buildTotalNum;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	@Override
	public String toString() {
		return "BuildKpiEntity [compSuccNum=" + compSuccNum + ", compTotalNum="
				+ compTotalNum + ", buildSuccNum=" + buildSuccNum
				+ ", buildTotalNum=" + buildTotalNum + ", flowId=" + flowId
				+ ", flowName=" + flowName + "]";
	}
	
}
