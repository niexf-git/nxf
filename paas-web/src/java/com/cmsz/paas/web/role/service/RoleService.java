package com.cmsz.paas.web.role.service;

import java.util.List;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.entity.TreeNode;

/**
 * 角色管理类Service.
 *
 * @author zhouyunxia
 */
public interface RoleService {

	/**
	 * 查询角色列表.
	 *
	 * @param pc 查询条件
	 * @return page 返回角色结果
	 * @throws PaasWebException
	 */
	public Page<Role> findPage(PageContext pc) throws PaasWebException;

	/**
	 * 查询所有角色.
	 *
	 * @return List Role 角色集合
	 * @throws PaasWebException
	 */
	public List<Role> queryAll() throws PaasWebException;

	/**
	 * 查询角色树形结构.
	 *
	 * @return List TreeNode 角色的树形结构集合
	 * @throws PaasWebException
	 */
	public List<TreeNode> queryRoleTreeNode() throws PaasWebException;
	
	/**
	 * 查询角色树形结构.
	 * 
	 * @param id 登录用户id
	 * @return List TreeNode 角色的树形结构集合
	 * @throws PaasWebException
	 */
	public List<TreeNode> queryRoleTreeNode(Long id)throws PaasWebException;

	/**
	 * 删除角色.
	 *
	 * @param roleId 角色id
	 * @throws PaasWebException
	 */
	public void deleteRole(Long roleId) throws PaasWebException;

	/**
	 * 新增角色.
	 *
	 * @param role 角色信息
	 * @param operSelectNode 角色拥有的操作权限
	 * @param dataSelectNode 角色拥有的数据权限
	 * @throws PaasWebException
	 */
	public void createRole(Role role, String operSelectNode,String dataSelectNode) throws PaasWebException;

	/**
	 * 通过角色id查询角色信息.
	 *
	 * @param roleId 角色id
	 * @return Role 角色信息
	 * @throws PaasWebException
	 */
	public Role queryRole(Long roleId) throws PaasWebException;

	/**
	 * 修改角色信息.
	 *
	 * @param role 角色信息
	 * @param deletePermissionList 角色删除的权限集合
	 * @param addPermissionList 角色新增的权限集合
	 * @throws PaasWebException
	 */
	public void updateRole(Role role,
			List<RolePermissionRelation> deletePermissionList,
			List<RolePermissionRelation> addPermissionList) throws PaasWebException;

	/**
	 * 通过权限名称查询角色信息.
	 *
	 * @param roleName 角色名称
	 * @return Role 角色信息
	 * @throws PaasWebException
	 */
	public Role queryRoleByName(String roleName) throws PaasWebException;
	
	
}
