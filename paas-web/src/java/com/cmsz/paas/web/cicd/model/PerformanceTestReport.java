package com.cmsz.paas.web.cicd.model;

/**
 * 性能测试报表实体（外）
 * 
 * @author lin.my
 * @version 创建时间：2017年11月27日 下午4:23:13
 */
public class PerformanceTestReport {
	/** 流水ID */
	private String flowId;
	/** 流水名称 */
	private String flowName;
	/** 构建次数 */
	private int buildNum;
	/** 总执行次数 */
	private String executionNum;
	/** 失败总数 */
	private int failNum;

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

	public String getExecutionNum() {
		return executionNum;
	}

	public void setExecutionNum(String executionNum) {
		this.executionNum = executionNum;
	}

	public int getFailNum() {
		return failNum;
	}

	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}

}
