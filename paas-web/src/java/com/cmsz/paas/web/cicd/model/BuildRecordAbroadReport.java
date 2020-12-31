package com.cmsz.paas.web.cicd.model;

import java.io.Serializable;

/**
 * 构建记录（外）报表entity
 */
public class BuildRecordAbroadReport implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String flowId;//流水Id
	private String flowName;//流水名称
	private String buildNum;//构建测试
	private String downloadCheck ;//下载检查
	private String compileBuild;//编译构建
	public String getFlowId() {
		return flowId;
	}
	@Override
	public String toString() {
		return "BuildRecordAbroadReport [flowId=" + flowId + ", flowName=" + flowName + ", buildNum=" + buildNum
				+ ", downloadCheck=" + downloadCheck + ", compileBuild=" + compileBuild + ", deployment=" + deployment
				+ ", autoTest=" + autoTest + ", integrateTest=" + integrateTest + ", performanceTest=" + performanceTest
				+ ", releaseTest=" + releaseTest + "]";
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getBuildNum() {
		return buildNum;
	}
	public void setBuildNum(String buildNum) {
		this.buildNum = buildNum;
	}
	public String getDownloadCheck() {
		return downloadCheck;
	}
	public void setDownloadCheck(String downloadCheck) {
		this.downloadCheck = downloadCheck;
	}
	public String getCompileBuild() {
		return compileBuild;
	}
	public void setCompileBuild(String compileBuild) {
		this.compileBuild = compileBuild;
	}
	public String getDeployment() {
		return deployment;
	}
	public void setDeployment(String deployment) {
		this.deployment = deployment;
	}
	public String getAutoTest() {
		return autoTest;
	}
	public void setAutoTest(String autoTest) {
		this.autoTest = autoTest;
	}
	public String getIntegrateTest() {
		return integrateTest;
	}
	public void setIntegrateTest(String integrateTest) {
		this.integrateTest = integrateTest;
	}
	public String getPerformanceTest() {
		return performanceTest;
	}
	public void setPerformanceTest(String performanceTest) {
		this.performanceTest = performanceTest;
	}
	public String getReleaseTest() {
		return releaseTest;
	}
	public void setReleaseTest(String releaseTest) {
		this.releaseTest = releaseTest;
	}
	private String deployment;//发布
	private String autoTest;//自动测试
	private String integrateTest ;//集成测试
	private String performanceTest;//性能测试
	private String releaseTest;//发布测试
	
}
