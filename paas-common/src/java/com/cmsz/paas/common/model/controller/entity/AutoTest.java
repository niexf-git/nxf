package com.cmsz.paas.common.model.controller.entity;

public class AutoTest {
	private int codeType;

	private String shellpath;

	private String userName;

	private String password;

	private String testCmd;

	private int failNumber;//失败数

	private int totals;//用例总数

	private int isChoise;

	private int status;

	private double time;
	
	private String aliasName;
	
	private String branchName;  //分支名称
	
	private String sysUer; //PAAS系统用户
	
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
	

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	@Override
	public String toString() {
		return "AutoTest [codeType=" + codeType + ", shellpath=" + shellpath + ", userName=" + userName + ", password="
				+ password + ", testCmd=" + testCmd + ", failNumber=" + failNumber + ", totals=" + totals
				+ ", isChoise=" + isChoise + ", status=" + status + ", time=" + time + ", aliasName=" + aliasName
				+ ", branchName=" + branchName + ", sysUer=" + sysUer + "]";
	}

	



}
