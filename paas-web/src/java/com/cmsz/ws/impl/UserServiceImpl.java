package com.cmsz.ws.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.base.util.DES;
import com.cmsz.paas.web.base.util.MD5Util;
import com.cmsz.paas.web.user.dao.UserDao;
import com.cmsz.paas.web.user.dao.UserRoleRelationDao;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.entity.UserRoleRelation;
import com.cmsz.paas.web.user.service.UserRoleRelationService;
import com.cmsz.vo.ResultVO;
import com.cmsz.vo.UserInfoVO;
import com.cmsz.ws.UserService;

/** 
 * @author 林绵炎
 * @version 创建时间：2016年11月21日 上午9:46:03
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleRelationDao userRoleRelationDao;
	
	/** 用户与角色关联service对象. */
	@Autowired
	private UserRoleRelationService userRoleService;

	/*
	 * 用户查询
	 * 功能：同步从帐号至4A平台
	 * 参数：为空查询全部
	 * @see com.cmsz.ws.UserService#queryUserInfo(java.lang.String)
	 */
	@Override
	public List<UserInfoVO> queryUserInfo(String loginUser) {
		
		logger.info("同步从帐号至4A平台:" + loginUser);
		
		List<UserInfoVO> voList = new ArrayList<UserInfoVO>();
		UserInfoVO userInfo = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginName", loginUser);
		List<User> userList = userDao.findBy(map);
		
		for(User user : userList){
			userInfo = new UserInfoVO();
			userInfo.setLoginUser(user.getLoginName());
			userInfo.setPassword(user.getPassword()); // 密码（密文） 4A方提供加解密方式
			userInfo.setStaffName(user.getUserName());
			userInfo.setFlag("");
			userInfo.setValidLength("");
			userInfo.setExpireDate(null);
			userInfo.setLogLock("1"); // （锁定状态） 0，禁用；1，启用；9删除
			userInfo.setDepartmentCode("");
			
			// 根据用户id查询用户角色关联关系，从而获取角色id，组成用户所属的工作组（角色）
			Map<String,Long> relation = new HashMap<String,Long>();
			relation.put("userId", user.getId());
			List<UserRoleRelation> relationList = userRoleRelationDao.findBy(relation);
			String workCode = "";
			for(UserRoleRelation re : relationList){
				if(workCode.equals("")){
					workCode += re.getRoleId().toString();
				}else{
					workCode += "," + re.getRoleId().toString();
				}
			}
			userInfo.setWorkCode(workCode); // （所属工作组，多个组用逗号隔开）
			
			userInfo.setNote("");
			voList.add(userInfo);
		}
		logger.info("同步从帐号至4A平台，操作成功");
		return voList;
	}

	/*
	 * 用户新增
	 * 功能：添加从帐号
	 * 参数：
	 * @see com.cmsz.ws.UserService#addUserInfo(com.cmsz.vo.UserInfoVO)
	 */
	@Override
	public ResultVO addUserInfo(UserInfoVO userInfo) {
		
		logger.info("添加从帐号:" + userInfo);
		
		ResultVO result = new ResultVO();
		
		try {
			User user = new User();
			user.setLoginName(userInfo.getLoginUser());
			
			userInfo.setPassword(DES.getInstance().decrypt(userInfo.getPassword())); // 4A方提供加解密算法进行解密
			userInfo.setPassword(MD5Util.MD5(userInfo.getPassword())); // PAAS平台自己存储是：MD5加密
			user.setPassword(userInfo.getPassword());
			
			user.setUserName(userInfo.getStaffName());
			user.setCreateBy(1L);
			// 添加用户信息
			userDao.insert(user);
			
			// 添加用户同时添加用户角色关联信息
			String roleIds = userInfo.getWorkCode();
			
			if(!"".equals(roleIds) && roleIds != null){
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("loginName", userInfo.getLoginUser());
				List<User> userList = userDao.findBy(map);
				Long userId = 0L;
				for(User u : userList){
					userId =  u.getId();
				}
				
				List<UserRoleRelation> reList = new ArrayList<UserRoleRelation>();
				UserRoleRelation rela = null;
				String[] roleId = roleIds.split(",");
				for(int i = 0; i < roleId.length; i++){
					rela = new UserRoleRelation();
					rela.setUserId(userId);
					rela.setRoleId(Long.parseLong(roleId[i]));
					reList.add(rela);
				}
				// 添加用户角色(工作组)关联信息
				userRoleRelationDao.insert(reList);
			}
			result.setResult("0");
			result.setResultDesc("添加从帐号成功");
			logger.info("添加从帐号成功");
		} catch (Exception e) {
			result.setResult("1");
			result.setResultDesc("添加从帐号失败");
			logger.error("添加从帐号失败:" + e);
		}
		return result;
	}

	/*
	 * 用户修改
	 * @see com.cmsz.ws.UserService#modifyUserInfo(com.cmsz.vo.UserInfoVO)
	 */
	@Override
	public ResultVO modifyUserInfo(UserInfoVO userInfo) {
		
		logger.info("修改从帐号：" + userInfo);
		
		ResultVO result = new ResultVO();
		User user = new User();
		
		try {
			// 更新一个实体需要用到Id，所以下面根据登录用户（账号），查找到已经存在的用户从而拿到Id
			Map<String, String> map = new HashMap<String, String>();
			map.put("loginName", userInfo.getLoginUser());
			List<User> userList = userDao.findBy(map);
			
			for(User temp : userList){
				user.setId(temp.getId());
			}
			
			// 说明：如果密码为空，则不修改密码，否则修改。密码传输以4A方加密方式加密（已提供加解密算法）。
			if(!StringUtils.isEmpty(userInfo.getPassword())){
				userInfo.setPassword(DES.getInstance().decrypt(userInfo.getPassword()));
			}
			
			if (userInfo.getPassword()!=null && !"".equals(userInfo.getPassword())) {
				user.setPassword(MD5Util.MD5(userInfo.getPassword()));
			}
			user.setLoginName(userInfo.getLoginUser());
			user.setUserName(userInfo.getStaffName());
			//修改用户信息
			userDao.update(user);
			
			// 修改用户同时修改用户角色关联信息
			String roleIds = userInfo.getWorkCode();
			
			Long userId = user.getId();
			
			if("".equals(roleIds) || roleIds == null){ // 没有角色的时候，要不本来就是没的，要不就是本来有现在删除了
			
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("userId", userId+"");
				List<UserRoleRelation> relist = userRoleRelationDao.findBy(map3);
				if(relist.size() >= 1){
					for(UserRoleRelation re : relist){
						userRoleRelationDao.delete(re);
					}
				}
			}else{ // 有角色的时候
				List<UserRoleRelation> userRoleList = userRoleService.queryByUser(userId);
				String[] nodeArray = roleIds.split(",");
				List<UserRoleRelation> deleteRole = findDeleteRole(nodeArray, userRoleList, userId);
				List<UserRoleRelation> addRole = findAddRole(nodeArray, userRoleList, userId);
				userRoleService.updateUserRole(deleteRole, addRole);
			}
			
			result.setResult("0");
			result.setResultDesc("修改从帐号成功");
			logger.info("修改从帐号成功");
		} catch (Exception e) {
			result.setResult("1");
			result.setResultDesc("修改从帐号失败");
			logger.error("修改从帐号失败:" + e);
		}
		return result;
	}
	
	/**
	 * 查找用户删除的角色.
	 * 
	 * @param nodeArray
	 *            用户选中的角色
	 * @param userRoleList
	 *            用户拥有的角色列表
	 * @return List UserRoleRelation 用户删除的角色集合
	 */
	private List<UserRoleRelation> findDeleteRole(String[] nodeArray,
			List<UserRoleRelation> userRoleList, Long userId) {
		Log.info("查询删除角色");
		UserRoleRelation deleteUserRole = null;
		List<UserRoleRelation> deleteUserRoleList = new ArrayList<UserRoleRelation>();
		for (UserRoleRelation userRole : userRoleList) {
			Long roleId = userRole.getRoleId();
			boolean isFind = false;
			for (String node : nodeArray) {
				if (roleId.equals(Long.valueOf(node))) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				deleteUserRole = new UserRoleRelation();
				deleteUserRole.setRoleId(roleId);
				deleteUserRole.setUserId(userId);
				deleteUserRoleList.add(deleteUserRole);
			}
		}
		return deleteUserRoleList;
	}
	
	/**
	 * 查找用户增加的角色.
	 * 
	 * @param nodeArray
	 *            用户选中的角色
	 * @param userRoleList
	 *            用户拥有的角色
	 * @return List UserRoleRelation 用户增加角色集合
	 */
	private List<UserRoleRelation> findAddRole(String[] nodeArray,
			List<UserRoleRelation> userRoleList, Long userId) {
		Log.info("查询新增角色");
		UserRoleRelation addUserRole = null;
		List<UserRoleRelation> addUserRoleList = new ArrayList<UserRoleRelation>();
		for (String node : nodeArray) {
			boolean isFind = false;
			for (UserRoleRelation userRole : userRoleList) {
				Long roleId = userRole.getRoleId();
				if (roleId.equals(Long.valueOf(node))) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				addUserRole = new UserRoleRelation();
				addUserRole.setRoleId(Long.valueOf(node));
				addUserRole.setUserId(userId);
				addUserRoleList.add(addUserRole);
			}
		}
		return addUserRoleList;
	}
	
	/*
	 * 用户密码修改
	 * 说明：原密码传空值。新密码以4A方加密方式加密传输。
	 * @see com.cmsz.ws.UserService#modifyUserPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultVO modifyUserPassword(String loginUser, String oldPassword,
			String newPassword) {
		
		logger.info("从帐号密码修改：" + loginUser);
		
		ResultVO result = new ResultVO();
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("loginName", loginUser);
			List<User> userList = userDao.findBy(map);
			User user = new User();
			
			//将4A来的密码解密，再按前台方式加密
			newPassword=DES.getInstance().decrypt(newPassword);
			
			for(User u : userList){
				user = u;
				user.setPassword(MD5Util.MD5(newPassword));
			}
			userDao.update(user);
			
			result.setResult("0");
			result.setResultDesc("");
			logger.info("密码修改成功");
		} catch (Exception e) {
			result.setResult("1");
			result.setResultDesc("密码修改失败");
			logger.error("密码修改失败:" + e);
		}
		
		return result;
	}

	/*
	 * 用户删除
	 * @see com.cmsz.ws.UserService#delUserInfo(java.lang.String)
	 */
	@Override
	public ResultVO delUserInfo(String loginUsers) {
		
		logger.info("删除从帐号：" + loginUsers);
		
		ResultVO result = new ResultVO();
		
		// 判断是否为空
		if (isEmpty(loginUsers)) {
			result.setResult("1");
			result.setResultDesc("用户名称为空");
		}
		
		String[] user = loginUsers.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < user.length; i++) {
			map.put("loginName", user[i]);
			List<User> userList = userDao.findBy(map);
			for(User u : userList){ // 通过循环实现批量删除
				
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("userId", u.getId()+"");
				List<UserRoleRelation> relist = userRoleRelationDao.findBy(map3);
				
				if(relist.size() >= 1){
					for(UserRoleRelation re : relist){
						// 删除用户角色(工作组)关联信息
						userRoleRelationDao.delete(re);
					}
				}
				// 删除用户信息
				userDao.delete(u);
				
				result.setResult("0");
				result.setResultDesc("删除从帐号成功");
				logger.info("删除从帐号成功");
			}
		}
		return result;
	}
	
	/*
	 * 空字符串判断处理
	 */
    public static boolean isEmpty(String input) {
        if (input == null) {
            return true;
        }
        if ("".equals(input.trim())) {
            return true;
        }
        return false;
    }

}
