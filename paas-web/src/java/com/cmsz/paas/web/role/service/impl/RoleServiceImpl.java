package com.cmsz.paas.web.role.service.impl;

import java.util.ArrayList;
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
import com.cmsz.paas.web.role.dao.RoleDao;
import com.cmsz.paas.web.role.dao.RolePerRelationDao;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.entity.TreeNode;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.cmsz.paas.web.role.service.RoleService;

/**
 * 角色管理类ServiceImpl
 * @author zhouyunxia
 *  
 */
@Service("RoleService")
public class RoleServiceImpl  implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RolePerRelationDao rolePerDao;
	
	/** 角色与权限管理service对象. */
	@Autowired
	private RolePerRelationService rolePerService;

	@Override
	public Page<Role> findPage(PageContext pc) throws PaasWebException{
		try{
			Log.info("查询分页"+pc);
			Page<Role> page = roleDao.findPage(pc);
			return page;
		}catch(Exception ex){
			Log.error("查询分页异常",ex);
			throw new PaasWebException(Constants.FIND_PAGE_ROLE_ERROR,
					ex.getMessage());
		}
		
	}

	@Override
	public List<Role> queryAll() throws PaasWebException{
		try{
			Log.info("查询所有");
			return roleDao.findAll();
		}catch(Exception ex){
			Log.error("查询所有异常",ex);
			throw new PaasWebException(Constants.FIND_ALL_ROLE_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public List<TreeNode> queryRoleTreeNode() throws PaasWebException{
		try{
			Log.info("查询角色树节点");
			List<Role> roleList = roleDao.findAll();
			return changeToTreeNode(roleList);
		}catch(Exception ex){
			Log.error("查询角色树节点异常",ex);
			throw new PaasWebException(Constants.FIND_ALL_ROLE_ERROR,
					ex.getMessage());
		}
	}
	
	@Override
	public List<TreeNode> queryRoleTreeNode(Long userId) throws PaasWebException{
		try{
			Log.info("查询角色树节点userId:"+userId);
			Map<String,Long> map = new HashMap<String,Long>();
			map.put("createBy", userId);
			List<Role> roleList = roleDao.findBy(map);
			return changeToTreeNode(roleList);
		}catch(Exception ex){
			Log.error("查询角色树节点异常",ex);
			throw new PaasWebException(Constants.FIND_ALL_ROLE_ERROR,
					ex.getMessage());
		}
	}
	
	/**
	 * 将Role对象转换为TreeNode
	 * @param roleList 角色集合
	 * @return
	 */
	private List<TreeNode> changeToTreeNode(List<Role> roleList){
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		TreeNode treeNode = null;
		for(Role role:roleList){
			//过滤超级管理员角色
			if(role.getId() != 1){
				treeNode = new TreeNode();
				treeNode.setId(String.valueOf(role.getId()));
				
				// 角色后面加上（开发、测试、运维）操作类型
				String roleOperType = rolePerService.queryOperTypeByRoleId(role.getId());
				if(roleOperType != null && !"".equals(roleOperType)){
//					treeNode.setName(role.getRoleName() + "(" + ("1".equals(roleOperType)?"开发":"测试") + ")");
					if("1".equals(roleOperType)){
						treeNode.setName(role.getRoleName() + "(开发)");
					} else if ("2".equals(roleOperType)){
						treeNode.setName(role.getRoleName() + "(测试)");
					} else if ("3".equals(roleOperType)){
						treeNode.setName(role.getRoleName() + "(运维)");
					}
				} else {
					treeNode.setName(role.getRoleName());
				}
				
				treeNodeList.add(treeNode);
			}
		}
		return treeNodeList;
	}

	@Override
	public void deleteRole(Long roleId) throws PaasWebException{
		try{
			roleDao.deleteById(roleId);
			RolePermissionRelation relation = new RolePermissionRelation();
			relation.setRoleId(roleId);
			rolePerDao.delete(relation);
		}catch(Exception ex){
			Log.error("删除角色异常",ex);
			throw new PaasWebException(Constants.DELETE_ROLE_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public void createRole(Role role, String operSelectNode,String dataSelectNode) throws PaasWebException{
		try{
			roleDao.insert(role);
			Long roleId = role.getId();
			
			List<RolePermissionRelation> rolePerList = new ArrayList<RolePermissionRelation>();
			//操作权限
			List<RolePermissionRelation> operList = getPerRelation(operSelectNode,Constants.ROLE_OPER_PERMISSION_TYPE,roleId);
			if(operList != null){
				rolePerList.addAll(operList);
			}
			//迭代一：数据权限
//			List<RolePermissionRelation> dataList = getPerRelation(dataSelectNode,Constants.ROLE_DATA_PERMISSION_TYPE,roleId);
//			if(dataList != null){
//				rolePerList.addAll(dataList);
//			}
			
			//迭代二：应用组和应用
//			if(role.getType() == 2){ //普通用户
//				String[] nodes = dataSelectNode.split(",");
//				String appSelectNode = "";
//				for(int i=0; i<nodes.length; i++){
//					if(nodes[i].indexOf("a") != -1){ //匹配到包含“a”的字符串
//						if(appSelectNode.equals("")){
//							appSelectNode += nodes[i].substring(0, nodes[i].length()-1); //截取“a”前面部分组成appIds
//						}else{
//							appSelectNode += "," + nodes[i].substring(0, nodes[i].length()-1);
//						}
//					}
//				}
//				List<RolePermissionRelation> appList = getPerRelation(appSelectNode,Constants.ROLE_APP_PERMISSION_TYPE,roleId);
//				if(appList != null){
//					rolePerList.addAll(appList);
//				}
//			} else if(role.getType() == 1){ //普通管理员
//				String[] nodes = dataSelectNode.split(",");
//				String appGroupSelectNode = "";
//				for(int i=0; i<nodes.length; i++){
//					if(nodes[i].indexOf("g") != -1){ //匹配到包含“g”的字符串
//						if(appGroupSelectNode.equals("")){
//							appGroupSelectNode += nodes[i].substring(0, nodes[i].length()-1); //截取“g”前面部分组成appGroupIds
//						}else{
//							appGroupSelectNode += "," + nodes[i].substring(0, nodes[i].length()-1);
//						}
//					}
//				}
//				List<RolePermissionRelation> appGroupList = getPerRelation(appGroupSelectNode,Constants.ROLE_APPGROUP_PERMISSION_TYPE,roleId);
//				if(appGroupList != null){
//					rolePerList.addAll(appGroupList);
//				}
//			}
			
			for(RolePermissionRelation rolePer:rolePerList){
				rolePerDao.insert(rolePer);
			}
		}catch(Exception ex){
			Log.error("创建角色异常",ex);
			throw new PaasWebException(Constants.CREATE_ROLE_ERROR,
					ex.getMessage());
		}
	}
	/**
	 * 将string类型的权限转换为角色与权限的关联对象集合
	 * @param selectNode 权限
	 * @param type 迭代一权限类型：1操作权限、2数据权限；迭代二权限类型：1操作权限、2应用组、3应用
	 * @param roleId角色id
	 * @return
	 */
	private List<RolePermissionRelation> getPerRelation(String selectNode,int type,Long roleId){
		if(selectNode == null || selectNode.equals("")){
			return null;
		}
		List<RolePermissionRelation> rolePerList = new ArrayList<RolePermissionRelation>();
		RolePermissionRelation rolePer = null;
		String[] nodes = selectNode.split(",");
		for(String node:nodes){
			rolePer = new RolePermissionRelation();
			rolePer.setPermissionId(node);
			rolePer.setPermissionType(type);
			rolePer.setRoleId(roleId);
			rolePerList.add(rolePer);
		}
		return rolePerList;
	}

	@Override
	public Role queryRole(Long roleId)  throws PaasWebException{
		try{
			return roleDao.findById(roleId);
		}catch(Exception ex){
			throw new PaasWebException(Constants.QUERY_BY_ROLE_ID_ERROR,
					ex.getMessage());
		}
	}
	
	@Override
	public Role queryRoleByName(String roleName)  throws PaasWebException{
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("roleName", roleName);
			List<Role> role = roleDao.findBy(map);
			if(role.size()>0){
				return role.get(0);
			}
			return null;
		}catch(Exception ex){
			throw new PaasWebException(Constants.QUERY_ROLE_BY_NAME_ERROR,
					ex.getMessage());
		}
	}

	@Override
	public void updateRole(Role role,
			List<RolePermissionRelation> deletePermissionList,
			List<RolePermissionRelation> addPermissionList)  throws PaasWebException{
		try{
			roleDao.update(role);
			updateRolePermission(deletePermissionList,addPermissionList);
		}catch(Exception ex){
			Log.error("修改角色异常",ex);
			throw new PaasWebException(Constants.UPDATE_ROLE_ERROR,
					ex.getMessage());
		}
	}

	/**
	 * 更新角色的权限信息
	 * @param deletePermissionList 角色删除的权限
	 * @param addPermissionList 角色新增的权限
	 */
	private void updateRolePermission(
			List<RolePermissionRelation> deletePermissionList,
			List<RolePermissionRelation> addPermissionList) {
		for(RolePermissionRelation rolePer:deletePermissionList){
			rolePerDao.delete(rolePer);
		}
		rolePerDao.insert(addPermissionList);
	}
 }
