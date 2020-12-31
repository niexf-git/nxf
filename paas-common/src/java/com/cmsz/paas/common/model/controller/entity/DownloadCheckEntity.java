package com.cmsz.paas.common.model.controller.entity;

import java.util.List;

public class DownloadCheckEntity {

	/**
	 * 代码库类型（1-svn，2-git）
	 */
	private int codeType;

	/**
	 * 触发类型（1-手动，2-自动,3-定时,4-代码提交）
	 */
	private int triggerType;

	/**
	 * 定时配置信息
	 */
	private String pollScm;

	/**
	 * 是否代码检查（1-是，0-否）
	 */
	private int isCheck;

	/**
	 * 是否选中，0-未选择，1-选中
	 */
	private int isChoise;

	/**
	 * 状态，空-未执行，0-成功。1-未成功
	 */
	private int status;

	/**
	 * 时间
	 */
	private double time;

	/**
	 * 步骤ID
	 */
	private String stepId;

	/**
	 * 步骤名称
	 */
	private String stepName;

	/**
	 * 流水名+step名
	 */
	private String aliasName;

	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 构建次数
	 */
	private int buildNumber;
	
	/**
	 * 代码扫描路径
	 */
	private String checkPath;

	public String getCheckPath() {
		return checkPath;
	}

	public void setCheckPath(String checkPath) {
		this.checkPath = checkPath;
	}

	/**
	 * 代码库
	 */
	private List<CodePathEntity> codes;
	
	private CodeAccountEntity codeAccount;

	public CodeAccountEntity getCodeAccount() {
		return codeAccount;
	}

	public void setCodeAccount(CodeAccountEntity codeAccount) {
		this.codeAccount = codeAccount;
	}

	public int getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public int getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(int triggerType) {
		this.triggerType = triggerType;
	}

	public String getPollScm() {
		return pollScm;
	}

	public void setPollScm(String pollScm) {
		this.pollScm = pollScm;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public int getIsChoise() {
		return isChoise;
	}

	public void setIsChoise(int isChoise) {
		this.isChoise = isChoise;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<CodePathEntity> getCodes() {
		return codes;
	}

	public void setCodes(List<CodePathEntity> codes) {
		this.codes = codes;
	}

	@Override
	public String toString() {
		return "DownloadCheckEntity [codeType=" + codeType + ", triggerType="
				+ triggerType + ", pollScm=" + pollScm + ", isCheck=" + isCheck
				+ ", isChoise=" + isChoise + ", status=" + status + ", time="
				+ time + ", stepId=" + stepId + ", stepName=" + stepName
				+ ", aliasName=" + aliasName + ", appName=" + appName
				+ ", buildNumber=" + buildNumber + ", checkPath=" + checkPath
				+ ", codes=" + codes + ", codeAccount=" + codeAccount + "]";
	}

}
