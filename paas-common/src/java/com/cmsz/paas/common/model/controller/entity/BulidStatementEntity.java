package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class BulidStatementEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String flowName;//流水名称
	private Integer buildNum;//构建次数
	private String downloadCheck;//下载审查
	private String build;//编辑构建
	private String depscan;//部署扫描
	private String intetest;//集成测试
	private String autotest;//自动测试
	private String performance;//性能测试
	private String release;//发布测试
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public Integer getBuildNum() {
		return buildNum;
	}
	public void setBuildNum(Integer buildNum) {
		this.buildNum = buildNum;
	}
	public String getDownloadCheck() {
		return downloadCheck;
	}
	public void setDownloadCheck(String downloadCheck) {
		this.downloadCheck = downloadCheck;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getDepscan() {
		return depscan;
	}
	public void setDepscan(String depscan) {
		this.depscan = depscan;
	}
	public String getIntetest() {
		return intetest;
	}
	public void setIntetest(String intetest) {
		this.intetest = intetest;
	}
	public String getAutotest() {
		return autotest;
	}
	public void setAutotest(String autotest) {
		this.autotest = autotest;
	}
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
}
