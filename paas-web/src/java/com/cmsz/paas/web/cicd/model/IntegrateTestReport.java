package com.cmsz.paas.web.cicd.model;
/**
 * 集成测试报表实体(外)
 * 
 * @author ccl
 * @date 2017-11-23
 */
public class IntegrateTestReport {
	/** 流水ID */
	private String flowId;
	/** 流水名称 */
	private String flowName;
	/** 构建次数 */
	private int buildNum;
	/** 用例总数 */
	private String useCaseNum;
	/** 问题总数 */
	private int problemNum;
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
	public String getUseCaseNum() {
		return useCaseNum;
	}
	public void setUseCaseNum(String useCaseNum) {
		this.useCaseNum = useCaseNum;
	}
	public int getProblemNum() {
		return problemNum;
	}
	public void setProblemNum(int problemNum) {
		this.problemNum = problemNum;
	}





	
	
}
