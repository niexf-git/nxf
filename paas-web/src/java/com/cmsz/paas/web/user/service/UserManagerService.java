package com.cmsz.paas.web.user.service;

import java.util.List;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.user.entity.User;


/**
 * 用户管理service.
 *
 * @author zhouyunxia
 */
public interface UserManagerService {
	
    /**
     * 通过用户账号查询用户信息.
     *
     * @param loginName 用户账号
     * @return User 用户信息对象
     * @throws PaasWebException
     */
    public User queryUserByName(String loginName) throws PaasWebException;
	
	/**
	 * 查询所有用户
	 *
	 * @return List User 用户列表
	 * @throws PaasWebException
	 */
	public List<User> getUserList() throws PaasWebException;

	/**
	 * 查询用户列表.
	 *
	 * @param pc 查询参数
	 * @return Page 用户结果
	 * @throws PaasWebException
	 */
	public Page<User> findPage(PageContext pc) throws PaasWebException;

	/**
	 * 增加用户.
	 *
	 * @param user 用户信息
	 * @throws PaasWebException
	 */
	public void addUser(User user) throws PaasWebException;

	/**
	 * 删除用户.
	 *
	 * @param id 用户id
	 * @throws PaasWebException
	 */
	public void deleteUser(Long id) throws PaasWebException;

	/**
	 * 通过用户id查询用户信息.
	 *
	 * @param id 用户id
	 * @return User 用户信息
	 * @throws PaasWebException
	 */
	public User queryUserById(Long id) throws PaasWebException;

	/**
	 * 修改用户信息.
	 *
	 * @param user 用户信息
	 * @throws PaasWebException
	 */
	public void updateUser(User user) throws PaasWebException;

	/**
	 * 查询用户拥有的权限.
	 *
	 * @param userId 用户id
	 * @return List RolePermissionRelation 用户权限集合
	 * @throws PaasWebException
	 */
	public List<RolePermissionRelation> queryUserPermission(Long userId) throws PaasWebException;

	/**
	 * 判断用户是否是超级管理员.
	 *
	 * @param userId 用户id
	 * @return true 管理员 false 不是管理员
	 * @throws PaasWebException
	 */
	public boolean isSuperManager(Long userId) throws PaasWebException;

	/**
	 * 查询所有用户.
	 *
	 * @return List User 用户信息集合
	 * @throws PaasWebException
	 */
	public List<User> queryUserAll() throws PaasWebException;

	/**
	 * 查询所有用户.
	 *
	 * @param id 登录用户id
	 * @return List User 用户信息集合
	 * @throws PaasWebException
	 */
	public List<User> queryUserByCreator(Long id) throws PaasWebException;

	/***
	 * 查询应用下的操作类型
	 * @param user
	 * @param appId
	 * @return
	 * @throws PaasWebException
	 */
	public String queryOperTypeById(User user,String appId) throws PaasWebException;
	
	/***
	 * 修改当前用户sessionID
	 * @param user
	 * @return
	 * @throws PaasWebException
	 */
	public void updateSessionId(User user) throws PaasWebException;
}
