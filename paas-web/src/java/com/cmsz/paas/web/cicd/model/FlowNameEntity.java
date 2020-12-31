package com.cmsz.paas.web.cicd.model;

public class FlowNameEntity {

	//流水ID
	private String flowId;
	
	//流水名称
	private String flowName;
	
	//操作类型
	private int type;
	
	
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
}
