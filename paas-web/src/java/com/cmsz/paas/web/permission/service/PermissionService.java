package com.cmsz.paas.web.permission.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.role.entity.TreeNode;

/**
 * 权限Service.
 *
 * @author zhouyunxia
 */
public interface PermissionService {

	/**
	 * 查询权限树形结构.
	 *
	 * @return List TreeNode 权限树形结构集合
	 * @throws PaasWebException
	 */
	public List<TreeNode> queryOperPerTree() throws PaasWebException;

	/**
	 * 查询操作权限集合.
	 *
	 * @return List OperPermission 权限集合
	 * @throws PaasWebException
	 */
	public List<OperPermission> queryOperPermission() throws PaasWebException;

}
