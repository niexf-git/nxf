package com.cmsz.paas.web.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.user.dao.UserRoleRelationDao;
import com.cmsz.paas.web.user.entity.UserRoleRelation;
import com.cmsz.paas.web.user.service.UserRoleRelationService;

/**
 * 用户角色管理serviceImpl
 * @author zhouyunxia
 *
 */
@Service("UserRoleRelationService")
public class UserRoleRelationServiceImpl implements UserRoleRelationService {

	@Autowired
	private UserRoleRelationDao userRoleRelationDao;

	@Override
	public List<UserRoleRelation> queryByUser(Long userId) throws PaasWebException{
		Log.info("根据用户查询"+userId);
//		Map<String,String> map = new HashMap<String,String>();
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		try{
			return userRoleRelationDao.findBy(map);
		}catch(Exception ex){
			Log.error("根据用户查询异常",ex);
			throw new PaasWebException(Constants.QUERY_USER_ROLE_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public void deleteByUser(Long userId) throws PaasWebException{
		Log.info("根据用户删除"+userId);
		UserRoleRelation relation = new UserRoleRelation();
		relation.setUserId(userId);
		try{
			userRoleRelationDao.delete(relation);
		}catch(Exception ex){
			Log.error("根据用户删除异常",ex);
			throw new PaasWebException(Constants.DELETE_USER_ROLE,
					ex.getMessage());
		}
	}

	@Override
	public void updateUserRole(List<UserRoleRelation> deleteRole,
			List<UserRoleRelation> addRole) throws PaasWebException{
		try{
			Log.info("修改用户角色");
			for(UserRoleRelation userRole:deleteRole){
				userRoleRelationDao.delete(userRole);
			}
			userRoleRelationDao.insert(addRole);
		}catch(Exception ex){
			Log.error("修改用户角色异常",ex);
			throw new PaasWebException(Constants.UPDATE_USER_ROLE,
					ex.getMessage());
		}
	}

	@Override
	public List<UserRoleRelation> queryByRole(Long roleId) throws PaasWebException{
		try{
			Log.info("根据角色查询"+roleId);
//			Map<String,String> map = new HashMap<String,String>();
			Map<String,Long> map = new HashMap<String,Long>();
			map.put("roleId", roleId);
			return userRoleRelationDao.findBy(map);
		}catch(Exception ex){
			Log.error("根据角色查询异常",ex);
			throw new PaasWebException(Constants.QUERY_USER_ROLE_BY_ROLE_ID,
					ex.getMessage());
		}
	}

	@Override
	public void deleteByRole(Long roleId) throws PaasWebException{
		try{
			Log.info("根据角色删除"+roleId);
			UserRoleRelation relation = new UserRoleRelation();
			relation.setRoleId(roleId);
			userRoleRelationDao.delete(relation);
		}catch(Exception ex){
			Log.error("根据角色删除异常",ex);
			throw new PaasWebException(Constants.DELETE_USER_ROLE_BY_ROLE_ID,
					ex.getMessage());
		}
	}
}
