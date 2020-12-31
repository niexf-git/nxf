package com.cmsz.paas.common.model.controller.entity;

import com.cmsz.paas.common.orm.id.ID;

public class PerformanceTest {
	
	@ID
	private String uuid;
	
	private String stepRecordId;
	
	private int successNumber;
	
	private int codeType;

	private String shellpath;

	private String userName;

	private String password;

	private String testCmd;

	private int failNumber;

	private int totals;
	
	/** 最短响应时间 */
	private double miniRespTime;
	/** 平均响应时间  */
	private double averRespTime;
	/** 最长响应时间  */
	private double maxRespTime;

	private int isChoise;

	private int status;

	private double time;
	
	private String aliasName;
	
	private String branchName;  //分支名称
	
	private String sysUer; //PAAS系统用户
	
	private String flowName;//流水名称
	private Integer buildNum;//执行次数
	private String flowId;//流水id
	
	private String coder; //代码负责人
	
	public int getSuccessNumber() {
		return successNumber;
	}

	public void setSuccessNumber(int successNumber) {
		this.successNumber = successNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStepRecordId() {
		return stepRecordId;
	}

	public void setStepRecordId(String stepRecordId) {
		this.stepRecordId = stepRecordId;
	}

	public String getSysUer() {
		return sysUer;
	}

	public void setSysUer(String sysUer) {
		this.sysUer = sysUer;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public String getShellpath() {
		return shellpath;
	}

	public void setShellpath(String shellpath) {
		this.shellpath = shellpath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTestCmd() {
		return testCmd;
	}

	public void setTestCmd(String testCmd) {
		this.testCmd = testCmd;
	}

	public double getMiniRespTime() {
		return miniRespTime;
	}

	public void setMiniRespTime(double miniRespTime) {
		this.miniRespTime = miniRespTime;
	}

	public double getAverRespTime() {
		return averRespTime;
	}

	public void setAverRespTime(double averRespTime) {
		this.averRespTime = averRespTime;
	}

	public double getMaxRespTime() {
		return maxRespTime;
	}

	public void setMaxRespTime(double maxRespTime) {
		this.maxRespTime = maxRespTime;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public int getFailNumber() {
		return failNumber;
	}

	public void setFailNumber(int failNumber) {
		this.failNumber = failNumber;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

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

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getCoder() {
		return coder;
	}

	public void setCoder(String coder) {
		this.coder = coder;
	}

	@Override
	public String toString() {
		return "PerformanceTest [uuid=" + uuid + ", stepRecordId="
				+ stepRecordId + ", successNumber=" + successNumber
				+ ", codeType=" + codeType + ", shellpath=" + shellpath
				+ ", userName=" + userName + ", password=" + password
				+ ", testCmd=" + testCmd + ", failNumber=" + failNumber
				+ ", totals=" + totals + ", miniRespTime=" + miniRespTime
				+ ", averRespTime=" + averRespTime + ", maxRespTime="
				+ maxRespTime + ", isChoise=" + isChoise + ", status=" + status
				+ ", time=" + time + ", aliasName=" + aliasName
				+ ", branchName=" + branchName + ", sysUer=" + sysUer
				+ ", flowName=" + flowName + ", buildNum=" + buildNum
				+ ", flowId=" + flowId + ", coder=" + coder + "]";
	}
}
