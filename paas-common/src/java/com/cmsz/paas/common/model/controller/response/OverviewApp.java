package com.cmsz.paas.common.model.controller.response;

public class OverviewApp {

	private String appId;//应用id
	private int allCount;//总数
	private int runCount;//运行中
	private int stopCount;//已停止
	private int operCount;//操作中
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
	public int getStopCount() {
		return stopCount;
	}
	public void setStopCount(int stopCount) {
		this.stopCount = stopCount;
	}
	public int getOperCount() {
		return operCount;
	}
	public void setOperCount(int operCount) {
		this.operCount = operCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "OverviewApp [appId=" + appId + ", allCount=" + allCount + ", runCount=" + runCount + ", stopCount="
				+ stopCount + ", operCount=" + operCount + ", type=" + type + "]";
	}
	
	
	
}
