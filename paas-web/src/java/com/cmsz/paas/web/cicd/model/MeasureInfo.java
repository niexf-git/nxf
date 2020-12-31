package com.cmsz.paas.web.cicd.model;

import java.io.Serializable;
/**
 * 度量实体
 * @author jiayz
 * @time 2017-8-29
 */
public class MeasureInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String flowId;
	private String flowName;
	private String problemNumber;//问题数  
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
	public String getProblemNumber() {
		return problemNumber;
	}
	public void setProblemNumber(String problemNumber) {
		this.problemNumber = problemNumber;
	}
	public String getFlawNumber() {
		return flawNumber;
	}
	public void setFlawNumber(String flawNumber) {
		this.flawNumber = flawNumber;
	}
	public String getCodeRepetitionRate() {
		return codeRepetitionRate;
	}
	public void setCodeRepetitionRate(String codeRepetitionRate) {
		this.codeRepetitionRate = codeRepetitionRate;
	}
	public String getCodeRows() {
		return codeRows;
	}
	public void setCodeRows(String codeRows) {
		this.codeRows = codeRows;
	}
	public String getCodeComplexity() {
		return codeComplexity;
	}
	public void setCodeComplexity(String codeComplexity) {
		this.codeComplexity = codeComplexity;
	}
	public String getFlowCompileNumber() {
		return flowCompileNumber;
	}
	public void setFlowCompileNumber(String flowCompileNumber) {
		this.flowCompileNumber = flowCompileNumber;
	}
	public String getFlowCompileSuccessRate() {
		return flowCompileSuccessRate;
	}
	public void setFlowCompileSuccessRate(String flowCompileSuccessRate) {
		this.flowCompileSuccessRate = flowCompileSuccessRate;
	}
	public String getFlowBuildNumber() {
		return flowBuildNumber;
	}
	public void setFlowBuildNumber(String flowBuildNumber) {
		this.flowBuildNumber = flowBuildNumber;
	}
	public String getFlowBuildSuccessRate() {
		return flowBuildSuccessRate;
	}
	public void setFlowBuildSuccessRate(String flowBuildSuccessRate) {
		this.flowBuildSuccessRate = flowBuildSuccessRate;
	}
	public String getFlowSeriousProblemNumber() {
		return flowSeriousProblemNumber;
	}
	public void setFlowSeriousProblemNumber(String flowSeriousProblemNumber) {
		this.flowSeriousProblemNumber = flowSeriousProblemNumber;
	}
	public String getFlowCommonProblemNumber() {
		return flowCommonProblemNumber;
	}
	public void setFlowCommonProblemNumber(String flowCommonProblemNumber) {
		this.flowCommonProblemNumber = flowCommonProblemNumber;
	}
	private String flawNumber;//漏洞数
	private String codeRepetitionRate;//重复率
	private String codeRows;//代码行数
	private String codeComplexity;//复杂度
	private String flowCompileNumber;//流水编译次数
	private String flowCompileSuccessRate;//编译成功次数
	private String flowBuildNumber;//构建次数
	private String flowBuildSuccessRate;//构建成功次数
	private String flowSeriousProblemNumber;//流水严重问题数
	private String flowCommonProblemNumber;//一般问题数
	@Override
	public String toString() {
		return "MeasureInfo [flowId=" + flowId + ", flowName=" + flowName
				+ ", problemNumber=" + problemNumber + ", flawNumber="
				+ flawNumber + ", codeRepetitionRate=" + codeRepetitionRate
				+ ", codeRows=" + codeRows + ", codeComplexity="
				+ codeComplexity + ", flowCompileNumber=" + flowCompileNumber
				+ ", flowCompileSuccessRate=" + flowCompileSuccessRate
				+ ", flowBuildNumber=" + flowBuildNumber
				+ ", flowBuildSuccessRate=" + flowBuildSuccessRate
				+ ", flowSeriousProblemNumber=" + flowSeriousProblemNumber
				+ ", flowCommonProblemNumber=" + flowCommonProblemNumber + "]";
	}
}


