package com.cmsz.paas.web.user.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
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
import com.cmsz.paas.web.base.log.OperationLogUtil;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.permission.service.PermissionService;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.entity.TreeNode;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.cmsz.paas.web.role.service.RoleService;
import com.cmsz.paas.web.user.entity.AdminAppInfo;
import com.cmsz.paas.web.user.entity.AppInfo;
import com.cmsz.paas.web.user.entity.Authority;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.entity.UserErrorLoginRecord;
import com.cmsz.paas.web.user.entity.UserPermission;
import com.cmsz.paas.web.user.entity.UserRoleRelation;
import com.cmsz.paas.web.user.service.UserManagerService;
import com.cmsz.paas.web.user.service.UserRoleRelationService;
import com.cmsz.ws.LoginSwitchService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用户管理Action类
 * 
 * @author zhouyunxia
 */
public class UserManagerAction extends AbstractAction implements SessionAware {
	/**
	 * 随机序列号
	 */
	private static final long serialVersionUID = -7720978896577605824L;

	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory.getLogger(UserManagerAction.class);
	
	// struts2 注入
	private Map<String, Object> session;

	/** 用户管理service对象 . */
	@Autowired
	private UserManagerService userManagerService;

	/** 角色service对象. */
	@Autowired
	private RoleService roleService;

	/** 用户与角色关联service对象. */
	@Autowired
	private UserRoleRelationService userRoleService;

	/** 权限service对象. */
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RolePerRelationService rolePerRelationService; //角色权限关系
	
	@Autowired
	private LoginSwitchService loginSwitchService;

	/** 登陆帐户名. */
	private String loginName;

	/** 登陆密码. */
	private String password;
	
	/** 验证码 */
	private String checkCode;
	
	/** 更改密码时旧密码. */
	private String oldPassword;

	/** 用户登录结果描述. */
	private String loginMessage;

	/** 用户Id. */
	private Long id;

	/** 用户名称. */
	private String userName;

	/** 用户邮箱. */
	private String mail;

	/** 用户信息. */
	private User user;

	/** 选中的节点. */
	private String selectNode;

	/** 错误消息. */
	private String errorMsg;
	
//	private String localIp;
	
	private static Map<String, UserErrorLoginRecord> LOG_MAP = new HashMap<String, UserErrorLoginRecord>();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	Calendar nowTime = Calendar.getInstance();
	Date nowTime = new Date();
	
	/***
	 * 清除历史验证码
	 */
	public void refreshCheckCode(){
		Random r = new Random();
		getSessionMap().put("checkCode", r.nextInt(9999));
	}
	
	/**
	 * 验证登录参数
	 */
	@UnLogging
	public void verifyLoginParams(){
		
		UserErrorLoginRecord userError = null;
		
		User user = null;
		
		try {
			logger.info("开始验证参数，帐号：" + loginName + "、密码：" + password + "、验证码：" + checkCode);
			
			if(!getSessionMap().get("checkCode").toString().toLowerCase().equals(checkCode.toLowerCase())){
				sendAjaxReslult("false", "验证码不正确，请重新输入！");
				refreshCheckCode();
				return;
			}
			refreshCheckCode();
			
			//从配置文件获取登录错误次数
			int loginErrorNumber = Integer.parseInt(PropertiesUtil.getValue("loginErrorNumber"));
			
			userError = LOG_MAP.get(loginName); //通过用户名获取登录信息对象
			
			//如果没有登录失败记录
			if(userError == null){
				user = userManagerService.queryUserByName(loginName);
				if(user == null){
					sendAjaxReslult("false", "用户名或密码错误，请重试！");
					return;
				}
				if (user != null && !user.getPassword().equals(password.toUpperCase())) {
					user = null;
				}
			} else {
				String loginTime = userError.getLoginTime();
				int loginNumber = userError.getErrorNumber();
				
				//从配置文件获取锁定时间（单位：分钟）
				long loginLockTime = Long.parseLong(PropertiesUtil.getValue("loginLockTime")) * 60 * 1000;
				
				//如果现在的时间与Map中存的登录时间之差大于5分钟，那么显然时间已经过了5分钟，那么根据用户名直接清除Map中的登录信息
				if(nowTime.getTime() - Long.parseLong(loginTime)  > loginLockTime){
					LOG_MAP.remove(loginName);
					user = userManagerService.queryUserByName(loginName);
					if(user == null){
						sendAjaxReslult("false", "用户名或密码错误，请重试！");
						return;
					}
					if (user != null && !user.getPassword().equals(password.toUpperCase())) { //密码由原来的后台MD5加密改为前台sha1加密
						user = null;
					}
			        //否则就是判断如果登录次数小于5，那么允许其查询user对象，否则直接将user设为空
				} else {
					if (loginNumber < loginErrorNumber) {
						user = userManagerService.queryUserByName(loginName);
						if(user == null){
							sendAjaxReslult("false", "用户名或密码错误，请重试！");
							return;
						}
						if (user != null && !user.getPassword().equals(password.toUpperCase())) {
							user = null;
						}
			        } else {
			            user = null;
			        }
				}
			}
			
			if (user == null) {
				userError = LOG_MAP.get(loginName);
			    if (userError == null) {
			        userError = new UserErrorLoginRecord();
			        userError.setErrorNumber(1);
			        userError.setLoginTime(nowTime.getTime()+"");
			        LOG_MAP.put(loginName, userError);
			    } else {
			        int loginNumber = userError.getErrorNumber();
			        if(loginNumber < loginErrorNumber){
			        	userError.setErrorNumber(loginNumber + 1);
				        userError.setLoginTime(nowTime.getTime()+"");
				        LOG_MAP.put(loginName, userError);
			        }
			    }
			    userError = LOG_MAP.get(loginName);
			    if (userError.getErrorNumber() < loginErrorNumber) { //连续错误5次，锁定5分钟(这个在配置文件里配置)
			    	sendAjaxReslult("false", "用户名或密码错误，请重试，剩余"+ (loginErrorNumber-userError.getErrorNumber()) +"次机会");
			    	return;
			    } else {
			        sendAjaxReslult("false", "连续登陆失败超过"+loginErrorNumber+"次，请过了"+PropertiesUtil.getValue("loginLockTime")+"分钟后再尝试登陆！");
			    	return;
			    }
			} else {
				
				LOG_MAP.remove(user.getLoginName());
				
				// 获取角色信息
				Role role = null;
				List<UserRoleRelation> userRoleRelationList = userRoleService.queryByUser(user.getId());
				if (userRoleRelationList.size() > 0) {
					for (UserRoleRelation userRoleRelation : userRoleRelationList) {
						role = roleService.queryRole(userRoleRelation.getRoleId());
					}
				} else {
					sendAjaxReslult("false", "该用户没有关联角色，无法登录");
					return;
				}

				UserPermission userPermision = getUserPermission(user.getId(), role.getType());
				if ((null == userPermision.getOperPermissionList() || 0 == userPermision.getOperPermissionList().size()) && role.getId() != 1) {
					sendAjaxReslult("false", "该用户未被授予操作权限");
					return;
				} else {
					if ((null == userPermision.getRolePermissionList() || 0 == userPermision.getRolePermissionList().size()) && role.getId() != 1) {
						sendAjaxReslult("false", "该用户未被授予应用权限");
						return;
					}
				}
				
//				问题描述：登录之前和登录之后session的id值没有变化；
//				问题解决方法：(实现SessionAware接口)在验证登录用户名密码成功后，把session失效，新生成一个session；
				// 把当前session失效
				((SessionMap<String, Object>) session).invalidate();
				// 新生成一个session
				this.session = ActionContext.getContext().getSession();
				
//				session.put("AUTHENTICATED", new Boolean(true));
				
//				getSessionMap().put("userPermision", userPermision);
//				getSessionMap().put("user", user);
//				getSessionMap().put("role", role);
				//下面的存放方式和上面的效果一样，都实现了存放session值
				session.put("userPermision", userPermision);
				session.put("user", user);
				session.put("role", role);
				user.setSessionId(ServletActionContext.getRequest().getSession().getId());
				userManagerService.updateSessionId(user);
				
				//参数都验证通过
				sendAjaxReslult("true", "");
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			sendAjaxReslult("false", "请检查相关数字格式异常信息");
			return;
		} catch (PaasWebException e) {
			logger.error(e.getMessage(), e);
			sendAjaxReslult("false", "请检查数据库连接相关信息");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sendAjaxReslult("false", e.getMessage());
			return;
		}
	}

	/**
	 * 查询用户列表
	 * 
	 * @throws JSONException
	 */
	public void queryUserList() throws JSONException {
		try {
			Log.info("查询用户" + id);
			Page<User> pageData = this.getJqGridPage("id");
			// 写入sql查询条件
			PageContext buildPageContext = Struts2.buildPageContext();
			User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
			if (!userManagerService.isSuperManager(tempUser.getId())) {
				buildPageContext.addParam("createBy", tempUser.getId());
			}
			// 当查询条件包含%时，会被转义位%25
			// 由于不是接口传参，所以不需要转义
			if(loginName != null){
				if(loginName.contains("%")){
					loginName = loginName.replaceAll("%25", "%");
				}
				if("_".equals(loginName)){
					loginName = "\\" + loginName;
				}
			}
			// 条件查询
			buildPageContext.addParam("loginName", loginName);
			com.cmsz.paas.common.page.Page<User> findPage = userManagerService
					.findPage(buildPageContext);
			pageData.setResult(findPage.getResult());
			pageData.setTotalCount(findPage.getTotalCount());
			JSONObject jsonPage = this.getJsonPage(pageData);
			JSONArray jsonList = new JSONArray();
			for (User user : pageData.getResult()) {
				JSONObject jo = this.entityToJsonObject(user);
				jsonList.put(jo);
			}
			jsonPage.put("rows", jsonList);
			Log.info("查询成功" + jsonPage.toString());
			this.renderText(jsonPage.toString());
		} catch (PaasWebException ex) {
			logger.error("查询用户列表异常" + ex.getMessage(), ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 用户登录
	 * @return 成功或失败
	 */
	public String login(){
		
		// 1、从数据库获取4A登陆开关
		String switchValue = loginSwitchService.getSwitchValue();
		
		if(switchValue.equals("0")){ // 开启了4A认证
			logger.info("开启了4A认证，从4A平台登录...");
//			try {
//				// 2、实例化Account2Login4A，用于存储Token串的参数
//				Account2Login4A account = new Account2Login4A();
//				
//				// 3、从request请求中获取Token串
//				String tokenStr = Struts2.getRequest().getParameter("Token"); // 4A平台的登录
//				
//				// 4、 4A带来的请求参数 E681C2A10A7991D085FAD219E6FD88371276224172953@weilisha@appid@MasterAcc
//				if (tokenStr != null && !"".equals(tokenStr)) {
//					String[] strArray = tokenStr.split("@");
//					account.setToken(strArray[0]);
//					account.setSlaveAccount(strArray[1]);
//					account.setAppId(strArray[2]);
//					account.setMasterAccount(strArray[3]);
//					account.setIp(getIpAddr(Struts2.getRequest()));
//					// 4A主帐号
//					getSessionMap().put("masterAccount", strArray[3]);
//				}
//				
//				Http2Login4A client = new Http2Login4A();
//				
//				// 4、调用4A认证的接口
//				Map<String, String> map = client.getSwitchValue(account);
//				
//				// 结果标识（RESULT - 0：验证失败；  1：验证成功）
//				String resultInfo = map.get("result");
//				if ("1".equals(resultInfo) || resultInfo == "1") {
//					
//					loginName = map.get("account");
//					
//					User user = userManagerService.queryUserByName(loginName);
//					// 获取角色信息
//					Role role = null;
//					List<UserRoleRelation> userRoleRelationList = userRoleService.queryByUser(user.getId());
//					if (userRoleRelationList.size() > 0) {
//						for (UserRoleRelation userRoleRelation : userRoleRelationList) {
//							role = roleService.queryRole(userRoleRelation.getRoleId());
//						}
//					}
//					else {
//						setLoginMessage("该用户没有关联角色，无法登录");
//						return "resultMsg";
//					}
//
//					UserPermission userPermision = getUserPermission(user.getId(), role.getType());
//					if (null == userPermision.getOperPermissionList() || 0 == userPermision.getOperPermissionList().size()) {
//						setLoginMessage("该用户未被授予权限");
//						return "resultMsg";
//					}
//					
//					// 通过4A登录方式和系统原来登录方式，都共用该方法
//					loginValidation(userPermision, user, role);
//					
//					OperationLogUtil.ipAddress = getRemoteAddr();
////					OperationLogUtil.ipAddress = localIp;
//					return SUCCESS;
//				} else {
//					// 获取账号失败
//					String resultMsg  = map.get("resultMsg");
//					String resultStr = "";
//					if (resultMsg.equals("USER_UNLOGIN")) {
//						resultStr = "用户没有登陆";
//					} else if (resultMsg.equals("SYS_ERROR")) {
//						resultStr = "系统错误";
//					} else if (resultMsg.equals("SYS_ERROR_DB")) {
//						resultStr = "数据库错误";
//					} else if (resultMsg.equals("SYS_ERROR_NO_PARAMETER")) {
//						resultStr = "参数不完整";
//					} else if (resultMsg.equals("SYS_ERROR_NO_AUTHORIZATION")) {
//						resultStr = "没有对应的授权";
//					} else if (resultMsg.equals("UNAUTHORIZED_IP_ACCESS")) {
//						resultStr = "非法的IP访问";
//					}
//
//					setLoginMessage("检测在线用户在指定应用key中的从账号授权失败【" + resultMsg + "】" + resultStr);
//					return "resultMsg";
//				}
//			} catch (PaasWebException e) {
//				logger.error(e.getMessage(), e);
//				setLoginMessage(e.getErrorMsg());
//				return "resultMsg";
//			}
			return SUCCESS;
		}
		else{ // 关闭了4A认证
			logger.info("关闭了4A认证，从原来的方式登录...");
			try {
				UserPermission userPermision = (UserPermission) getSessionMap().get("userPermision");
				User user = (User) getSessionMap().get("user");
				Role role = (Role) getSessionMap().get("role");
				if (!user.getLoginName().equals(loginName)) {
					return INPUT;
				}
				if(userPermision==null || user==null || role==null){
					return INPUT;
				}
				// 通过4A登录方式和系统原来登录方式，都共用该方法
				loginValidation(userPermision, user, role);
				
				getSessionMap().put("masterAccount", ""); //不通过4A登录，也需要保存这个参数
				
				setLoginMessage("success");
				OperationLogUtil.ipAddress = getRemoteAddr();
//				OperationLogUtil.ipAddress = localIp;
//				OperationLogUtil.ipAddress = getIpAddr(Struts2.getRequest());
				return SUCCESS;
			} catch (PaasWebException e) {
				logger.error("登录" + e.getMessage(), e);
				if (e.getKey().equals("control")) {
					setLoginMessage("访问控制中心报错,请检查连接是否正常");
				} else {
					setLoginMessage(PropertiesUtil.getValue(e.getKey()) + ",请检查数据库");
				}
				return INPUT;
			}
		}
	}
	
	public String getRemoteAddr() {
        try{
            HttpServletRequest request =  Struts2.getRequest();
            String remoteAddr = request.getHeader("X-Forwarded-For");
            logger.info("X-Forwarded-For" + remoteAddr);
            if (isEffective(remoteAddr) && (remoteAddr.indexOf(",") > -1)) {
                String[] array = remoteAddr.split(",");
                for (String element : array) {
                    if (isEffective(element)) {
                        remoteAddr = element;
                        break;
                    }
                }
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getHeader("X-Real-IP");
                logger.info("X-Real-IP " + remoteAddr);
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
                logger.info("request.getRemoteAddr() " + remoteAddr);
            }
            return remoteAddr;
        }catch(Exception e){
            logger.error("get romote ip error,error message:"+e.getMessage());
            return "";
        }
   }
	
	private static boolean isEffective(String remoteAddr) {
        boolean isEffective = false;
        if ((null != remoteAddr) && (!"".equals(remoteAddr.trim()))
                && (!"unknown".equalsIgnoreCase(remoteAddr.trim()))) {
            isEffective = true;
        }
        return isEffective;
   }
	
	/**
	 * 
	 * 通过4A登录方式和系统原来登录方式，都共用该方法
	 * @param userPermision
	 * @param user
	 * @param role
	 */
	private void loginValidation(UserPermission userPermision, User user, Role role) {
		
		// 角色应用关联关系
		List<RolePermissionRelation> list = userPermision.getRolePermissionList();
		String appPermissionId = "";
		String appPermissionName = "";
		String opertype = "";

		for (RolePermissionRelation rolePermission : list) {
			if (null != rolePermission) {
				if (rolePermission.getPermissionType() == 2) { // 权限类型，1表示操作权限，2表示数据权限，如应用
					if (appPermissionId.equals("")) {
						appPermissionId += rolePermission.getPermissionId();
						appPermissionName += rolePermission.getPermissionName();
						opertype += rolePermission.getOpertype();
					} else {
						appPermissionId += ","
								+ rolePermission.getPermissionId();
						appPermissionName += ","
								+ rolePermission.getPermissionName();
						opertype += "," + rolePermission.getOpertype();
					}
				}
			}
		}

		TreeMap<String, String> noReapted = new TreeMap<String, String>();

		String noReaptedAppId = "";
		String noReaptedAppName = "";

		String[] appPermissionIdTemp = appPermissionId.split(",");
		String[] appPermissionNameTemp = appPermissionName.split(",");

		for (int i = 0; i < appPermissionIdTemp.length; i++) {
			noReapted.put(appPermissionIdTemp[i], appPermissionNameTemp[i]);
		}

		Iterator it = noReapted.entrySet().iterator();
		while (it.hasNext()) {
			// entry的输出结果如key0=value0等
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (noReaptedAppId.equals("")) {
				noReaptedAppId += key;
			} else {
				noReaptedAppId += "," + key;
			}

			if (noReaptedAppName.equals("")) {
				noReaptedAppName += value;
			} else {
				noReaptedAppName += "," + value;
			}
		}

		String noReaptedOpertype = "";
		TreeSet<String> noReapted3 = new TreeSet<String>(); // 带有String类型的TreeSet泛型
		String[] opertypeTemp = opertype.split(",");
		for (int i = 0; i < opertypeTemp.length; i++) {
			noReapted3.add(opertypeTemp[i]);
		}
		for (String index : noReapted3) {
			if (noReaptedOpertype.equals("")) {
				noReaptedOpertype += index;
			} else {
				noReaptedOpertype += "," + index;
			}
		}

		getSessionMap().put(Constants.CURRENT_USER, user);
		getSessionMap().put("loginName", loginName);
		getSessionMap().put("user_name", user.getUserName());
		getSessionMap().put("roleId", role.getId());
		getSessionMap().put("roleName", role.getRoleName());
		getSessionMap().put("roleType", role.getType());

		getSessionMap().put("appPermissionId", noReaptedAppId); // 应用ID
		getSessionMap().put("appPermissionName", noReaptedAppName); // 应用名称
		getSessionMap().put("opertype", noReaptedOpertype); // 操作类型，如开发1，测试2，运维3，只有应用才有操作类型
		// getSessionMap().put("appPerFlag", false);//用户是否更改应用状态 false为初始状态
		// true为用户修改过

		// 当前选中应用的ID
		getSessionMap().put("appPerSelectedId", noReaptedAppId);
		// 当前选中应用的名称
		getSessionMap().put("appPerSelectedName", noReaptedAppName);
		// 当前选中操作类型名称
		getSessionMap().put("selectedOpertype", noReaptedOpertype);
		
//		getSessionMap().put("localIp", localIp);

		// 当前网络环境标示
		getSessionMap().put("currentContext", PropertiesUtil.getValue("currentContext").equals("development") ? true : false);

		getSessionMap().put("operPermission", userPermision.getOperPermissionList()); // 操作权限
		getSessionMap().put("unOperPermission", userPermision.getUnOperPermissionList());
		getSessionMap().put("dataPermission", userPermision.getRolePermissionList()); // 应用权限
		
	}
	
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 * 
	 * @return ip
	 */
//	private String getIpAddr(HttpServletRequest request) {
//		String ip = request.getHeader("x-forwarded-for");
//		System.out.println("x-forwarded-for ip: " + ip);
//		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
//			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
//			if (ip.indexOf(",") != -1) {
//				ip = ip.split(",")[0];
//			}
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//			System.out.println("Proxy-Client-IP ip: " + ip);
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//			System.out.println("WL-Proxy-Client-IP ip: " + ip);
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("HTTP_CLIENT_IP");
//			System.out.println("HTTP_CLIENT_IP ip: " + ip);
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//			System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("X-Real-IP");
//			System.out.println("X-Real-IP ip: " + ip);
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//			System.out.println("getRemoteAddr ip: " + ip);
//		}
//		System.out.println("获取客户端ip: " + ip);
//		return ip;
//	}
	
	/** 应用切换操作类型   1为触发加载联动效果  否则为保存操作 */
	private int type;
	
	/** 应用Id */
	private String appId;
	
	/** 应用名称   */
	private String deveName;
	
	/** 应用类型名称  */
	private String typeName;
	
	
	public void queryAppType(){
		String typeIds = "";
		if(type == 1){
			if(!"0".equals(appId)){
				User user = (User) getSessionMap().get(Constants.CURRENT_USER);
				typeIds = userManagerService.queryOperTypeById(user, appId);
			}else{
				typeIds = (String) getSessionMap().get("opertype");
			}
			//将结果覆盖原有值
			getSessionMap().put("selectedOpertype", typeIds);
			this.renderText(typeIds);
		}else{
			//0为全部 取初始值
			if("0".equals(appId)){
				appId = (String) getSessionMap().get("appPermissionId");
			}
			if("0".equals(typeName)){
				typeName = (String) getSessionMap().get("opertype");
			}
			if("".equals(appId)){
				appId = "0";
				deveName  = "全部";
				}
			if("".equals(typeName)){
				typeName = "0";
			}
			
			if((Boolean) getSessionMap().get("currentContext") && "3".equals(typeName)){
				if(getSessionMap().get("opertype").toString().indexOf(",")>-1){
					typeName = "1";
				}else{
					typeName = getSessionMap().get("opertype").toString();
				}
			}
			
			//当前选中应用的ID
			getSessionMap().put("appPerSelectedId", appId);
			//当前选中应用的名称
			getSessionMap().put("appPerSelectedName", deveName);
			//当前选中操作类型名称
			User user = (User) getSessionMap().get(Constants.CURRENT_USER);
			typeIds = userManagerService.queryOperTypeById(user, appId);
			getSessionMap().put("selectedOpertype", typeName);
			sendSuccessReslult(typeName);
		}
	}
	
	/***
	 * 获取当前用户所有的应用信息
	 */
	public void queryAppInfo(){
		boolean isAdmin = "0".equals(getSessionMap().get("roleType").toString())?true:false;
		//将该用户拥有的所有应用权限封装
		List<AppInfo> appList = new ArrayList<AppInfo>();
		String[] appIds = getSessionMap().get("appPermissionId").toString().split(",");
		String[] appNames = getSessionMap().get("appPermissionName").toString().split(",");
		for (int i = 0; i < appIds.length; i++) {
			if(!"".equals(appIds[i])){
				AppInfo app = new AppInfo();
				app.setId(appIds[i]);
				app.setAppName(appNames[i]);
				
				//0为管理员  需要展示操作类型,普通的不需要展示操作类型
				if(isAdmin){
					if(!(boolean) getSessionMap().get("currentContext")){//是否为生存环境
						if(i == 0 && getSessionMap().get("opertypestate") == null){//管理员第一次登录默认为全部
							getSessionMap().put("opertypestate", "全部");
						}
						app.setOperType("3");
						appList.add(app);
					}else{
						app.setOperType("1");
						
						//按原逻辑管理员既有开发也有测试 不需要查询
						AppInfo app2 = new AppInfo();
						app2.setId(appIds[i]);
						app2.setAppName(appNames[i]);
						app2.setOperType("2");
						appList.add(app);
						appList.add(app2);
					}
					
				}else{
					//普通用户不需要绑定操作类型
					appList.add(app);
				}
			}
		}
		AdminAppInfo adminAppInfo = new AdminAppInfo();
		adminAppInfo.setAdmin(isAdmin);
		adminAppInfo.setAppInfo(appList);
		this.sendSuccessReslult(adminAppInfo);
	}
	
	/***
	 * 
	 */
	@UnLogging
	public void deleteSessionInfo(){
		if(getSessionMap()!=null){
			getSessionMap().clear();
		}
		sendSuccessReslult();
	}
	
	/***
	 * 获取当前用户的操作类型
	 */
	public void queryCurrentUserOperType(){
		Object type = getSessionMap().get("selectedOpertype");
		this.sendSuccessReslult(type);
	}
	
	/***
	 * 应用树切换改变响应session值
	 */
	public void changeAppInfo(){
		//0为选择全部 重置选中appId为初始值
		if("".equals(appId) || "0".equals(appId) || null == appId){//选择为全部重置应用和操作类型
			appId = (String) getSessionMap().get("appPermissionId");
			typeName = getSessionMap().get("opertype").toString()+"%"+getSessionMap().get("appPermissionName").toString();
			getSessionMap().put("opertypestate", "全部");
		}else{
			//实时查询当前用户选择应用的操作类型
			User user = (User) getSessionMap().get(Constants.CURRENT_USER); 
			typeName = rolePerRelationService.queryUserRoleType(Long.valueOf(appId), user.getId(),typeName);
			getSessionMap().put("opertypestate", "");
		}
		getSessionMap().put("appPerSelectedId", appId);
		getSessionMap().put("selectedOpertype", typeName.split("%")[0]);
		// 当前选中应用的名称
		getSessionMap().put("appPerSelectedName", typeName.split("%")[1]);
		this.sendSuccessReslult();
	}

	/**
	 * 获取用户权限：操作权限，数据权限，没有拥有的权限
	 * 
	 * @param userId
	 *            用户id
	 * @return UserPermission 用户权限：操作权限，数据权限，没有拥有的权限
	 * @throws PaasWebException
	 */
	private UserPermission getUserPermission(Long userId, int roleType)
			throws PaasWebException {
		Log.info("获取用户权限" + userId);
		//获取用户权限
		UserPermission userPermision = new UserPermission();
		//获取用户权限其中的【操作权限】
		List<OperPermission> operPermission = permissionService.queryOperPermission();
		
		if (userManagerService.isSuperManager(userId)) { //超级管理员，具有全部的数据权限和操作权限
			try {
				//全部
				List<RolePermissionRelation> appInfo = rolePerRelationService.queryRolePermissionRelation();
				//超级管理员应用权限
				List<RolePermissionRelation> adminAppPer = new ArrayList<RolePermissionRelation>();
				for(RolePermissionRelation relation : appInfo){
					if(relation.getRoleId() == Constants.ROLE_ID){
						adminAppPer.add(relation);
					}
				}
				userPermision.setRolePermissionList(adminAppPer); //【应用权限】
//				userPermision.setRolePermissionList(appInfo); //【应用权限】
				userPermision.setOperPermissionList(operPermission); //【操作权限】
				userPermision.setUnOperPermissionList(new ArrayList<OperPermission>()); //【没有拥有的权限】
			} catch (PaasWebException ex) {
				throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
			}
		} else { //普通用户
			List<RolePermissionRelation> userPermissionList = userManagerService.queryUserPermission(userId);
			List<OperPermission> operPermissionList = new ArrayList<OperPermission>();
			List<RolePermissionRelation> appPermissionList = new ArrayList<RolePermissionRelation>();
			for (RolePermissionRelation rolePermission : userPermissionList) {
				if (null != rolePermission) {
					//权限类型，1表示操作权限，2表示数据权限，如应用
					if (rolePermission.getPermissionType() == Constants.ROLE_OPER_PERMISSION_TYPE) {
						for (OperPermission per : operPermission) {
							if (rolePermission.getPermissionId().equals(per.getId())) {
								if (!isAdd(operPermissionList, per.getId())) {
									operPermissionList.add(per);
								}
								break;
							}
						}
					}else if(rolePermission.getPermissionType() == Constants.ROLE_APP_PERMISSION_TYPE){
						appPermissionList.add(rolePermission);
					}
				}
			}
			userPermision.setUnOperPermissionList(findUnOperPermision(operPermissionList, operPermission));
			sortOperMenuMethod(operPermissionList);
			userPermision.setOperPermissionList(operPermissionList);
			userPermision.setRolePermissionList(appPermissionList);
		}
		return userPermision;
	}
	
	/**
	 * 普通用户左侧菜单栏排序
	 * @param list
	 */
	private static void sortOperMenuMethod(List<OperPermission> list) {
		Collections.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object obj1, Object obj2) {
				OperPermission operMenu1 = (OperPermission) obj1;
				OperPermission operMenu2 = (OperPermission) obj2;
				if (Integer.parseInt(operMenu1.getId()) > Integer.parseInt(operMenu2.getId())) {
					return 1;
				} else if (Integer.parseInt(operMenu1.getId()) == Integer.parseInt(operMenu2.getId())) {
					return 0;
				} else {
					return -1;
				}
			}
		});
	}
	
	/**
	 * 获取用户权限：操作权限，数据权限，没有拥有的权限
	 * 
	 * @param userId
	 *            用户id
	 * @return UserPermission 用户权限：操作权限，数据权限，没有拥有的权限
	 * @throws PaasWebException
	 
	private UserPermission getUserPermission(Long userId, int roleType)
			throws PaasWebException {
		Log.info("获取用户权限" + userId);
		UserPermission userPermision = new UserPermission();
		List<OperPermission> operPermission = permissionService
				.queryOperPermission();
		if (userManagerService.isSuperManager(userId)) {// 超级管理员，具有全部的数据权限和操作权限
			try {
				List<ClusterInfo> clusterInfo = sysMonitorService
						.queryClusterInfoByIds("");
				userPermision.setOperPermissionList(operPermission);
				userPermision
						.setUnOperPermissionList(new ArrayList<OperPermission>());
				userPermision.setClusterList(clusterInfo);
			} catch (PaasWebException ex) {
				// throw new PaasWebException("control","查询集群异常");
				throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
			}
		} else {
			List<RolePermissionRelation> userPermissionList = userManagerService
					.queryUserPermission(userId);
			List<OperPermission> operPermissionList = new ArrayList<OperPermission>();
			String dataPermission = "";
			for (RolePermissionRelation rolePermission : userPermissionList) {
				if (null != rolePermission) {
					if (rolePermission.getPermissionType() == 1) { // 操作权限
						for (OperPermission per : operPermission) {
							if (rolePermission.getPermissionId().equals(
									per.getId())) {
								if (!isAdd(operPermissionList, per.getId())) {
									operPermissionList.add(per);
								}
								break;
							}
						}
					} else {
						if (dataPermission.equals("")) {
							dataPermission += rolePermission.getPermissionId();
						} else {
							dataPermission += ","
									+ rolePermission.getPermissionId();
						}
					}
				}
			}
			try {
				List<ClusterInfo> clusterInfo = null;
				if (roleType == 1) { // 普通管理员
					clusterInfo = sysMonitorService
							.queryClusterAndAppGroupsByappGroupIds(
									dataPermission, "createTime");
				} else if (roleType == 2) { // 普通用户
					clusterInfo = sysMonitorService
							.queryClusterAndAppGroupsByappIds(dataPermission,
									"createTime");
				}
				userPermision.setUnOperPermissionList(findUnOperPermision(
						operPermissionList, operPermission));
				userPermision.setOperPermissionList(operPermissionList);
				userPermision.setClusterList(clusterInfo);
			} catch (PaasWebException ex) {
				// throw new PaasWebException("control","查询集群异常");
				throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
			}
		}
		return userPermision;
	}
	*/

	/**
	 * 判断权限是否已经添加到权限列表里面
	 * 
	 * @param operPermissionList
	 *            权限列表
	 * @param id
	 *            权限id
	 * @return true 已经添加 false 没有添加
	 */
	private boolean isAdd(List<OperPermission> operPermissionList, String id) {
		for (OperPermission operPermission : operPermissionList) {
			if (operPermission.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取用户没有的操作权限
	 * 
	 * @param operPermissionList
	 *            用户拥有的操作权限
	 * @param allOperPerList
	 *            系统中所有的操作权限
	 * @return List OperPermission 用户没有的操作权限集合
	 */
	private List<OperPermission> findUnOperPermision(
			List<OperPermission> operPermissionList,
			List<OperPermission> allOperPerList) {
		Log.info("查询没有赋予的权限");
		if (operPermissionList == null || operPermissionList.size() == 0) {
			return allOperPerList;
		}
		List<OperPermission> unOperPerList = new ArrayList<OperPermission>();
		for (OperPermission operPer : allOperPerList) {
			boolean isFind = false;
			for (OperPermission per : operPermissionList) {
				if (operPer.getId().equals(per.getId())) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				unOperPerList.add(operPer);
			}
		}
		Log.info("查询成功" + unOperPerList);
		return unOperPerList;
	}

	/**
	 * 增加用户
	 */
	public void addUser() {
		try {
			Log.info("新增用户");
			
			User user = new User();
			user.setLoginName(loginName);
			user.setUserName(userName);
			
			User userInfo = userManagerService.queryUserByName(user.getLoginName());
			if (userInfo != null) {
				this.sendFailResult(Constants.CREATE_USER_EXIST_ERROR, "用户账号已存在");
				return;
			}
			user.setPassword(password.toUpperCase());
			User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
			user.setCreateBy(tempUser.getId());
			userManagerService.addUser(user);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("增加用户异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	
	/***
	 * 查询当前环境
	 */
	public void queryCurrenEvn(){
		
		try {
			Log.info("查询当前环境");
			Authority authority = new Authority();
			authority.setCurrenEvn(!(boolean) getSessionMap().get("currentContext"));
			authority.setCurrenRole(getSessionMap().get("selectedOpertype").toString());
			sendSuccessReslult(authority);
		} catch (PaasWebException ex) {
			logger.error("查询当前环境异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 删除用户
	 */
	public void deleteUser() {
		try {
			Log.info("删除用户" + id);
			List<UserRoleRelation> userRoleList = userRoleService.queryByUser(id);
			if (userRoleList != null && userRoleList.size() > 0) {
				sendFailResult(Constants.QUERY_USER_ROLE_ERROR, "该用户已被授予角色,不能删除");
				return;
			}
			userManagerService.deleteUser(id);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("删除用户异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 除了admin外，没有用户可以修改超级管理员用户信息
	 */
	public void queryCurrentUser() {
		try {
			Log.info("查询用户" + id);
			User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
			if (id == 1 && id != tempUser.getId()) {
				sendFailReslutl("当前用户不能修改超级管理员用户");
				return;
			}
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("查询用户异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 通过用户id查询用户信息
	 * 
	 * @return String 返回成功
	 */
	public String queryUserById() {
		try {
			Log.info("根据id查询用户" + id);
			user = userManagerService.queryUserById(id);
		} catch (PaasWebException ex) {
			logger.error("通过id查询用户异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorMsg = ex.toString();
		}
		return SUCCESS;
	}

	/**
	 * 修改用户信息.
	 */
	public void updateUser() {
		try {
			Log.info("修改用户");
			
			User user = new User();
			user.setId(id);
			user.setLoginName(loginName);
			user.setUserName(userName);
			
			User userInfo = userManagerService.queryUserByName(user.getLoginName());
			if (userInfo != null && !userInfo.getId().equals(user.getId())) {
				this.sendFailResult(Constants.UPDATE_USER_EXIST_ERROR, "用户账号已存在");
				return;
			}
			user.setPassword(password.toUpperCase());
			userManagerService.updateUser(user);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("更改用户密码异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 更改用户密码.
	 */
	public void updatePassword() {
		try {
			Log.info("修改密码");
			User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
			User userInfo = userManagerService.queryUserById(tempUser.getId());
			if (!userInfo.getPassword().equals(oldPassword.toUpperCase())) {
				this.sendFailResult(Constants.UPDATE_USER_PASSWORD_ERROR, "您输入的旧密码错误，请重新输入！");
				return;
			}
			userInfo.setPassword(password.toUpperCase());
			userManagerService.updateUser(userInfo);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("更改用户密码异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 用户注销.
	 * 
	 * @return the string 返回成功
	 */
	public String logout() {
		Log.info("注销登录");
		if (null == getSessionMap().get("user_name")) {
			return SUCCESS;
		}
		getSession().removeAttribute(Constants.CURRENT_USER);
		getSessionMap().clear();
		return SUCCESS;
	}

	/**
	 * 用户管理 - 操作 - 授予角色.
	 * 用户授权更改用户角色.
	 */
	public void updateUserRole() {
		try {
			Log.info("修改用户权限" + id);
			if (selectNode == null || selectNode.equals("")) {
				userRoleService.deleteByUser(id);
				sendSuccessReslutl();
				return;
			}
			List<UserRoleRelation> userRoleList = userRoleService.queryByUser(id);
			// 重新选中的角色
			String[] nodeArray = selectNode.split(",");
			
			// 当前用户所属的操作类型（开发-1、测试-2）
			String currentUserOperType = rolePerRelationService.queryOperTypeByUserId(id);
			// 循环比较重新选中的角色所属的操作类型，与当前用户的操作类型是否匹配
			if(currentUserOperType != null && !"".equals(currentUserOperType)){
				for(String node : nodeArray){
					String currentRoleOperType = rolePerRelationService.queryOperTypeByRoleId(Long.parseLong(node));
					if(currentRoleOperType != null && !"".equals(currentRoleOperType)){
						if(!currentUserOperType.equals(currentRoleOperType)){
							sendFailResult(Constants.QUERY_ROLE_PERMISSION_ERROR, "用户不能分配既是开发又是测试角色");
							return;
						}
					}
				}
				TreeSet<String> checkRepeatOperType = new TreeSet<String>();
				for(String node : nodeArray){
					String currentRoleOperType = rolePerRelationService.queryOperTypeByRoleId(Long.parseLong(node));
					if(currentRoleOperType != null && !"".equals(currentRoleOperType)){
						checkRepeatOperType.add(currentRoleOperType); // TreeSet存储重复的值只会存一个
						if(checkRepeatOperType.size() > 1){
							sendFailResult(Constants.QUERY_ROLE_PERMISSION_ERROR, "用户不能分配既是开发又是测试角色");
							return;
						}
					}
				}
			} else {
				TreeSet<String> checkRepeatOperType = new TreeSet<String>();
				for(String node : nodeArray){
					String currentRoleOperType = rolePerRelationService.queryOperTypeByRoleId(Long.parseLong(node));
					if(currentRoleOperType != null && !"".equals(currentRoleOperType)){
						checkRepeatOperType.add(currentRoleOperType); // TreeSet存储重复的值只会存一个
						if(checkRepeatOperType.size() > 1){
							sendFailResult(Constants.QUERY_ROLE_PERMISSION_ERROR, "用户不能分配既是开发又是测试角色");
							return;
						}
					}
				}
			}
			
			List<UserRoleRelation> deleteRole = findDeleteRole(nodeArray, userRoleList);
			List<UserRoleRelation> addRole = findAddRole(nodeArray, userRoleList);
			userRoleService.updateUserRole(deleteRole, addRole);
			getSession().setAttribute("userId", id);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("更改用户角色异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
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
			List<UserRoleRelation> userRoleList) {
		Log.info("查询新增角色");
		UserRoleRelation addUserRole = null;
		List<UserRoleRelation> addUserRoleList = new ArrayList<UserRoleRelation>();
		for (String node : nodeArray) {
			boolean isFind = false;
			for (UserRoleRelation userRole : userRoleList) {
				// String roleId = userRole.getRoleId();
				Long roleId = userRole.getRoleId();
				// if(roleId.equals(node)){
				if (roleId.equals(Long.valueOf(node))) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				addUserRole = new UserRoleRelation();
				// addUserRole.setRoleId(node);
				addUserRole.setRoleId(Long.valueOf(node));
				addUserRole.setUserId(id);
				addUserRoleList.add(addUserRole);
			}
		}
		return addUserRoleList;
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
			List<UserRoleRelation> userRoleList) {
		Log.info("查询删除角色");
		UserRoleRelation deleteUserRole = null;
		List<UserRoleRelation> deleteUserRoleList = new ArrayList<UserRoleRelation>();
		for (UserRoleRelation userRole : userRoleList) {
			// String roleId = userRole.getRoleId();
			Long roleId = userRole.getRoleId();
			boolean isFind = false;
			for (String node : nodeArray) {
				// if(roleId.equals(node)){
				if (roleId.equals(Long.valueOf(node))) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				deleteUserRole = new UserRoleRelation();
				deleteUserRole.setRoleId(roleId);
				deleteUserRole.setUserId(id);
				deleteUserRoleList.add(deleteUserRole);
			}
		}
		return deleteUserRoleList;
	}

	/**
	 * 查询用户角色的树形结构.
	 */
	public void queryUserRoleTree() {
		try {
			Log.info("查询用户角色树形结构");
			User tempUser = (User) getSessionMap().get(Constants.CURRENT_USER);
			List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
			if (userManagerService.isSuperManager(tempUser.getId())) {
				treeNodeList = roleService.queryRoleTreeNode();
			} else {
				treeNodeList = roleService.queryRoleTreeNode(tempUser.getId());
			}
			List<UserRoleRelation> userRoleList = userRoleService
					.queryByUser(id);
			boolean isSuperChecked = false;
			for (TreeNode treeNode : treeNodeList) {
				treeNode.setChecked(isFindRole(Long.valueOf(treeNode.getId()),
						userRoleList));
				// 如果是超级管理员用户不允许重新授权
				if (treeNode.isChecked() == true
						&& treeNode.getId().equals("1")
						&& userRoleList.get(0).getRoleId() == 1
						&& userRoleList.get(0).getUserId() == 1) {
					isSuperChecked = true;
				}
				if (isSuperChecked) {
					treeNode.setChkDisabled(true);
				}
				if (treeNode.isChecked() == true
						&& treeNode.getId().equals("1")) {
					treeNode.setChkDisabled(false);
				}
			}
			sortStringMethod(treeNodeList);
			this.renderText(JackJson.fromObjectToJson(treeNodeList));
		} catch (PaasWebException ex) {
			logger.error("查询用户角色的树形结构异常" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	/**
	 * 按照List中的某个String类型的属性进行排序；
	 * 这里需要按角色名称（字典顺序）排序
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private static void sortStringMethod(List<TreeNode> list) {
		Collections.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object obj1, Object obj2) {
				TreeNode node1 = (TreeNode) obj1;
				TreeNode node2 = (TreeNode) obj2;
				return node1.getName().compareTo(node2.getName());
			}
		});
	}
	
	/**
	 * 根据userId查询用户所属操作类型（1-开发，2-测试，3-运维）
	 * @return
	 */
	public void queryUserOperTypeByUserId() {
		try {
			String userOperType = rolePerRelationService.queryOperTypeByUserId(id);
			this.renderText(JackJson.fromObjectToJson(userOperType));
		} catch (PaasWebException e) {
			logger.error("用户所属操作类型异常 ", e);
			this.sendFailResult2jqGrid(e.getKey(), e.toString());
		}
		
	}

	/**
	 * 查找角色是否存在.
	 * 
	 * @param roleId
	 *            角色id
	 * @param userRoleList
	 *            用户拥有角色集合
	 * @return true 查找到 false没有查找到
	 */
	private boolean isFindRole(Long roleId, List<UserRoleRelation> userRoleList) {
		for (UserRoleRelation userRole : userRoleList) {
			if (roleId.equals(userRole.getRoleId())) {
				return true;
			}
		}
		return false;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getMail() {
		return mail;
	}

	public String getPassword() {
		return password;
	}

	public User getUser() {
		return user;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public String getUserName() {
		return userName;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public UserRoleRelationService getUserRoleService() {
		return userRoleService;
	}

	public void setUserRoleService(UserRoleRelationService userRoleService) {
		this.userRoleService = userRoleService;
	}

	public String getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(String selectNode) {
		this.selectNode = selectNode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getDeveName() {
		return deveName;
	}

	public void setDeveName(String deveName) {
		this.deveName = deveName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

//	public String getLocalIp() {
//		return localIp;
//	}
//
//	public void setLocalIp(String localIp) {
//		this.localIp = localIp;
//	}
	
}
