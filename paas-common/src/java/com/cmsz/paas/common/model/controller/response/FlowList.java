package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.FlowEntity;

public class FlowList {
	private List<FlowEntity> flowList;

	public List<FlowEntity> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<FlowEntity> flowList) {
		this.flowList = flowList;
	}

	@Override
	public String toString() {
		return "FlowEntity [flowList=" + flowList + "]";
	}
}
