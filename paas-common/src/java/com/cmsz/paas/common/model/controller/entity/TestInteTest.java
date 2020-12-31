package com.cmsz.paas.common.model.controller.entity;

public class TestInteTest {

private int codeType;

private int inteType;

private String shellpath;

private String userName;

private String password;

private String testCmd;

private String relationName;

private int failNumber;

private int totals;

private int isChoise;

private int status;

private double time;

public int getCodeType() {
	return codeType;
}

public void setCodeType(int codeType) {
	this.codeType = codeType;
}

public int getInteType() {
	return inteType;
}

public void setInteType(int inteType) {
	this.inteType = inteType;
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

public String getRelationName() {
	return relationName;
}

public void setRelationName(String relationName) {
	this.relationName = relationName;
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
	return "TestInteTest [codeType=" + codeType + ", inteType=" + inteType + ", shellpath=" + shellpath + ", userName="
			+ userName + ", password=" + password + ", testCmd=" + testCmd + ", relationName=" + relationName
			+ ", failNumber=" + failNumber + ", totals=" + totals + ", isChoise=" + isChoise + ", status=" + status
			+ ", time=" + time + "]";
}





}
