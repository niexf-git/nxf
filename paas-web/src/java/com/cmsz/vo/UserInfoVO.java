package com.cmsz.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户定义
 * 
 * @author 林绵炎 
 * @version 创建时间：2016年11月18日 下午5:25:13
 */
public class UserInfoVO implements Serializable {

	private static final long serialVersionUID = -675389784876238114L;

	private String loginUser; // 登录用户（账号）

	private String password; // 密码（密文） 4A方提供加解密方式

	private String staffName; // （姓名）

	private String flag; // （有效标志）

	private String validLength; // （有效期）

	private Date expireDate; // （失效时间）

	private String logLock; // （锁定状态） 0，禁用；1，启用；9删除

	private String departmentCode; // （所属部门，多个部门用逗号隔开）

	private String workCode; // （所属工作组，多个组用逗号隔开）

	private String note; // （备注）

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getValidLength() {
		return validLength;
	}

	public void setValidLength(String validLength) {
		this.validLength = validLength;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getLogLock() {
		return logLock;
	}

	public void setLogLock(String logLock) {
		this.logLock = logLock;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}