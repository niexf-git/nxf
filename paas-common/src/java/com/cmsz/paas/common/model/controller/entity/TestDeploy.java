package com.cmsz.paas.common.model.controller.entity;

public class TestDeploy {
	private String appSvcName;
	
	private String appSvcId;//服务id
	
	private String serviceRunVersion;//服务运行版本
	
	private long serviceRunVersionId;//服务运行版本id
	
	private int serviceState;//服务状态
	
	private int totalInstanceNum;//总实例数

	private int depType;

	private int isChoise;
	
	private int status;
	
	private double time;

	public String getAppSvcName() {
		return appSvcName;
	}

	public void setAppSvcName(String appSvcName) {
		this.appSvcName = appSvcName;
	}

	public int getDepType() {
		return depType;
	}

	public void setDepType(int depType) {
		this.depType = depType;
	}

	public int getIsChoise() {
		return isChoise;
	}

	public void setIsChoise(int isChoise) {
		this.isChoise = isChoise;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getAppSvcId() {
		return appSvcId;
	}

	public void setAppSvcId(String appSvcId) {
		this.appSvcId = appSvcId;
	}

	public String getServiceRunVersion() {
		return serviceRunVersion;
	}

	public void setServiceRunVersion(String serviceRunVersion) {
		this.serviceRunVersion = serviceRunVersion;
	}

	public long getServiceRunVersionId() {
		return serviceRunVersionId;
	}

	public void setServiceRunVersionId(long serviceRunVersionId) {
		this.serviceRunVersionId = serviceRunVersionId;
	}

	public int getServiceState() {
		return serviceState;
	}

	public void setServiceState(int serviceState) {
		this.serviceState = serviceState;
	}

	public int getTotalInstanceNum() {
		return totalInstanceNum;
	}

	public void setTotalInstanceNum(int totalInstanceNum) {
		this.totalInstanceNum = totalInstanceNum;
	}

	@Override
	public String toString() {
		return "TestDeploy [appSvcName=" + appSvcName + ", appSvcId=" + appSvcId + ", serviceRunVersion="
				+ serviceRunVersion + ", serviceRunVersionId=" + serviceRunVersionId + ", serviceState=" + serviceState
				+ ", totalInstanceNum=" + totalInstanceNum + ", depType=" + depType + ", isChoise=" + isChoise
				+ ", status=" + status + ", time=" + time + "]";
	}




}
