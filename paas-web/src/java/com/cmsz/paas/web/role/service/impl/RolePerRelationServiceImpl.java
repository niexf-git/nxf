package com.cmsz.paas.web.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.application.model.ApplicationInfoUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.role.dao.RolePerRelationDao;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 角色权限管理serviceImpl
 *
 */
@Service("RolePerRelationService")
public class RolePerRelationServiceImpl implements RolePerRelationService {

	@Autowired
	private RolePerRelationDao rolePerDao;
	
	@Override
	public List<RolePermissionRelation> queryByRoleId(Long roleId) throws PaasWebException {
		Log.info("根据id查询角色"+roleId);
//		Map<String,String> map = new HashMap<String,String>();
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("roleId", roleId);
		try{
			return rolePerDao.findBy(map);
		}catch(Exception ex){
			Log.error("根据id查询角色异常",ex);
			throw new PaasWebException(Constants.QUERY_ROLE_PERMISSION_ERROR,
					ex.getMessage());
		}
	}
	
	@Override
	public void deleteByAppId(String appId) throws PaasWebException {
		Log.info("根据应用id("+appId+")删除角色关联这个应用id的记录");
		
		try{
//			int ret = rolePerDao.deleteById(appId);
			int ret = rolePerDao.deleteById(Long.valueOf(appId));
		
		}catch(Exception ex){
			Log.error("删除所有角色与该应用id关联的权限异常",ex);
			throw new PaasWebException(Constants.DELETE_ROLE_PERMISSION_ERROR,
					ex.getMessage());
		}
	}
	
	@Override
	public void InsertRolePermission(RolePermissionRelation rolePer) throws PaasWebException {
		Log.info("根据应用id或者应用组id新增角色关联这个应用id或应用组的记录");		
		
		try{			
			int ret = rolePerDao.insert(rolePer);		
		
		}catch(Exception ex){
			Log.error("根据应用id或者应用组id新增角色关联这个应用id或应用组的记录异常",ex);
			throw new PaasWebException(Constants.DELETE_ROLE_PERMISSION_ERROR,
					ex.getMessage());
		}
	}
	
	@Override
	public List<RolePermissionRelation> queryRolePermissionRelation() throws PaasWebException{
		try{
			return rolePerDao.findAll();
		}catch(Exception ex){
			throw new PaasWebException(Constants.QUERY_ROLE_PERMISSION_FIND_ALL_ERROR,
					ex.getMessage());
		}
	}

	//数据结构转换
	private List<RolePermissionRelation> mapToAppList(long appId,ApplicationInfoUtil infoUtil) throws Exception{
		Map<String, List<String>> map = infoUtil.getTableData().get(0);
		List<RolePermissionRelation> permissionRelations = new ArrayList<RolePermissionRelation>();
		if(map == null){return permissionRelations;}
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<String> tempList = map.get(key);
			for (String roleId : tempList) {
				RolePermissionRelation permissionRelation = new RolePermissionRelation();
				permissionRelation.setPermissionId(String.valueOf(appId));
				permissionRelation.setRoleId(Long.valueOf(roleId));
				permissionRelation.setOpertype("Development".equals(key)?1:"Test".equals(key)?2:3);
				permissionRelation.setPermissionName(infoUtil.getAppName());
				permissionRelation.setPermissionType(2);
				permissionRelations.add(permissionRelation);
			}
			
		}
		return permissionRelations;
	}
	@Override
	public void createRoleToAppRelation(long appId,ApplicationInfoUtil infoUtil) throws PaasWebException {
			//.getTableData().get(0)
			Log.info("创建应用与角色关系开始");
			if(infoUtil != null){
				Log.info("结构数据转换中...");
				List<RolePermissionRelation> list = null;
				try {
					list = mapToAppList(appId,infoUtil);
					addDefaultAdminRole(list,appId,infoUtil.getAppName());					
				} catch (Exception e) {
					Log.error("应用管理数据转换异常:"+e.getMessage());
				}
				rolePerDao.insert(list);
				//roleToAppRelationDao.insert(list);
				Log.info("应用角色关系创建完成");
			}
		
	}
	
	public Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}
	
	private void addDefaultAdminRole(List<RolePermissionRelation> relations,long appId,String appName){
		boolean currentContext = (boolean) getSessionMap().get("currentContext");
		RolePermissionRelation relation = null;
		for (int i = 1; i <= (currentContext?2:1); i++) {
			//默认构造一条管理员的关系
			relation = new RolePermissionRelation();
			relation.setRoleId(1L);
			relation.setPermissionId(String.valueOf(appId));
			relation.setPermissionName(appName);
			relation.setPermissionType(2);
			relation.setOpertype((currentContext?i:3));
			relations.add(relation);
		}
	}
	
	public void deleteAppRolePerRelation(long id){
		try {
			Log.info("删除应用 appId:"+id+"角色关联关系开始");
			rolePerDao.deleteById(id);
			Log.info("删除应用角色关联关系成功");
		} catch (Exception e) {
			Log.error("删除应用关联关系异常："+e.getMessage());
		}
	}
	
	public void updateAppRolePerRelation(ApplicationInfoUtil infoUtil){
		Log.info("修改应用与角色关系开始");
		if(infoUtil != null){
			deleteAppRolePerRelation(infoUtil.getAppId());
			Log.info("结构数据转换中...");
			List<RolePermissionRelation> list = null;
			try {
				list = mapToAppList(infoUtil.getAppId(),infoUtil);
				addDefaultAdminRole(list,infoUtil.getAppId(),infoUtil.getAppName());
			} catch (Exception e) {
				Log.error("应用管理数据转换异常:"+e.getMessage());
			}
			rolePerDao.insert(list);
			//roleToAppRelationDao.insert(list);
			Log.info("应用角色关系创建完成");
		}
	}

	@Override
	public List<RolePermissionRelation> queryByUserId(long id) {
		Log.info("根据id查询应用"+id);
//		Map<String,String> map = new HashMap<String,String>();
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", id);
		try{
			return rolePerDao.findBy(map);
		}catch(Exception ex){
			Log.error("根据id查询角色异常",ex);
			throw new PaasWebException(Constants.QUERY_ROLE_PERMISSION_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public String queryUserRoleType(long appId, long userId,String type) {
		String roleType = "";
		Log.info("根据当前用户选择的应用配对操作类型,用户Id:"+userId);
//		Map<String,String> map = new HashMap<String,String>();
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("permissionId", appId);
		map.put("permissionType", 2l);
		try{
			List<RolePermissionRelation> list = rolePerDao.findBy(map);
			if(userId == 1 && !"".equals(type)){
				for (RolePermissionRelation rolePermissionRelation : list) {
					if(appId==Long.valueOf(rolePermissionRelation.getPermissionId()) 
							&& rolePermissionRelation.getOpertype()==Integer.valueOf(type)){
						roleType = String.valueOf(rolePermissionRelation.getOpertype())+"%"+rolePermissionRelation.getPermissionName();
					}
				}
			}else{
				if(list.size()>0){
					roleType = String.valueOf(list.get(0).getOpertype())+"%"+list.get(0).getPermissionName();
				}
				
			}
		}catch(Exception ex){
			Log.error("根据id查询角色异常",ex);
			throw new PaasWebException(Constants.QUERY_ROLE_PERMISSION_ERROR,
					ex.getMessage());
		}
		return roleType;
	}
	
	/**
	 * 查询用户所属的操作类型
	 */
	@Override
	public String queryOperTypeByUserId(long userId) {
		String roleType = "";
		Log.info("根据当前用户选择的应用配对操作类型,用户Id:"+userId);
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("permissionType", 2L);
		try{
			List<RolePermissionRelation> list = rolePerDao.findBy(map);
			if(list.size() > 0){
				roleType = String.valueOf(list.get(0).getOpertype());
			}
		}catch(Exception ex){
			Log.error("根据id查询角色异常",ex);
			throw new PaasWebException(Constants.QUERY_ROLE_PERMISSION_ERROR,
					ex.getMessage());
		}
		return roleType;
	}
	
	/**
	 * 查询角色所属的操作类型
	 */
	@Override
	public String queryOperTypeByRoleId(long roleId) {
		String roleType = "";
		Log.info("根据当前用户选择的应用配对操作类型,角色Id:"+roleId);
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("roleId", roleId);
		map.put("permissionType", 2L);
		try{
			List<RolePermissionRelation> list = rolePerDao.findBy(map);
			if(list.size() >0){
				roleType = String.valueOf(list.get(0).getOpertype());
			}
		}catch(Exception ex){
			Log.error("根据id查询角色异常",ex);
			throw new PaasWebException(Constants.QUERY_ROLE_PERMISSION_ERROR,
					ex.getMessage());
		}
		return roleType;
	}
	
}
