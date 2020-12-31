package com.cmsz.paas.web.permission.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.permission.dao.PermissionDao;
import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.permission.service.PermissionService;
import com.cmsz.paas.web.role.entity.TreeNode;

/**
 * 权限Service
 * @author zhouyunxia
 *
 */
@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private PermissionDao permissionDao;
	@Override
	public List<TreeNode> queryOperPerTree() throws PaasWebException{
		try{
			List<OperPermission> roleList = permissionDao.findAll();
			List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
			TreeNode treeNode = null;
			for(OperPermission operPermission:roleList){
				treeNode = new TreeNode();
				treeNode.setId(operPermission.getId());
				treeNode.setpId(operPermission.getpId());
				treeNode.setName(operPermission.getName());
				if(operPermission.getName().equals("我的应用")){
					treeNode.setChkDisabled(true);
					treeNode.setChecked(true);
				}
				treeNodeList.add(treeNode);
			}
			return treeNodeList;
		}catch(Exception ex){
			throw new PaasWebException(Constants.QUERY_PERMISSION_FIND_ALL_ERROR,
					ex.getMessage());
		}
	}
	@Override
	public List<OperPermission> queryOperPermission() throws PaasWebException{
		try{
			return permissionDao.findAll();
		}catch(Exception ex){
			throw new PaasWebException(Constants.QUERY_PERMISSION_FIND_ALL_ERROR,
					ex.getMessage());
		}
	}

}
