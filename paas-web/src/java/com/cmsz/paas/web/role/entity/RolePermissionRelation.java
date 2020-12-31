package com.cmsz.paas.web.role.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 角色权限管理类
 * 
 * @author zhouyunxia
 * 
 */
public class RolePermissionRelation implements Serializable {
	
	private static final long serialVersionUID = 4552822754671202202L;

	/** 主键ID */
	@ID
	private Long id;

	/** 角色ID */
	private Long roleId;

	/** 权限ID */
	private String permissionId;

	private String permissionName; // 权限名称，这里特指应用名称appname

	/** 迭代一权限类型：1为操作权限、2为数据权限 */
	/** 迭代二权限类型：1为操作权限、2为应用组、3为应用 */
	/** 迭代三权限类型，1表示操作权限，2表示数据权限，如应用 */
	private int permissionType;

	private int opertype; // 操作类型，如开发1，测试2，运维3，只有应用才有操作类型

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public int getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}

	public int getOpertype() {
		return opertype;
	}

	public void setOpertype(int opertype) {
		this.opertype = opertype;
	}

	@Override
	public String toString() {
		return "RolePermissionRelation [id=" + id + ", roleId=" + roleId
				+ ", permissionId=" + permissionId + ", permissionName="
				+ permissionName + ", permissionType=" + permissionType
				+ ", opertype=" + opertype + "]";
	}

}
