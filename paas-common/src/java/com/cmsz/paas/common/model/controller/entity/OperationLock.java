package com.cmsz.paas.common.model.controller.entity;

import java.util.Date;

public class OperationLock {

	private Date startTime;
	private String appId;
	private String operationType;
	
	public OperationLock(Date startTime,String appId,String operationType)
	{
		this.startTime = startTime;
		this.appId = appId;
		this.operationType = operationType;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String toString()
	{
		return "Lock info: "
				+ "appId = " + appId 
				+ " , startTime = " +startTime.toString() 
				+ " , operationType " + operationType;
	}
}
