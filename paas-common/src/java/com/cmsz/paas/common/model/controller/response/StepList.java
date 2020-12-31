package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.StepEntity;

public class StepList {
	private List<StepEntity> StepList;

	public List<StepEntity> getStepRecordList() {
		return StepList;
	}

	public void setStepRecordList(List<StepEntity> StepList) {
		this.StepList = StepList;
	}

	@Override
	public String toString() {
		return "StepEntity [StepList=" + StepList + "]";
	}
}
