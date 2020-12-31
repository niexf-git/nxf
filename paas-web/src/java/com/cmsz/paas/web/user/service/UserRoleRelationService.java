package com.cmsz.paas.web.user.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.user.entity.UserRoleRelation;


/**
 * 用户角色管理service.
 *
 * @author zhouyunxia
 */
public interface UserRoleRelationService {
	
	/**
	 * 查询用户关联角色信息.
	 *
	 * @param userId 用户id
	 * @return List UserRoleRelation 用户管理角色信息集合
	 * @throws PaasWebException
	 */
	public List<UserRoleRelation> queryByUser(Long userId) throws PaasWebException;
	
	/**
	 * 删除用户与角色的管理关系.
	 *
	 * @param userId 用户id
	 * @throws PaasWebException
	 */
	public void deleteByUser(Long userId) throws PaasWebException;

	/**
	 * 更新用户与角色的关联关系.
	 *
	 * @param deleteRole 删除的角色关联
	 * @param addRole 新增的角色关联
	 * @throws PaasWebException
	 */
	public void updateUserRole(List<UserRoleRelation> deleteRole, List<UserRoleRelation> addRole) throws PaasWebException;

	/**
	 * 通过角色id查询用户与角色的关联.
	 *
	 * @param roleId 角色id
	 * @return List UserRoleRelation用户与角色关联集合
	 * @throws PaasWebException
	 */
	public List<UserRoleRelation> queryByRole(Long roleId) throws PaasWebException;

	/**
	 * 删除该角色与用户的关联关系.
	 *
	 * @param roleId 角色id
	 * @throws PaasWebException
	 */
	public void deleteByRole(Long roleId) throws PaasWebException;
}
