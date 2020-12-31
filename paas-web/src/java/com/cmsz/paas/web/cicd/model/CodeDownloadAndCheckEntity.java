package com.cmsz.paas.web.cicd.model;

import java.util.List;

/**
 * 代码下载&审查 实体
 * 
 * @author liaohw
 * @date 2017-8-25
 */
public class CodeDownloadAndCheckEntity {

	/** 代码库类型 */
	private String repositoryType;

	/** 触发类型 */
	private String triggerType;

	/** 触发时间 */
	private String triggerTime;

	/** 是否代码扫描 1-是，0-否 */
	private String isCodeCheck;

	/** 代码库列表 */
	private List<RepositoryInfo> repositoryInfo;

	/** 卡片公共属性实体 */
	private StepDetailInfo stepDetailInfo;

	/** 需要扫描的目录，为空扫描所有工程 */
	private String scanDir;

	/** 代码库用户名 */
	private String name;

	/** 代码库密码 */
	private String password;

	public StepDetailInfo getStepDetailInfo() {
		return stepDetailInfo;
	}

	public void setStepDetailInfo(StepDetailInfo stepDetailInfo) {
		this.stepDetailInfo = stepDetailInfo;
	}

	public String getRepositoryType() {
		return repositoryType;
	}

	public void setRepositoryType(String repositoryType) {
		this.repositoryType = repositoryType;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}

	public String getIsCodeCheck() {
		return isCodeCheck;
	}

	public void setIsCodeCheck(String isCodeCheck) {
		this.isCodeCheck = isCodeCheck;
	}

	public List<RepositoryInfo> getRepositoryInfo() {
		return repositoryInfo;
	}

	public void setRepositoryInfo(List<RepositoryInfo> repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}

	public String getScanDir() {
		return scanDir;
	}

	public void setScanDir(String scanDir) {
		this.scanDir = scanDir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "CodeDownloadAndCheckEntity [repositoryType=" + repositoryType
				+ ", triggerType=" + triggerType + ", triggerTime="
				+ triggerTime + ", isCodeCheck=" + isCodeCheck
				+ ", repositoryInfo=" + repositoryInfo + ", stepDetailInfo="
				+ stepDetailInfo + ", scanDir=" + scanDir + ", name=" + name
				+ ", password=" + password + "]";
	}

}
