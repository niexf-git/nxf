package com.cmsz.paas.common.model.controller.response;

public class OverviewInstance {

	private String appId;//应用id
	private int allCount;//总数
	private int runCount;//运行中
	private int terminationCount;//停止中
	private int unknownCount;//未知状态
	private int unassignedCount;//未调度
	private int waitCount;//等待中
	private String type;//操作类型
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getRunCount() {
		return runCount;
	}
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
	public int getTerminationCount() {
		return terminationCount;
	}
	public void setTerminationCount(int terminationCount) {
		this.terminationCount = terminationCount;
	}
	public int getUnknownCount() {
		return unknownCount;
	}
	public void setUnknownCount(int unknownCount) {
		this.unknownCount = unknownCount;
	}
	public int getUnassignedCount() {
		return unassignedCount;
	}
	public void setUnassignedCount(int unassignedCount) {
		this.unassignedCount = unassignedCount;
	}
	public int getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "OverviewInstance [appId=" + appId + ", allCount=" + allCount + ", runCount=" + runCount
				+ ", terminationCount=" + terminationCount + ", unknownCount=" + unknownCount + ", unassignedCount="
				+ unassignedCount + ", waitCount=" + waitCount + ", type=" + type + "]";
	}
	
	
	
}
