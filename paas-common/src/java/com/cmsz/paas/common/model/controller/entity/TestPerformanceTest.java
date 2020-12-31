package com.cmsz.paas.common.model.controller.entity;

public class TestPerformanceTest {

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

@Override
public String toString() {
	return "TestPerformanceTest [codeType=" + codeType + ", shellpath=" + shellpath + ", userName=" + userName
			+ ", password=" + password + ", testCmd=" + testCmd + ", failNumber=" + failNumber + ", totals=" + totals
			+ ", miniRespTime=" + miniRespTime + ", averRespTime=" + averRespTime + ", maxRespTime=" + maxRespTime
			+ ", isChoise=" + isChoise + ", status=" + status + ", time=" + time + "]";
}






}
