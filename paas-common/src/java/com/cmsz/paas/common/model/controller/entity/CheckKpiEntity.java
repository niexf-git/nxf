package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class CheckKpiEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int problemNum; // 问题数
	private int leakNum; // 漏洞数
	private String codeRepRate;// 代码重复率
	private int lineNum;// 代码行数
	private String complexity; // 复杂度
	private String flowId; //流水id
	private String flowName; //流水名 
	
	public int getProblemNum() {
		return problemNum;
	}
	public void setProblemNum(int problemNum) {
		this.problemNum = problemNum;
	}
	public int getLeakNum() {
		return leakNum;
	}
	public void setLeakNum(int leakNum) {
		this.leakNum = leakNum;
	}
	public String getCodeRepRate() {
		return codeRepRate;
	}
	public void setCodeRepRate(String codeRepRate) {
		this.codeRepRate = codeRepRate;
	}
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getComplexity() {
		return complexity;
	}
	public void setComplexity(String complexity) {
		this.complexity = complexity;
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
		return "CheckKpiEntity [problemNum=" + problemNum + ", leakNum="
				+ leakNum + ", codeRepRate=" + codeRepRate + ", lineNum="
				+ lineNum + ", complexity=" + complexity + ", flowId=" + flowId
				+ ", flowName=" + flowName + "]";
	}


}
