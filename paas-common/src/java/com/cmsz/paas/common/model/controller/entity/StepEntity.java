package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

public class StepEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	@ID
	private String uuid;
	private String flowId;
	private String aliasName;
	private String stepName;
	private int isChoise;
	
	private String lastBuildRecordId;//流水最近一次构建的recordId
	/**
	 * 步骤配置
	 * */
	private List<StepConfEntity> stepConfs;
	
	//private StepRecordEntity stepRecord;

	private List<StepRecordEntity> stepRecords;
	
	
	
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

	public int getIsChoise() {
		return isChoise;
	}

	public void setIsChoise(int isChoise) {
		this.isChoise = isChoise;
	}

	public List<StepConfEntity> getStepConfs() {
		return stepConfs;
	}

	public void setStepConfs(List<StepConfEntity> stepConfs) {
		this.stepConfs = stepConfs;
	}

	public List<StepRecordEntity> getStepRecords() {
		return stepRecords;
	}

	public void setStepRecords(List<StepRecordEntity> stepRecords) {
		this.stepRecords = stepRecords;
	}

	public String getLastBuildRecordId() {
		return lastBuildRecordId;
	}

	public void setLastBuildRecordId(String lastBuildRecordId) {
		this.lastBuildRecordId = lastBuildRecordId;
	}

	@Override
	public String toString() {
		return "StepEntity [uuid=" + uuid + ", flowId=" + flowId + ", aliasName=" + aliasName + ", stepName=" + stepName
				+ ", isChoise=" + isChoise + ", lastBuildRecordId=" + lastBuildRecordId + ", stepConfs=" + stepConfs
				+ ", stepRecords=" + stepRecords + "]";
	}



	

//	public List<StepRecordEntity> getStepRecordList() {
//		return stepRecordList;
//	}
//	public void setStepRecordList(List<StepRecordEntity> stepRecordList) {
//		this.stepRecordList = stepRecordList;
//	}
	
}
