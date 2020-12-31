package com.cmsz.paas.common.model.controller.response;
/**
*@author chenlq	
*@date 2018年5月8日 --- 上午10:09:26
**/
public class OverviewRealResource {
	 private String appId;//一个或多个应用ID
     private long realCpu;
     private long totailCpu;
     private long realMemory;
     private long totailMemory;
     private long realDisk;
     private long totailDisk;
     private String type;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public long getRealCpu() {
		return realCpu;
	}
	public void setRealCpu(long realCpu) {
		this.realCpu = realCpu;
	}
	public long getTotailCpu() {
		return totailCpu;
	}
	public void setTotailCpu(long totailCpu) {
		this.totailCpu = totailCpu;
	}
	public long getRealMemory() {
		return realMemory;
	}
	public void setRealMemory(long realMemory) {
		this.realMemory = realMemory;
	}
	public long getTotailMemory() {
		return totailMemory;
	}
	public void setTotailMemory(long totailMemory) {
		this.totailMemory = totailMemory;
	}
	public long getRealDisk() {
		return realDisk;
	}
	public void setRealDisk(long realDisk) {
		this.realDisk = realDisk;
	}
	public long getTotailDisk() {
		return totailDisk;
	}
	public void setTotailDisk(long totailDisk) {
		this.totailDisk = totailDisk;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "OverviewRealResource [appId=" + appId + ", realCpu=" + realCpu + ", totailCpu=" + totailCpu
				+ ", realMemory=" + realMemory + ", totailMemory=" + totailMemory + ", realDisk=" + realDisk
				+ ", totailDisk=" + totailDisk + ", type=" + type + "]";
	}

	
	
	

}

