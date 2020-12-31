package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.StepRecordEntity;

public class StepRecordList {
	private List<StepRecordEntity> stepRecordList;

	public List<StepRecordEntity> getStepRecordList() {
		return stepRecordList;
	}

	public void setStepRecordList(List<StepRecordEntity> stepRecordList) {
		this.stepRecordList = stepRecordList;
	}

	@Override
	public String toString() {
		return "StepRecordList [stepRecordList=" + stepRecordList + "]";
	}
}
