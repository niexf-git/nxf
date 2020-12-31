package com.cmsz.vo;

/**
 * 为了验证应用系统用户登入应用系统的认证情况，应用系统需要调用4A平台此接口，4A平台根据传入的参数返回认证情况。
 * 应用系统调用该接口时，根据4A平台访问应用系统时带的Token串，解析出Token、IP 、APP_KEY 、ACC_KEY参数，提交给4A平台。
 * 4A平台返回该应用系统的认证情况并返回具有授权的应用系统帐号。
 * 
 * @author 林绵炎
 * @version 创建时间：2016年11月25日 下午2:52:03
 */
public class Account2Login4A {

	/*
	 * 4A发送过来的令牌码
	 */
	private String token;

	/*
	 * 4A从账号
	 */
	private String slaveAccount;

	/*
	 * 4A主账号
	 */
	private String masterAccount;

	/*
	 * 系统ID
	 */
	private String appId;

	/*
	 * IP地址
	 */
	private String ip;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSlaveAccount() {
		return slaveAccount;
	}

	public void setSlaveAccount(String slaveAccount) {
		this.slaveAccount = slaveAccount;
	}

	public String getMasterAccount() {
		return masterAccount;
	}

	public void setMasterAccount(String masterAccount) {
		this.masterAccount = masterAccount;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
