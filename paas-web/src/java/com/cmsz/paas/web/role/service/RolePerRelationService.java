package com.cmsz.paas.web.role.service;

import java.util.List;

import com.cmsz.paas.web.application.model.ApplicationInfoUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;



/**
 * 角色权限管理service.
 *
 * @author zhouyunxia
 */
public interface RolePerRelationService {

	/**
	 * 通过角色id查询角色与权限的关联.
	 *
	 * @param roleId 角色id
	 * @return List RolePermissionRelation 角色与权限的关联信息集合
	 * @throws PaasWebException
	 */
	public List<RolePermissionRelation> queryByRoleId(Long roleId) throws PaasWebException;



	/**
	 * 删除角色关联表中与该应用id对应的记录.
	 *
	 * @param appId 应用id
	 * @throws PaasWebException 
	 */
	public void deleteByAppId(String appId) throws PaasWebException;


	/**
	 * 根据应用id或者应用组id新增角色关联这个应用id或应用组的记录.
	 *
	 * @param rolePer 角色权限关联对象
	 * @throws PaasWebException 
	 */		
	void InsertRolePermission(RolePermissionRelation rolePer) throws PaasWebException;
	
	/**
	 * 查询角色权限关系集合
	 * 
	 * @throws PaasWebException
	 */
	public List<RolePermissionRelation> queryRolePermissionRelation() throws PaasWebException;
	
	
	/***
	 * 新增角色与应用的关联关系
	 * @param appId
	 * @param map
	 * @throws PaasWebException
	 */
	public void createRoleToAppRelation(long appId,ApplicationInfoUtil infoUtil) throws PaasWebException;
	
	/***
	 * 修改应用时同时修改其关联关系
	 * @param infoUtil
	 */
	public void updateAppRolePerRelation(ApplicationInfoUtil infoUtil);
	
	/***
	 * 删除应用的关联关系
	 * @param id
	 */
	public void deleteAppRolePerRelation(long id);
	
	/***
	 * 查询用户与应用的关系
	 * @param id
	 */
	public List<RolePermissionRelation> queryByUserId(long id);
	
	/***
	 * 根据当前用户和应用id查询对应的操作类型
	 * @param appId
	 * @param roleId
	 * @return
	 */
	public String queryUserRoleType(long appId,long userId,String type);

	/**
	 * 查询用户所属的操作类型
	 * @param userId
	 * @return
	 */
	public String queryOperTypeByUserId(long userId);

	/**
	 * 查询角色所属的操作类型
	 * @param roleId
	 * @return
	 */
	public String queryOperTypeByRoleId(long roleId);
	
}
