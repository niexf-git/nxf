package com.cmsz.paas.web.cicd.model;

/**
 * 流水步骤
 * 
 * @author lin.my
 * @version 创建时间：2017年8月31日 下午2:16:17
 */
public class StepInfo {

	private String uuid;
	private String flowId;
	private String aliasName;
	private String stepName;
	private String isChoise;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getIsChoise() {
		return isChoise;
	}

	public void setIsChoise(String isChoise) {
		this.isChoise = isChoise;
	}

	/**
	 * 步骤配置
	 * */
	// private List<StepConfEntity> stepConfs;
	//
	// private StepRecordEntity stepRecord;
	//
	// private List<StepRecordEntity> stepRecordList;

}
