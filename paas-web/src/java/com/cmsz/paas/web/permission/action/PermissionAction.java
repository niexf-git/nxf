package com.cmsz.paas.web.permission.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.permission.entity.PermissionTree;
import com.cmsz.paas.web.permission.service.PermissionService;
import com.cmsz.paas.web.role.entity.TreeNode;
import com.cmsz.paas.web.user.service.UserManagerService;

/**
 * 权限管理action.
 * 
 * @author zhouyunxia
 */
@UnLogging
public class PermissionAction extends AbstractAction {

	/** 序列号. */
	private static final long serialVersionUID = 7766823116645717421L;

	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory
			.getLogger(PermissionAction.class);

	/** 权限service对象. */
	@Autowired
	private PermissionService permissionService;

	/** 用户管理service对象. */
	@Autowired
	private UserManagerService userManagerService;

	
	/**
	 * 查询权限树形结构.
	 */
	@UnLogging
	public void queryPerTree() {
		try {
			TreeNode treeNode = null;
			
			@SuppressWarnings("unchecked")
			List<OperPermission> operPer = (List<OperPermission>) getSessionMap()
					.get("operPermission");
			List<TreeNode> treeOperNodeList = new ArrayList<TreeNode>();
			for (OperPermission info : operPer) {
				treeNode = new TreeNode();
				treeNode.setId(info.getId());
				treeNode.setName(info.getName());
				treeNode.setTitle(info.getName());
				if (info.getName().equals("服务管理")) {
					treeNode.setChkDisabled(true);
					treeNode.setChecked(true);
				}
				//系统管理、系统监控 不能授予给普通用户
				if ("系统管理".equals(info.getName())
						|| "用户管理".equals(info.getName())
						|| "角色管理".equals(info.getName())) {
					treeNode.setChkDisabled(true);
				}
				treeOperNodeList.add(treeNode);
			}
			PermissionTree tree = new PermissionTree();
			tree.setOperPerTreeList(treeOperNodeList);
			
			List<TreeNode> treeDataNodeList = Collections.emptyList();
			tree.setDataPerTreeList(treeDataNodeList);
			
			this.renderText(JackJson.fromObjectToJson(tree));
		} catch (PaasWebException e) {
			logger.error("查询权限树" + e.getMessage(), e);
			this.sendFailResult(e.getErrorCode(), e.toString());
		} catch (Exception e) {
			logger.error("查询权限树" + e.getMessage(), e);
			this.sendFailReslutl(e.toString());
		}
	}
	
	/**
	 * 查询菜单栏权限
	 */
	@UnLogging
	public void queryMenuPermission() {
		try {
			@SuppressWarnings("unchecked")
			List<OperPermission> menuPermission = (List<OperPermission>) getSessionMap().get("operPermission");
			this.sendSuccessReslult(menuPermission);
		} catch (Exception e) {
			logger.error("查询菜单栏权限异常" + e.getMessage(), e);
			this.sendFailResult(Constants.QUERY_PERMISSION_FIND_ALL_ERROR, e.getMessage());
		}
	}
	/**
	 * 查询当前登陆的用户角色
	 */
	@UnLogging
	public void queryUserRoleAndOperType() {
		try {
			String operType = getSessionMap().get("selectedOpertype")+"";
			String role = getSessionMap().get("roleType")+"";
			String loginName = getSessionMap().get("loginName")+"";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("role", role);
			map.put("operType", operType);
			map.put("loginName", loginName);
			this.sendSuccessReslult(map);
		} catch (Exception e) {
			logger.error("查询当前登陆用户角色异常" + e.getMessage(), e);
			this.sendFailResult(Constants.QUERY_USERROLE_AND_OPERTYPE_ERROR, e.getMessage());
		}
	}

}
