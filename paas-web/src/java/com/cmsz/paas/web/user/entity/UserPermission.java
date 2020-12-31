package com.cmsz.paas.web.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;

/**
 * 用户拥有权限
 * 
 * @author zhouyunxia
 * 
 */
public class UserPermission {
	
	/** 数据权限集群 */
//	List<ClusterInfo> clusterList;
	
	/** 操作权限菜单 */
	List<OperPermission> operPermissionList;
	
	/** 没有权限菜单 */
	private List<OperPermission> unOperPermissionList;
	
	private List<RolePermissionRelation> rolePermissionList;

	public List<RolePermissionRelation> getRolePermissionList() {
		return rolePermissionList;
	}

	public void setRolePermissionList(
			List<RolePermissionRelation> rolePermissionList) {
		this.rolePermissionList = rolePermissionList;
	}

//	public List<ClusterInfo> getClusterList() {
//		return clusterList;
//	}
//
//	public void setClusterList(List<ClusterInfo> clusterList) {
//		this.clusterList = clusterList;
//		if (clusterList == null) {
//			this.clusterList = new ArrayList<ClusterInfo>();
//		}
//	}

	public List<OperPermission> getOperPermissionList() {
		return operPermissionList;
	}

	public void setOperPermissionList(List<OperPermission> operPermissionList) {
		this.operPermissionList = operPermissionList;
		if (operPermissionList == null) {
			this.operPermissionList = new ArrayList<OperPermission>();
		}
	}

	public List<OperPermission> getUnOperPermissionList() {
		return unOperPermissionList;
	}

	public void setUnOperPermissionList(
			List<OperPermission> unOperPermissionList) {
		this.unOperPermissionList = unOperPermissionList;
		if (unOperPermissionList == null) {
			this.unOperPermissionList = new ArrayList<OperPermission>();
		}
	}
}
