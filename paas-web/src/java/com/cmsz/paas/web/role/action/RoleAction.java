package com.cmsz.paas.web.role.action;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.permission.entity.PermissionTree;
import com.cmsz.paas.web.permission.service.PermissionService;
import com.cmsz.paas.web.role.entity.Multiselect;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.entity.TreeNode;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.cmsz.paas.web.role.service.RoleService;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.entity.UserRoleRelation;
import com.cmsz.paas.web.user.service.UserManagerService;
import com.cmsz.paas.web.user.service.UserRoleRelationService;

/**
 * 角色管理Action.
 * 
 * @author zhouyunxia
 */
public class RoleAction extends AbstractAction {

	/** 序列号. */
	private static final long serialVersionUID = 5688740944484875850L;

	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory
			.getLogger(RoleAction.class);

	/** 角色service对象. */
	@Autowired
	private RoleService roleService;

	/** 用户与角色关联service对象. */
	@Autowired
	private UserRoleRelationService userRoleService;

	/** 角色与权限管理service对象. */
	@Autowired
	private RolePerRelationService rolePerService;

	/** 操作权限service对象. */
	@Autowired
	private PermissionService permissionService;

	/** 用户管理service对象. */
	@Autowired
	private UserManagerService userManagerService;

	/** 角色ID. */
	private Long roleId;

	/** 角色name. */
	private String roleName;

	/** 角色描述. */
	private String description;

	/** 角色类型. */
	private int roleType;

	/** 角色信息. */
	private Role role;

	/** 操作权限树形结构选择的权限. */
	private String operSelectNode;

	/** 数据权限树形结构选择的权限. */
	private String dataSelectNode;

	/** 用户选择列表. */
	private List<Multiselect> userSelectList;
	
	/** 应用权限列表. */
	private List<RolePermissionRelation> appPerList;

	/** 分配人员. */
	private String userIdList;

	/** 错误消息. */
	private String errorMsg;

	/**
	 * 查询角色列表.
	 * 
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public void queryRoleList() throws JSONException {
		Page<Role> pageData = this.getJqGridPage("id");
		try {
			Log.info("查询角色,角色名称" + roleName);
			PageContext buildPageContext = Struts2.buildPageContext();
			User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
			if (!userManagerService.isSuperManager(tempUser.getId())) {
				buildPageContext.addParam("createBy", tempUser.getId());
			}
			// 当查询条件包含%时，会被转义位%25
			// 由于不是接口传参，所以不需要转义
			if(roleName != null){
				if(roleName.contains("%")){
					roleName = roleName.replaceAll("%25", "%");
				}
				if("_".equals(roleName)){
					roleName = "\\" + roleName;
				}
			}
			// 条件查询
			buildPageContext.addParam("roleName", roleName);
			com.cmsz.paas.common.page.Page<Role> findPage = roleService.findPage(buildPageContext);
			pageData.setResult(findPage.getResult());
			pageData.setTotalCount(findPage.getTotalCount());
			
			JSONObject jsonPage = this.getJsonPage(pageData);
			JSONArray jsonList = new JSONArray();
			for (Role role : pageData.getResult()) {
				JSONObject jo = this.entityToJsonObject(role);
				jsonList.put(jo);
			}
			jsonPage.put("rows", jsonList);
			Log.info("查询角色成功，查询结果" + jsonPage.toString());
			this.renderText(jsonPage.toString());
		} catch (PaasWebException ex) {
			logger.error("查询角色列表异常" + ex.getMessage(), ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询角色树形结构.
	 */
	public void queryRoleTreeNode() {
		try {
			Log.info("查询角色树形结构");
			List<TreeNode> treeNodeList = roleService.queryRoleTreeNode();
			this.renderText(JackJson.fromObjectToJson(treeNodeList));
			Log.info("查询角色树形结构成功" + JackJson.fromObjectToJson(treeNodeList));
		} catch (PaasWebException ex) {
			logger.error("查询所有角色的树形结构" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 删除角色.
	 * 
	 * @throws JSONException
	 */
	public void deleteRole() throws JSONException {
		try {
			Log.info("删除角色,角色名" + roleId);
			Role tempRole = roleService.queryRole(roleId);
			if (tempRole.getType() == Constants.ROLE_TYPE_SUPER_MANAGER) {
				sendFailReslutl("超级管理员角色不能删除");
				return;
			}
			List<UserRoleRelation> userRoleList = userRoleService.queryByRole(roleId);
			if (userRoleList != null && userRoleList.size() > 0) {
				sendFailResult(Constants.QUERY_USER_ROLE_BY_ROLE_ID, "还有人员使用,不能删除");
				return;
			}
			
			List<RolePermissionRelation> rolePerList = rolePerService.queryByRoleId(roleId);
			appPerList = new ArrayList<RolePermissionRelation>();
			//应用权限
			for(RolePermissionRelation relation : rolePerList){
				if(relation.getPermissionType() == Constants.ROLE_APP_PERMISSION_TYPE){
					appPerList.add(relation);
				}
			}
			if(appPerList != null && appPerList.size()>0){
				sendFailResult(Constants.QUERY_ROLE_PERMISSION_ERROR, "该角色有绑定的应用,不能删除");
				return;
			}
			
			roleService.deleteRole(roleId);
			getSession().setAttribute("roleId", roleId);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("删除角色异常" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 查询角色与用户的关联.
	 * 
	 * @return the string 返回成功
	 */
	public String queryRoleUser() {
		try {
			Log.info("查询角色关联用户，关联ID" + roleId);
//			User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
			List<User> userList = new ArrayList<User>();
//			if (userManagerService.isSuperManager(tempUser.getId())) {
//				userList = userManagerService.queryUserAll();
//			} else {
//				userList = userManagerService.queryUserByCreator(tempUser.getId());
//			}
			userList = userManagerService.queryUserAll();
			//该角色关联的用户（已分配用户）
			List<UserRoleRelation> userRoleList = userRoleService.queryByRole(roleId);
			//存放可能重复的对象
			List<Multiselect> tempList = new ArrayList<Multiselect>();
			Multiselect multiselect = null;
			for (User user : userList) {
				if (userManagerService.isSuperManager(user.getId())) {
					continue;
				}
				multiselect = new Multiselect();
				multiselect.setId(String.valueOf(user.getId()));
				// 用户后面加上（开发、测试、运维）操作类型
				String userOperType = rolePerService.queryOperTypeByUserId(user.getId());
				if(userOperType != null && !"".equals(userOperType)){
//					multiselect.setName(user.getLoginName() + "(" + ("1".equals(userOperType)?"开发":"测试") + ")");
					if("1".equals(userOperType)){
						multiselect.setName(user.getLoginName() + "(开发)");
					} else if ("2".equals(userOperType)){
						multiselect.setName(user.getLoginName() + "(测试)");
					} else if ("3".equals(userOperType)){
						multiselect.setName(user.getLoginName() + "(运维)");
					}
				} else {
					multiselect.setName(user.getLoginName());
				}
				
				if(userRoleList != null && userRoleList.size() > 0){ //该角色有关联的用户
					for (UserRoleRelation userRole : userRoleList) {
						if (user.getId().equals(userRole.getUserId())) { //当前角色选中的用户
							multiselect.setSelect(true);
							tempList.add(multiselect);
							break;
						} else { //当前角色没有选中的用户
							List<UserRoleRelation> list = userRoleService.queryByUser(user.getId());
							if (list != null && list.size() > 0) {
								for(UserRoleRelation relation : list){
									if(relation.getRoleId() != roleId){
										continue;
									}else{
										if(relation.getUserId() == user.getId()){
											multiselect.setSelect(true);
											tempList.add(multiselect);
										}
									}
								}
								tempList.add(multiselect);
								continue;
							}else{
								tempList.add(multiselect);
								continue;
							}
						}
					}
				}else{ //该角色没有关联的用户
					multiselect.setSelect(false);
					if (userManagerService.isSuperManager(user.getId())) { //超级管理员
						continue;
					}
					tempList.add(multiselect);
					continue;
				}
				
				// 判断用户是否已经授予角色
//				List<UserRoleRelation> list = userRoleService.queryByUser(user.getId());
//				if (list.size() > 0) {
//					continue;
//				}
//				userSelectList.add(multiselect);
			}
			//去除重复的对象
			userSelectList = new ArrayList<Multiselect>();
			int size = tempList.size() ;
			for(int i = 0 ; i < size ; i++){
				if(! tempList.subList(i+1, size).contains(tempList.get(i))){
					userSelectList.add(tempList.get(i));
				}
			}
		} catch (PaasWebException ex) {
			logger.error("查询角色关联的用户异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorMsg = ex.toString();
		}
		Log.info("查询角色关联用户成功");
		return SUCCESS;
	}
	
	/**
	 * 根据roleId查询角色所属操作类型（1-开发，2-测试，3-运维）
	 * @return
	 */
	public void queryRoleOperTypeByRoleId() {
		try {
			String roleOperType = rolePerService.queryOperTypeByRoleId(roleId);
			this.renderText(JackJson.fromObjectToJson(roleOperType));
		} catch (PaasWebException e) {
			logger.error("角色所属操作类型异常 ", e);
			this.sendFailResult2jqGrid(e.getKey(), e.toString());
		}
		
	}

	/**
	 * 角色管理-操作-人员分配.
	 */
	public void roleGrantUser() {
		try {
			Log.info("用户角色授权" + roleId);
			if (userIdList == null || userIdList.equals("")) {
				userRoleService.deleteByRole(roleId);
				sendSuccessReslult("操作成功");
				return;
			}
			// 当前角色原来分配的用户
			List<UserRoleRelation> userRoleList = userRoleService.queryByRole(roleId);
			// 重新选中的所有用户
			String[] userRoleArray = userIdList.split(",");
			// 当前角色所属的操作类型（开发-1、测试-2）
			String currentRoleOperType = rolePerService.queryOperTypeByRoleId(roleId);
			// 循环比较重新选中的用户所属的操作类型，与当前角色的操作类型是否匹配
			if(currentRoleOperType != null && !"".equals(currentRoleOperType)){
				for(String node : userRoleArray){
					String currentUserOperType = rolePerService.queryOperTypeByUserId(Long.parseLong(node));
					if(currentUserOperType != null && !"".equals(currentUserOperType)){
						if(!currentRoleOperType.equals(currentUserOperType)){
							sendFailResult(Constants.QUERY_ROLE_PERMISSION_ERROR, "角色不能分配既是开发又是测试用户");
							return;
						}
					}
				}
			} else {
				TreeSet<String> checkRepeatOperType = new TreeSet<String>();
				for(String node : userRoleArray){
					String currentUserOperType = rolePerService.queryOperTypeByUserId(Long.parseLong(node));
					if(currentUserOperType != null && !"".equals(currentUserOperType)){
						checkRepeatOperType.add(currentUserOperType); // TreeSet存储重复的值只会存一个
						if(checkRepeatOperType.size() > 1){
							sendFailResult(Constants.QUERY_ROLE_PERMISSION_ERROR, "角色不能分配既是开发又是测试用户");
							return;
						}
					}
				}
			}
			
			List<UserRoleRelation> addUserList = findAddUser(userRoleArray,
					userRoleList);
			List<UserRoleRelation> deleteUserList = findDeleteUser(
					userRoleArray, userRoleList);
			userRoleService.updateUserRole(deleteUserList, addUserList);
			sendSuccessReslult("操作成功");
		} catch (PaasWebException ex) {
			logger.error("人员分配异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 查询角色新增的关联用户.
	 * 
	 * @param selectNode
	 *            用户选中的关联用户
	 * @param userRoleList
	 *            角色管理的用户集合
	 * @return List UserRoleRelation 新增的关联用户集合
	 */
	private List<UserRoleRelation> findAddUser(String[] selectNode,
			List<UserRoleRelation> userRoleList) {
		Log.info("查询新增用户");
		UserRoleRelation addUser = null;
		List<UserRoleRelation> addUserList = new ArrayList<UserRoleRelation>();
		for (String node : selectNode) {
			boolean isFind = false;
			for (UserRoleRelation userRole : userRoleList) {
				Long userId = userRole.getUserId();
				if (userId.equals(Long.valueOf(node))) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				addUser = new UserRoleRelation();
				addUser.setRoleId(roleId);
				addUser.setUserId(Long.valueOf(node));
				addUserList.add(addUser);
			}
		}
		return addUserList;
	}

	/**
	 * 查询角色删除的关联用户.
	 * 
	 * @param selectNode
	 *            用户选中的关联用户
	 * @param userRoleList
	 *            角色关联的用户集合
	 * @return List UserRoleRelation 删除的关联用户集合
	 */
	private List<UserRoleRelation> findDeleteUser(String[] selectNode,
			List<UserRoleRelation> userRoleList) {
		Log.info("查询删除用户");
		UserRoleRelation deleteUser = null;
		List<UserRoleRelation> deleteUserList = new ArrayList<UserRoleRelation>();
		for (UserRoleRelation userRole : userRoleList) {
			Long userId = userRole.getUserId();
			boolean isFind = false;
			for (String node : selectNode) {
				if (userId.equals(Long.valueOf(node))) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				deleteUser = new UserRoleRelation();
				deleteUser.setRoleId(roleId);
				deleteUser.setUserId(userId);
				deleteUserList.add(deleteUser);
			}
		}
		return deleteUserList;
	}

	/**
	 * 新增角色.
	 */
	public void createRole() {
		Log.info("创建角色");
		Role role = new Role();
		role.setDescription(description);
		role.setRoleName(roleName);
//		role.setType(roleType); //迭代二角色类型:0超级管理员，1普通管理员，2普通用户
		role.setType(1); //迭代三角色类型:0超级管理员，1普通用户
		User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
		role.setCreateBy(tempUser.getId());
		try {
			if (roleService.queryRoleByName(roleName) != null) {
				this.sendFailReslutl("角色名称已经存在");
				return;
			}
			roleService.createRole(role, operSelectNode, dataSelectNode);
			getSession().setAttribute("roleId", role.getId());
			Log.info("创建信息" + role.toString());
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("创建角色异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 查询角色.
	 * 
	 * @return the string 返回成功
	 */
	public String queryRole() {
		try {
			Log.info("查询角色，角色ID" + roleId);
			role = roleService.queryRole(roleId);
			
			List<RolePermissionRelation> rolePerList = rolePerService.queryByRoleId(roleId);
			appPerList = new ArrayList<RolePermissionRelation>();
			//应用权限
			for(RolePermissionRelation relation : rolePerList){
				if(relation.getPermissionType() == Constants.ROLE_APP_PERMISSION_TYPE){
					appPerList.add(relation);
				}
			}
			
		} catch (PaasWebException ex) {
			logger.error("查询角色异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorMsg = ex.toString();
		}
		return SUCCESS;
	}

	/**
	 * 查询角色关联的权限树形结构.
	 */
	public void queryRolePerTree() {
		Log.info("查询角色父节点树" + roleId);
		Role tempRole = roleService.queryRole(roleId);
		if (Constants.ROLE_TYPE_SUPER_MANAGER == tempRole.getType()) {
			sendFailReslutl("超级管理员不能修改");
			return;
		}
		try {
			@SuppressWarnings("unchecked")
			List<OperPermission> operPerList = (List<OperPermission>) getSessionMap().get("operPermission");
			List<RolePermissionRelation> rolePerList = rolePerService.queryByRoleId(roleId);
			
			List<TreeNode> operTreeList = new ArrayList<TreeNode>();
			TreeNode treeNode1 = null;
			//操作权限
			for (OperPermission info : operPerList) {
				treeNode1 = new TreeNode();
				treeNode1.setId(info.getId());
				treeNode1.setName(info.getName());
				treeNode1.setTitle(info.getName());
				boolean isFind = isFind(treeNode1, rolePerList,
						Constants.ROLE_OPER_PERMISSION_TYPE);
				treeNode1.setChecked(isFind);
				if (info.getName().equals("服务管理")) {
					treeNode1.setChkDisabled(true);
					treeNode1.setChecked(true);
				}
				//系统管理、系统监控 不能授予给普通用户
				if ("系统管理".equals(info.getName())
						|| "用户管理".equals(info.getName())
						|| "角色管理".equals(info.getName())) {
					treeNode1.setChkDisabled(true);
				}
				operTreeList.add(treeNode1);
			}
			
			PermissionTree tree = new PermissionTree();
			tree.setOperPerTreeList(operTreeList);
			Log.info("查询角色父节点树成功，结果：" + JackJson.fromObjectToJson(tree));
			this.renderText(JackJson.fromObjectToJson(tree));
		} catch (PaasWebException ex) {
			logger.error("查询角色权限：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		} catch (Exception ex) {
			logger.error("查询角色权限：" + ex.getMessage(), ex);
			this.sendFailReslutl(ex.toString());
		}
	}

	/**
	 * 是否在角色权限关联找到相对应的权限.
	 * 
	 * @param treeNode
	 *            权限树形结构
	 * @param rolePerList
	 *            角色权限关联集合
	 * @param type
	 *            权限类型:1操作权限，2应用组，3应用
	 * @return true找到 false 没有找到
	 */
	private boolean isFind(TreeNode treeNode,
			List<RolePermissionRelation> rolePerList, int type) {
		Log.info("角色树子节点");
		for (RolePermissionRelation rolePer : rolePerList) {
			if (rolePer.getPermissionType() == type) {
				if (type == 1) { // 1操作权限类型
					if (treeNode.getId().equals(rolePer.getPermissionId())) {
						return true;
					}
				} else { // 2应用组，3应用
					String treeNodeId = treeNode.getId();
					treeNodeId = treeNodeId.substring(0,
							treeNodeId.length() - 1); // 将树Id包含的“a”或“g”去掉
					if (treeNodeId.equals(rolePer.getPermissionId())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 更新角色信息.
	 */
	public void updateRole() {
		Log.info("更改角色.角色ID" + roleId);
		Role oldRole = roleService.queryRole(roleId);
		if (Constants.ROLE_TYPE_SUPER_MANAGER == oldRole.getType()) {
			sendFailReslutl("超级管理员不能修改");
			return;
		}
		Role role = new Role();
		role.setId(roleId);
		role.setDescription(description);
		role.setRoleName(roleName);
		try {
			Role tempRole = roleService.queryRoleByName(roleName);
			if (tempRole != null && !tempRole.getId().equals(roleId)) {
				this.sendFailReslutl("角色名称已经存在");
				return;
			}
			List<RolePermissionRelation> rolePerList = rolePerService.queryByRoleId(roleId);
			List<RolePermissionRelation> addPermissionList = new ArrayList<RolePermissionRelation>();
			// 更新角色时添加的操作权限
			List<RolePermissionRelation> addOperPermission = findAddPermission(
					operSelectNode, rolePerList,
					Constants.ROLE_OPER_PERMISSION_TYPE);
			if (addOperPermission != null && addOperPermission.size() > 0) {
				addPermissionList.addAll(addOperPermission);
			}

			List<RolePermissionRelation> deletePermissionList = new ArrayList<RolePermissionRelation>();
			// 更新角色时删除的操作权限
			List<RolePermissionRelation> deleteOperPermission = findDeletePermission(
					operSelectNode, rolePerList,
					Constants.ROLE_OPER_PERMISSION_TYPE);
			if (deleteOperPermission != null && deleteOperPermission.size() > 0) {
				deletePermissionList.addAll(deleteOperPermission);
			}

			roleService.updateRole(role, deletePermissionList, addPermissionList);
			getSession().setAttribute("roleId", role.getId());
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("更改角色异常" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 查找角色新增的权限.
	 * 
	 * @param selectNode
	 *            用户选中角色新增的权限
	 * @param rolePerList
	 *            集合关联的权限集合
	 * @param type
	 *            权限类型1操作权限2数据权限；迭代二权限类型：1操作权限，2应用组，3应用
	 * @return List RolePermissionRelation 角色新增的权限集合
	 */
	private List<RolePermissionRelation> findAddPermission(String selectNode,
			List<RolePermissionRelation> rolePerList, int type) {
		Log.info("查询新增操作权限");
		if (selectNode == null || selectNode.equals("")) {
			return null;
		}
		String[] nodeArray = selectNode.split(",");
		RolePermissionRelation addPermission = null;
		List<RolePermissionRelation> addPermissionList = new ArrayList<RolePermissionRelation>();
		for (String node : nodeArray) {
			boolean isFind = false;
			for (RolePermissionRelation rolePer : rolePerList) {
				String permissionId = rolePer.getPermissionId();
				int permissionType = rolePer.getPermissionType();
				if (permissionId.equals(node) && permissionType == type) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				addPermission = new RolePermissionRelation();
				addPermission.setRoleId(roleId);
				addPermission.setPermissionId(node);
				addPermission.setPermissionType(type);
				addPermissionList.add(addPermission);
			}
		}
		return addPermissionList;
	}

	/**
	 * 查找角色删除的权限.
	 * 
	 * @param selectNode
	 *            用户选中角色新增的权限
	 * @param rolePerList
	 *            集合关联的权限集合
	 * @param type
	 *            权限类型1操作权限2数据权限
	 * @return List RolePermissionRelation 角色删除的权限集合
	 */
	private List<RolePermissionRelation> findDeletePermission(
			String selectNode, List<RolePermissionRelation> rolePerList,
			int type) {
		Log.info("查询删除权限");
		List<RolePermissionRelation> deletePermissionList = new ArrayList<RolePermissionRelation>();
		if (selectNode == null || selectNode.equals("")) {
			for (RolePermissionRelation rolePer : rolePerList) {
				if (rolePer.getPermissionType() == type) {
					deletePermissionList.add(rolePer);
				}
			}
			return deletePermissionList;
		}
		String[] nodeArray = selectNode.split(",");
		RolePermissionRelation deletePermission = null;
		for (RolePermissionRelation rolePermission : rolePerList) {
			String permissionId = rolePermission.getPermissionId();
			int permissionType = rolePermission.getPermissionType();
			boolean isFind = false;
			for (String node : nodeArray) {
				if (permissionId.equals(node) && permissionType == type) {
					isFind = true;
					break;
				}
			}
			if (!isFind && permissionType == type) {
				deletePermission = new RolePermissionRelation();
				deletePermission.setRoleId(roleId);
				deletePermission.setPermissionId(permissionId);
				deletePermission.setPermissionType(permissionType);
				deletePermissionList.add(deletePermission);
			}
		}
		return deletePermissionList;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperSelectNode() {
		return operSelectNode;
	}

	public void setOperSelectNode(String operSelectNode) {
		this.operSelectNode = operSelectNode;
	}

	public String getDataSelectNode() {
		return dataSelectNode;
	}

	public void setDataSelectNode(String dataSelectNode) {
		this.dataSelectNode = dataSelectNode;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Multiselect> getUserSelectList() {
		return userSelectList;
	}

	public void setUserSelectList(List<Multiselect> userSelectList) {
		this.userSelectList = userSelectList;
	}

	public String getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public List<RolePermissionRelation> getAppPerList() {
		return appPerList;
	}

	public void setAppPerList(List<RolePermissionRelation> appPerList) {
		this.appPerList = appPerList;
	}

}
