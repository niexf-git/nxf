package com.cmsz.paas.web.user.entity;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 用户角色管理类
 * 
 * @author zhouyunxia
 * 
 */
public class UserRoleRelation {
	/** 主键ID */
	@ID
	private Long id;

	/** 用户ID */
	private Long userId;

	/** 角色ID */
	private Long roleId;

	/** 角色类型 */
	private int roleType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "UserRoleRelation [id=" + id + ", userId=" + userId
				+ ", roleId=" + roleId + ", roleType=" + roleType + "]";
	}
}