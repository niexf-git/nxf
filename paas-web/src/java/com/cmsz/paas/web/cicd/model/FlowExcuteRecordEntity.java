package com.cmsz.paas.web.cicd.model;

/**
 * 流水执行记录实体
 * 
 * @author lin.my
 * @version 创建时间：2017年9月3日 下午1:56:28
 */
public class FlowExcuteRecordEntity {

	private String flowRecordId;
	private String flowId; // 流水Id
	private String aliasName;
	private String buildNum; // 构建次数
	private String beginTime; // 创建时间
	private double runTime; // 耗时
	private String tag;
	/*
	 * 流水执行状态，0-未执行，1-SUCESS 2-FAILED  3-RUNNING  4-WAITING  5-DELETE
	 */
	private int status; // 执行状态
	private String imageVersion; // 镜像版本
	private String codeVersion; // 代码版本
	private String triggerBy;

	public String getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(String flowRecordId) {
		this.flowRecordId = flowRecordId;
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

	public String getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(String buildNum) {
		this.buildNum = buildNum;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public double getRunTime() {
		return runTime;
	}

	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public String getCodeVersion() {
		return codeVersion;
	}

	public void setCodeVersion(String codeVersion) {
		this.codeVersion = codeVersion;
	}

	public String getTriggerBy() {
		return triggerBy;
	}

	public void setTriggerBy(String triggerBy) {
		this.triggerBy = triggerBy;
	}

	/**
	 * 步骤记录
	 * */
	// private List<StepRecordEntity> stepRecords;
	//
	// private PrivateImageEntity privateImage;

}
