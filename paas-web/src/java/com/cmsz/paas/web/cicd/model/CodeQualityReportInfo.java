package com.cmsz.paas.web.cicd.model;
/**
 * 代码质量报表实体（外）
 * 
 * @author ls
 * @date 2017-11-27
 */
public class CodeQualityReportInfo {

	private String flowId;//流水ID
	private String flowName;//流水名称
	private int buildNum;//构建次数
	private int problemNum ;//问题总数
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
	public int getBuildNum() {
		return buildNum;
	}
	public void setBuildNum(int buildNum) {
		this.buildNum = buildNum;
	}
	public int getProblemNum() {
		return problemNum;
	}
	public void setProblemNum(int problemNum) {
		this.problemNum = problemNum;
	}
	
	
	
}
