package com.cmsz.paas.common.model.controller.response;

public class OverviewAssResource {
	private String appId;//一个或多个应用ID
	private long assCpu; //已分配CPU
	private long totailCpu; //cpu总量
	private long assMemory; //分配的内存
	private long totailMemory; //内存总量
	private String type; //操作类型
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public long getAssCpu() {
		return assCpu;
	}
	public void setAssCpu(long assCpu) {
		this.assCpu = assCpu;
	}
	public long getTotailCpu() {
		return totailCpu;
	}
	public void setTotailCpu(long totailCpu) {
		this.totailCpu = totailCpu;
	}
	public long getAssMemory() {
		return assMemory;
	}
	public void setAssMemory(long assMemory) {
		this.assMemory = assMemory;
	}
	public long getTotailMemory() {
		return totailMemory;
	}
	public void setTotailMemory(long totailMemory) {
		this.totailMemory = totailMemory;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "OverviewAssResource [appId=" + appId + ", assCpu=" + assCpu + ", totailCpu=" + totailCpu
				+ ", assMemory=" + assMemory + ", totailMemory=" + totailMemory + ", type=" + type + "]";
	}


	
	
}


