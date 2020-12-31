package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.StepRecordEntity;

public class ReportDownloadList {

	private List<StepRecordEntity> stepRecord;

	public List<StepRecordEntity> getStepRecord() {
		return stepRecord;
	}

	public void setStepRecord(List<StepRecordEntity> stepRecord) {
		this.stepRecord = stepRecord;
	}
	
}
