package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.FlowRecordEntity;

public class FlowRecordList {
	private List<FlowRecordEntity> flowRecordList;

	public List<FlowRecordEntity> getFlowRecordList() {
		return flowRecordList;
	}

	public void setFlowRecordList(List<FlowRecordEntity> flowRecordList) {
		this.flowRecordList = flowRecordList;
	}

	@Override
	public String toString() {
		return "FlowRecordEntity [flowRecordList=" + flowRecordList + "]";
	}
}
