package com.cmsz.paas.web.user.entity;

/**
 * @author lin.my
 * @version 创建时间：2017年3月21日 下午8:22:42
 */
public class UserErrorLoginRecord {
	
	private String loginTime; //登录时间
	private int errorNumber; //登录失败次数

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}
}