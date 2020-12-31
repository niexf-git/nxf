package com.cmsz.paas.web.user.model;

import java.io.Serializable;

/**
 * 用户信息
 * 
 */
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**用户ID*/
	private String userId;
	/**用户名字*/
	private String userName;
	/**用户密码*/
	private String password;
	/**旧密码*/
	private String oldPassword;
	/**用户角色*/
	private int role;
	/**用户邮箱*/
	private String mail;
	
	public String getMail() {
		return mail;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public String getPassword() {
		return password;
	}
	public int getRole() {
		return role;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName
				+ ", password=" + password + ", oldPassword=" + oldPassword
				+ ", role=" + role + ", mail=" + mail + "]";
	}
}
