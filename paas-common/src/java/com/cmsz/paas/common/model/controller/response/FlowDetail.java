package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.model.controller.entity.FlowEntity;
import com.cmsz.paas.common.model.controller.entity.PrivateImageEntity;

public class FlowDetail implements Serializable {

	/**
	 * @author guyj
	 * @time 2017-8-24
	 */
	private static final long serialVersionUID = 1L;
	
	private FlowEntity flowEntity;

	public FlowEntity getFlowEntity() {
		return flowEntity;
	}

	public void setFlowEntity(FlowEntity flowEntity) {
		this.flowEntity = flowEntity;
	}

	@Override
	public String toString() {
		return "FlowDetail [flowEntity=" + flowEntity + "]";
	}
	

}
