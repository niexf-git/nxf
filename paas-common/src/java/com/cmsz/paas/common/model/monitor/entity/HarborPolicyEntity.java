package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

public class HarborPolicyEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ID
	private long id;
	
	private long dataCenterId;
	
	private long harborPolicyId;
	
	private long appId;
	
	@Override
	public String toString() {
		return "HarborPolicyEntity "
				+ "[id=" + id 
				+ ", dataCenterId=" + dataCenterId
				+ ", harborPolicyId=" + harborPolicyId 
				+ ", appId=" + appId + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(long dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public long getHarborPolicyId() {
		return harborPolicyId;
	}

	public void setHarborPolicyId(long harborPolicyId) {
		this.harborPolicyId = harborPolicyId;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}
}
