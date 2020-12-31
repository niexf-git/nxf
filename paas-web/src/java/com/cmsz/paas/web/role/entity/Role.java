package com.cmsz.paas.web.role.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 角色实体类
 * 
 * @author zhouyunxia
 * 
 */
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 角色id */
	@ID
	private Long id; //主键自增实现方式

	/** 服务名 */
	private String roleName;
	
	private long roleId;

	/** 迭代二角色类型:0超级管理员，1普通管理员，2普通用户 */
	/** 迭代三角色类型:0超级管理员，1普通用户 */
	private int type;

	/** 服务描述 */
	private String description;

	/** 创建人 */
	private Long createBy;
	
	private boolean checked;
	
	/**
	 * 在应用中授予角色的时候，
	 * 角色是否已经被授予操作类型，
	 * 1-开发，2-测试
	 */
	private int hasOperType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", type=" + type
				+ ", description=" + description + ", createBy=" + createBy
				+ "]";
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getHasOperType() {
		return hasOperType;
	}

	public void setHasOperType(int hasOperType) {
		this.hasOperType = hasOperType;
	}	
	
}
