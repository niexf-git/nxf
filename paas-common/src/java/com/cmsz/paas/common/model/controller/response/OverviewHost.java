package com.cmsz.paas.common.model.controller.response;

public class OverviewHost {
	private String appId;//一个或多个应用ID
	private long hostCount; //主机数
	private long cpuCount; //cpu总量
	private long memoryCount; //内存总量
	private long diskCount; //磁盘总量
	private String type; //操作类型
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public long getHostCount() {
		return hostCount;
	}
	public void setHostCount(long hostCount) {
		this.hostCount = hostCount;
	}
	public long getCpuCount() {
		return cpuCount;
	}
	public void setCpuCount(long cpuCount) {
		this.cpuCount = cpuCount;
	}
	public long getMemoryCount() {
		return memoryCount;
	}
	public void setMemoryCount(long memoryCount) {
		this.memoryCount = memoryCount;
	}
	public long getDiskCount() {
		return diskCount;
	}
	public void setDiskCount(long diskCount) {
		this.diskCount = diskCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "OverviewHost [appId=" + appId + ", hostCount=" + hostCount + ", cpuCount=" + cpuCount + ", memoryCount="
				+ memoryCount + ", diskCount=" + diskCount + ", type=" + type + "]";
	}

	
	
 
}

