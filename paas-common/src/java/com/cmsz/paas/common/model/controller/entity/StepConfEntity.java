package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

public class StepConfEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ID
	private String uuid;
	
	private String stepId;
	
	private String stepName;
	
	private String prarmName;
	
	private String prarmValue;
	
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getPrarmName() {
		return prarmName;
	}
	public void setPrarmName(String prarmName) {
		this.prarmName = prarmName;
	}
	public String getPrarmValue() {
		return prarmValue;
	}
	public void setPrarmValue(String prarmValue) {
		this.prarmValue = prarmValue;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return "StepConfEntity [uuid=" + uuid + ", stepId=" + stepId
				+ ", stepName=" + stepName + ", prarmName=" + prarmName
				+ ", prarmValue=" + prarmValue + "]";
	}
	
}
