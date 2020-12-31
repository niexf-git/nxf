package com.cmsz.paas.common.model.controller.request;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.DisasterToleranceConfEntity;

public class DisasterToleranceConfList {
	
	private long appId;
	
	private String appName;

	private List<DisasterToleranceConfEntity> disasterToleranceConfList;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public List<DisasterToleranceConfEntity> getDisasterToleranceConfList() {
		return disasterToleranceConfList;
	}

	public void setDisasterToleranceConfList(
			List<DisasterToleranceConfEntity> disasterToleranceConfList) {
		this.disasterToleranceConfList = disasterToleranceConfList;
	}
	
}
