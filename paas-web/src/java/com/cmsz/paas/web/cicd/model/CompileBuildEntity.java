package com.cmsz.paas.web.cicd.model;

import java.io.File;
import java.util.List;

/**
 * 编译&构建实体
 * 
 * @author liaohw
 * @date 2017-8-30
 */
public class CompileBuildEntity {
	/** 代码库列表 */
	private List<RepositoryInfo> repositoryInfo;

	/** Dockerfile文件路径 */
	private String dockerFilePath;

	/** 构建命令 */
	private String executeCommand;
	
	/** 单元测试选中 */
	private String isUnitTest;
	
	/** 单元测试报告目录 */
	private String unitTestReportDir;
	
	/** 单元测试报告入口文件 */
	private String unitTestReport;
	
	/** 覆盖率报告目录 */
	private String jacocoReportDir;
	
	/** 覆盖率报告入口文件 */
	private String jacocoReport;

	/** 镜像名称 */
	private String imageName;

	/** 镜像版本 */
	private String imageVersion;

	/** 卡片公共属性实体 */
	private StepDetailInfo stepDetailInfo;

	public List<RepositoryInfo> getRepositoryInfo() {
		return repositoryInfo;
	}

	public void setRepositoryInfo(List<RepositoryInfo> repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}

	public String getDockerFilePath() {
		return dockerFilePath;
	}

	public void setDockerFilePath(String dockerFilePath) {
		this.dockerFilePath = dockerFilePath;
	}

	public String getExecuteCommand() {
		return executeCommand;
	}

	public void setExecuteCommand(String executeCommand) {
		this.executeCommand = executeCommand;
	}

	public String getIsUnitTest() {
		return isUnitTest;
	}

	public void setIsUnitTest(String isUnitTest) {
		this.isUnitTest = isUnitTest;
	}

	public String getUnitTestReportDir() {
		return unitTestReportDir;
	}

	public void setUnitTestReportDir(String unitTestReportDir) {
		this.unitTestReportDir = unitTestReportDir;
	}

	public String getUnitTestReport() {
		return unitTestReport;
	}

	public void setUnitTestReport(String unitTestReport) {
		this.unitTestReport = unitTestReport;
	}

	public String getJacocoReportDir() {
		return jacocoReportDir;
	}

	public void setJacocoReportDir(String jacocoReportDir) {
		this.jacocoReportDir = jacocoReportDir;
	}

	public String getJacocoReport() {
		return jacocoReport;
	}

	public void setJacocoReport(String jacocoReport) {
		this.jacocoReport = jacocoReport;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public StepDetailInfo getStepDetailInfo() {
		return stepDetailInfo;
	}

	public void setStepDetailInfo(StepDetailInfo stepDetailInfo) {
		this.stepDetailInfo = stepDetailInfo;
	}

}
