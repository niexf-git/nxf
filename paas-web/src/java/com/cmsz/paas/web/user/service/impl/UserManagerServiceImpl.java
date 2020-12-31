package com.cmsz.paas.web.user.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.role.dao.RolePerRelationDao;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.cmsz.paas.web.user.dao.UserDao;
import com.cmsz.paas.web.user.dao.UserRoleRelationDao;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.entity.UserRoleRelation;
import com.cmsz.paas.web.user.service.UserManagerService;

/**
 * 用户管理serviceImpl
 * @author zhouyunxia
 *
 */
@Service("UserManagerService")
public class UserManagerServiceImpl implements UserManagerService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleRelationDao userRoleRelationDao;
	@Autowired
	private RolePerRelationDao rolePerRelationDao;
	@Autowired
	private RolePerRelationService rolePerRelationService; //角色权限关系
	@Override
	public Page<User> findPage(PageContext pc) throws PaasWebException{
		try{
			Log.info("查询分页页面"+pc);
			Page<User> page = userDao.findPage(pc);
			return page;
		}catch(Exception ex){
			Log.error("查询分页异常",ex);
			throw new PaasWebException(Constants.FIND_PAGE_USER_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public User queryUserByName(String loginName) throws PaasWebException{
		try{
			Log.info("根据用户名查询"+loginName);
			Map<String,String> map = new HashMap<String,String>();
			map.put("loginName", loginName);
			List<User> user = userDao.findBy(map);
			
			for(User u : user){
				if(u.getLoginName().toUpperCase().equals(loginName.toUpperCase())){
					return u;
				}
			}
			
//			if(user.size()>0){
//				return user.get(0);
//			}
			return null;
		}catch(Exception ex){
			Log.error("根据用户名查询异常",ex);
			throw new PaasWebException(Constants.FIND_USER_BY_NAME_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public List<User> getUserList() throws PaasWebException{
		try{
			Log.info("查询用户");
			return userDao.findAll();
		}catch(Exception ex){
			Log.error("查询用户异常",ex);
			throw new PaasWebException(Constants.FIND_USER_ALL_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public void addUser(User user) throws PaasWebException{
		try{
			Log.info("新增用户");
			userDao.insert(user);
		}catch(Exception ex){
			Log.error("新增用户异常",ex);
			throw new PaasWebException(Constants.CREATE_USER_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public void deleteUser(Long id) throws PaasWebException{
		try{
			Log.info("删除用户"+id);
			userDao.deleteById(id);
			UserRoleRelation relation = new UserRoleRelation();
			relation.setUserId(id);
			userRoleRelationDao.delete(relation);
		}catch(Exception ex){
			Log.error("删除用户异常",ex);
			throw new PaasWebException(Constants.DELETE_USER_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public User queryUserById(Long id) throws PaasWebException{
		try{
			Log.info("根据Id查询用户"+id);
			User user = userDao.findById(id);
			return user;
		}catch(Exception ex){
			Log.error("根据Id查询用户异常",ex);
			throw new PaasWebException(Constants.FIND_USER_BY_ID_ERROR,
					ex.getMessage());
		}
	}
	
	@Override
	public void updateUser(User user) throws PaasWebException{
		try{
			Log.info("修改用户"+user);
			userDao.update(user);
		}catch(Exception ex){
			Log.error("修改异常",ex);
			throw new PaasWebException(Constants.UPDATE_USER_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public List<RolePermissionRelation> queryUserPermission(Long userId) throws PaasWebException{
		try{
			Log.info("查询用户权限"+userId);
//			Map<String,String> map = new HashMap<String,String>();
			Map<String,Long> map = new HashMap<String,Long>();
			map.put("userId", userId);
			List<RolePermissionRelation> permission = rolePerRelationDao.findUserPermission(map);
			return permission;
		}catch(Exception ex){
			Log.error("查询用户权限异常",ex);
			throw new PaasWebException(Constants.QUERY_USER_PERMISSION_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public boolean isSuperManager(Long userId) throws PaasWebException{
		try{
			Log.info("查询是否是超级管理员账户："+userId);
//			Map<String,String> map = new HashMap<String,String>();
			Map<String,Long> map = new HashMap<String,Long>();
			map.put("userId", userId);
			List<UserRoleRelation> userRoleList = userRoleRelationDao.findBy(map);
			if(userRoleList!=null){
				for(UserRoleRelation userRole:userRoleList){
					if(userRole.getRoleType()==Constants.ROLE_TYPE_SUPER_MANAGER){
						return true;
					}
				}
			}
			return false;
		}catch(Exception ex){
			Log.error("查询是否是超级管理员账户异常");
			throw new PaasWebException(Constants.QUERY_USER_ROLE_ADMIN_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public List<User> queryUserAll() throws PaasWebException{
		try{
			Log.info("查询所有用户");
			return userDao.findAll();
		}catch(Exception ex){
			Log.error("查询所有用户异常");
			throw new PaasWebException(Constants.FIND_USER_ALL_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public List<User> queryUserByCreator(Long id) throws PaasWebException{
		try{
			Log.info("查询用户createBy:"+id);
//			Map<String,String> map = new HashMap<String,String>();
			Map<String,Long> map = new HashMap<String,Long>();
			map.put("createBy", id);
			return userDao.findBy(map);
		}catch(Exception ex){
			Log.error("查询用户异常");
			throw new PaasWebException(Constants.FIND_USER_ALL_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public String queryOperTypeById(User user,String appId) throws PaasWebException {
		String typeIds = "";
		//角色应用关联关系
		List<RolePermissionRelation> dataPermission = null;
		if (isSuperManager(user.getId())) { //超级管理员，具有全部的数据权限和操作权限
			try {
				dataPermission = rolePerRelationService.queryRolePermissionRelation();
			} catch (PaasWebException ex) {
				throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
			}
		} else { //普通用户
			dataPermission = queryUserPermission(user.getId());
		}
		
		for (int i = 0;i<dataPermission.size();i++) {
			//类型2的为应用信息
			if(dataPermission.get(i).getPermissionType() == 2){
				//获取集合中应用一致的 类型名称
				if(dataPermission.get(i).getPermissionId().equals(String.valueOf(appId))){
					//去重复
					if(!typeIds.contains(String.valueOf(dataPermission.get(i).getOpertype()))){
						typeIds += dataPermission.get(i).getOpertype()+",";
					}
				}
			}
		}
		String[] tempTypeIds = null;
		if(typeIds.contains(",")){
			typeIds = typeIds.substring(0, typeIds.lastIndexOf(","));
			tempTypeIds = typeIds.split(",");
		}
		if(tempTypeIds!=null && typeIds.contains(",")){
			typeIds = "";
			int[] sortTypeIds = new int[tempTypeIds.length];
			for (int i = 0; i < sortTypeIds.length; i++) {
				sortTypeIds[i] =Integer.valueOf(tempTypeIds[i]);
			}
			Arrays.sort(sortTypeIds);
			for (int i = 0; i < sortTypeIds.length; i++) {
				typeIds +=sortTypeIds[i];
				if(i != sortTypeIds.length-1){
					typeIds+=",";
				}
			}
		}
	
		return typeIds;
	}

	@Override
	public void updateSessionId(User user) throws PaasWebException {
		userDao.updateSessionId(user);
	}
}
