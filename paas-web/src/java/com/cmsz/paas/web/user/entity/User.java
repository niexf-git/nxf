package com.cmsz.paas.web.user.entity;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 用户实体类
 * 
 * @author zhouyunxia
 * 
 */
public class User {

	/** id */
	@ID
	private Long id;
	/** 真实姓名 */
	private String userName;
	/** 登陆帐户名 */
	private String loginName;
	/** 登陆密码 */
	private String password;
	/** 电话 */
	private String phone;
	/** email */
	private String email;
	/** 部门 */
	// private String dept;
	/** 角色名称 */
	private String roleName;
	
	/** sessionId用来控制异地登录 */
	private String sessionId;
	
	/** 创建时间 */
	private String createTime;
	/** 创建人id */
	private Long createBy;

	/** 状态 */
	// private String state;
	/** 描述信息 */
	// private String description;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", loginName="
				+ loginName + ", password=" + password + ", phone=" + phone
				+ ", email=" + email + ", roleName=" + roleName
				+ ", createTime=" + createTime + ", createBy=" + createBy + "]";
	}
}
