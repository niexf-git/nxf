package com.cmsz.paas.common.model.controller.response;

public class OverviewFlow {
	
	private String appId;//应用id
	private int allCount;//总数
	private int succCount;//成功数
	private int failCount;//失败数
	private int runCount;//构建中
	private int unexeCount;//未执行
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
	public int getSuccCount() {
		return succCount;
	}
	public void setSuccCount(int succCount) {
		this.succCount = succCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public int getRunCount() {
		return runCount;
	}
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
	public int getUnexeCount() {
		return unexeCount;
	}
	public void setUnexeCount(int unexeCount) {
		this.unexeCount = unexeCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "OverviewFlow [appId=" + appId + ", allCount=" + allCount + ", succCount=" + succCount + ", failCount="
				+ failCount + ", runCount=" + runCount + ", unexeCount=" + unexeCount + ", type=" + type + "]";
	}
	
}
