package com.cmsz.paas.web.permission.entity;

import java.util.List;

import com.cmsz.paas.web.role.entity.TreeNode;

/**
 * 
 * @author zhouyunxia
 *
 */
public class PermissionTree {
	/**操作权限*/
	private List<TreeNode> operPerTreeList;
	/**数据权限*/
	private List<TreeNode> dataPerTreeList;
	
	public List<TreeNode> getOperPerTreeList() {
		return operPerTreeList;
	}
	public void setOperPerTreeList(List<TreeNode> operPerTreeList) {
		this.operPerTreeList = operPerTreeList;
	}
	public List<TreeNode> getDataPerTreeList() {
		return dataPerTreeList;
	}
	public void setDataPerTreeList(List<TreeNode> dataPerTreeList) {
		this.dataPerTreeList = dataPerTreeList;
	}
}
