package com.cmsz.paas.web.group4a.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiainfo.uap.util.des.EncryptInterface;
import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.log.OperationLogUtil;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.group4a.service.User4AService;
import com.cmsz.paas.web.group4a.tool.AESFontUtil;
import com.cmsz.paas.web.group4a.tool.WsUtils;
import com.cmsz.paas.web.group4a.tool.XmlJsonUtil;
import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.permission.service.PermissionService;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.cmsz.paas.web.role.service.RoleService;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.entity.UserPermission;
import com.cmsz.paas.web.user.entity.UserRoleRelation;
import com.cmsz.paas.web.user.service.UserManagerService;
import com.cmsz.paas.web.user.service.UserRoleRelationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 用户管理Action类
 * 
 * @author ccl
 */
public class Group4aAction extends AbstractAction implements SessionAware {
	/**
	 * 随机序列号
	 */
	private static final long serialVersionUID = -7720978896577605824L;

	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory
			.getLogger(Group4aAction.class);

	@Autowired
	private WsUtils wsUtils;

	@Autowired
	private User4AService user4AService;

	/** 用户管理service对象 . */
	@Autowired
	private UserManagerService userManagerService;

	/** 权限service对象. */
	@Autowired
	private PermissionService permissionService;

	/** 用户与角色关联service对象. */
	@Autowired
	private UserRoleRelationService userRoleService;

	@Autowired
	private RolePerRelationService rolePerRelationService; // 角色权限关系

	/** 角色service对象. */
	@Autowired
	private RoleService roleService;

	// struts2 注入
	private Map<String, Object> session;

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

	/** 用户信息. */
	private User user;

	/** 错误消息. */
	private String errorMsg;

	/***
	 * 判断是否登录页面是集团4A登录
	 * 
	 * @param user
	 * @return
	 */
	@UnLogging
	public void is4aLogin() {
		try {
			sendSuccessReslult(PropertiesUtil.getValue("is4aLogin"));
		} catch (Exception e) {
			// 失败
			logger.error("is4aLogin", e.toString());
			sendFailReslutl("获取登录状态失败");
		}
	}

	/***
	 * 判断4A是否为应急状态
	 * 
	 * @param user
	 * @return
	 */
	@UnLogging
	public void isEmergency() {
		try {
			sendSuccessReslult(PropertiesUtil.getValue("isEmergency"));
		} catch (Exception e) {
			// 失败
			logger.error("isEmergency", e.toString());
			sendFailReslutl("获取登录状态失败!");
		}
	}

	private JSONObject getPubHead(Object sId) throws JSONException {
		JSONObject headReq = new JSONObject();
		headReq.put("client_ip", getIpAddr(Struts2.getRequest()));
		headReq.put("CODE", "");
		headReq.put("SID", sId);
		headReq.put("TIMESTAMP",
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		headReq.put("SERVICEID", PropertiesUtil.getValue("serviceId"));
		return headReq;
	}

	/***
	 * 获取集团4A短信验证码
	 * 
	 * @param map
	 * @return
	 */
	public void getNotekey() {
		try {
			HttpSession session = Struts2.getRequest().getSession();
			// String mainAcct = map.get("mainAcct");// 4A主账号
			// String loginPwd = map.get("loginPwd");
			String loginPwd = AESFontUtil.desEncrypt(password);
			JSONObject head = getPubHead("");
			JSONObject body = new JSONObject();
			
			body.put("MAINACCT", loginName);
			body.put("LOGINPWD", EncryptInterface.desEncryptData(loginPwd));
			JSONObject requestJson = new JSONObject();
			requestJson.put("HEAD", head);
			requestJson.put("BODY", body);
			String requestXml = XmlJsonUtil.jsonToXml(requestJson.toString(),
					"REQUEST");
			logger.info("MainAcctAuthenHQService  4a account check  request params"
					+ requestXml);
			// 4A静态密码验证接口
			 String responseXml =
			 wsUtils.SynService(PropertiesUtil.getValue("authLoginUrl")
			 + "/MainAcctAuthenHQService", "MainAcctAuthenHQService",
			 new Object[] { requestXml });
//			String responseXml = "<?xml version='1.0' encoding= 'UTF-8'?><RESPONSE><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><MAINACCT>chencl</MAINACCT><SUBACCTS><SUBACCT>cll</SUBACCT></SUBACCTS><MOBILE></MOBILE><ERRCODE>错误代码</ERRCODE><ERRDESC>错误描述</ERRDESC></BODY></RESPONSE>";
			/*
			 * String responseXml =
			 * "<?xml version=\"1.0\" encoding=\"UTF-8\"?><USERMODIFYRSP><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP>"
			 * +
			 * "<SERVICEID>应用标识</SERVICEID></HEAD><BODY><MAINACCT>4a_test</MAINACCT>"
			 * +
			 * "<SUBACCTS><SUBACCT>jiayouzhi</SUBACCT><SUBACCT>jiayouzhi1</SUBACCT>"
			 * +
			 * "</SUBACCTS><MOBILE>15017900431</MOBILE></BODY></USERMODIFYRSP>";
			 */
			logger.info("MainAcctAuthenHQService  4a account check  response params"
					+ responseXml);
			// 处理返回的数据
			JSONObject repJson = XmlJsonUtil.xmlToJsonObj(responseXml);
			JSONObject rep = repJson.getJSONObject("BODY");
			if (rep.containsKey("KEY")) {// 4A静态密码验证流程校验返回失败
				sendFailReslutl(rep.getString("ERRDESC"));
				return;
			} else {// 返回成功
				Object obj = rep.get("SUBACCTS");
				if (obj instanceof JSONArray) {
					JSONArray subaccts = rep.getJSONArray("SUBACCTS");// 返回的从账号列表
					String subaccct = "";
					for (int i = 0; i < subaccts.size(); i++) {
						subaccct += subaccts.getString(i) + ",";
					}
					session.setAttribute("subacct",
							subaccct.replaceAll(",$", ""));// 将返回的从账号列表保存在SESSION
				} else {
					JSONObject subaccts = rep.getJSONObject("SUBACCTS");// 返回的从账号
					session.setAttribute("subacct",
							subaccts.getString("SUBACCT"));// 将返回的从账号列表保存在SESSION
				}
				String mainacct = rep.getString("MAINACCT");// 登录返回的主账号，获取密钥接口需要
				session.setAttribute("mainacct", mainacct);// 校验密钥接口时需要
				String mobile = rep.getString("MOBILE");// 4A主账号返回的手机号码,获取密钥接口需要
				JSONObject noteKeyBody = new JSONObject();
				noteKeyBody.put("OPERATORID", mainacct);
				noteKeyBody.put("OPERATORIP", getIpAddr(Struts2.getRequest()));
				noteKeyBody.put("MOBILE", mobile);
				JSONObject noteKeyObj = new JSONObject();
				noteKeyObj.put("HEAD", head);
				noteKeyObj.put("BODY", noteKeyBody);
				String noteKeyRequestXml = XmlJsonUtil.jsonToXml(
						noteKeyObj.toString(), "CREATENOTEKEY");
				logger.info("CreateNotekeyService  request params"
						+ noteKeyRequestXml);
				// 密钥生成接口
				 String noteKeyResponseXml = wsUtils.SynService(
				 PropertiesUtil.getValue("authLoginUrl") +
				 "/CreateNotekeyService",
				 "CreateNotekeyService",
				 new Object[] { noteKeyRequestXml });
//				String noteKeyResponseXml = "<?xml version='1.0' encoding= 'UTF-8'?><CREATENOTEKEY><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><RSP>0</RSP><ERRDESC>错误描述</ERRDESC><MSGID>22</MSGID></BODY></CREATENOTEKEY>";
				/*
				 * String noteKeyResponseXml =
				 * "<?xml version='1.0' encoding='UTF-8'?><CREATENOTEKEY>" +
				 * "<HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID>"
				 * +
				 * "</HEAD><BODY><RSP>0</RSP><ERRDESC>错误描述</ERRDESC><MSGID>4a12345</MSGID></BODY></CREATENOTEKEY>"
				 * ;
				 */

				logger.info("CreateNotekeyService  response params"
						+ noteKeyResponseXml);
				JSONObject noteKeyRep = XmlJsonUtil
						.xmlToJsonObj(noteKeyResponseXml);
				JSONObject noteKeyRepBody = noteKeyRep.getJSONObject("BODY");
				String rsp = noteKeyRepBody.getString("RSP");// 密钥验证验证结果标志
				if ("0".equals(rsp)) {// 密钥成功
					String msgId = noteKeyRepBody.getString("MSGID");// 消息序号，校验验证码接口时用到
					session.setAttribute("msgId", msgId);
					sendSuccessReslult("success");
					// OperationLogUtil.ipAddress = getRemoteAddr();
					// return SUCCESS;
				} else {// 密钥生成失败
					sendFailReslutl(noteKeyRepBody.getString("ERRDESC"));
				}
			}
		} catch (Exception e) {
			logger.error("getNotekey --- 获取4A短信密钥异常" + e.toString());
			sendFailReslutl("获取4A验证码错误");
		}
	}

	/***
	 * 4A短信密钥验证
	 * 
	 * @param map
	 * @return
	 */
	public void checkNotekey() {
		try {
			HttpSession session = Struts2.getRequest().getSession();
			// String noteKey = map.get("noteKey");
			// noteKey = AESFontUtil.desEncrypt(noteKey);
			Object msgId = session.getAttribute("msgId") == null ? "" : session
					.getAttribute("msgId");
			JSONObject head = getPubHead(msgId);
			JSONObject body = new JSONObject();
			body.put("OPERATORID", session.getAttribute("mainacct"));
			body.put("OPERATORIP", getIpAddr(Struts2.getRequest()));
			body.put("NOTEKEY", EncryptInterface.desEncryptData(checkCode));
			JSONObject jo = new JSONObject();
			jo.put("HEAD", head);
			jo.put("BODY", body);
			String requestXml = XmlJsonUtil.jsonToXml(jo.toString(),
					"NOTEKEYVALIDATE");
			logger.info("NoteKeyValidate  request params" + requestXml);
			// 4A短信密钥校验接口
			 String responseXml =
			 wsUtils.SynService(PropertiesUtil.getValue("authLoginUrl")
			 + "/NoteKeyValidate", "NoteKeyValidate",
			 new Object[] { requestXml });
//			String responseXml = "<?xml version='1.0' encoding= 'UTF-8'?><NOTEKEYVALIDATE><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><RSP>0</RSP><ERRDESC>错误描述</ERRDESC></BODY></NOTEKEYVALIDATE>";

			/*
			 * String responseXml =
			 * "<?xml version='1.0' encoding= 'UTF-8'?><NOTEKEYVALIDATE><HEAD><CODE>消息标志</CODE>"
			 * +
			 * "<SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD>"
			 * +
			 * "<BODY><RSP>0</RSP><ERRDESC>错误描述</ERRDESC></BODY></NOTEKEYVALIDATE>"
			 * ;
			 */
			logger.info("NoteKeyValidate  response params" + responseXml);
			JSONObject repJson = XmlJsonUtil.xmlToJsonObj(responseXml);
			JSONObject repBody = repJson.getJSONObject("BODY");
			String rsp = repBody.getString("RSP");
			if ("0".equals(rsp)) {// 校验成功 ,返回从账号列表
				session.setAttribute("4aLoginFlag", "1");// 标识是否已经完成4A认证
				if ("true".equals(PropertiesUtil.getValue("isResetPwd"))) {
					Map<String, Object> uMap = new HashMap<String, Object>();
					uMap.put("userName", session.getAttribute("mainacct"));
					uMap.put("updateTime", new Date());
					// List<User4AEntity> uList = user4AService.findByMap(uMap);
					// if (uList == null || uList.size() == 0) {
					// sendFailReslutl("您的密码已过期，请修改密码后再登录！！");
					// return;
					// }
				}
				sendSuccessReslult(session.getAttribute("subacct").toString());
			} else {
				sendFailReslutl(repBody.getString("ERRDESC"));
			}
		} catch (Exception e) {
			logger.error("checkNotekey --- 校验短信密钥异常" + e.toString());
			sendFailReslutl("checkNotekey错误");
		}
	}

	/***
	 * 4A应用自登录接口
	 * 
	 * @param map
	 * @return
	 */
	public String selfLogin() {
		try {

			HttpSession session = Struts2.getRequest().getSession();
			String account = (String) session.getAttribute("subacct");
			String[] accounts = account.split(",");
			boolean bool1 = false;
			if (accounts.length > 0) {
				for (String str : accounts) {
					if (str.equals(userName)) {
						bool1 = true;
					}
				}
			}
			if (!bool1) {
				setLoginMessage("该主账号下没有此从账号！");
				return "resultMsg";
			}
			String loginFlag = (String) session.getAttribute("4aLoginFlag");
			if ("1".equals(loginFlag)) {
				User user = userManagerService.queryUserByName(userName);
				if (user == null) {
					setLoginMessage("用户名或密码错误，请重试！");
					return "resultMsg";
				}

				// 获取角色信息
				Role role = null;
				List<UserRoleRelation> userRoleRelationList = userRoleService
						.queryByUser(user.getId());
				if (userRoleRelationList.size() > 0) {
					for (UserRoleRelation userRoleRelation : userRoleRelationList) {
						role = roleService.queryRole(userRoleRelation
								.getRoleId());
					}
				} else {
					setLoginMessage("该用户没有关联角色，无法登录");
					return "resultMsg";
				}

				UserPermission userPermision = getUserPermission(user.getId(),
						role.getType());
				if ((null == userPermision.getOperPermissionList() || 0 == userPermision
						.getOperPermissionList().size()) && role.getId() != 1) {
					setLoginMessage("该用户未被授予操作权限");
					return "resultMsg";
				} else {
					if ((null == userPermision.getRolePermissionList() || 0 == userPermision
							.getRolePermissionList().size())
							&& role.getId() != 1) {
						setLoginMessage("该用户未被授予应用权限");
						return "resultMsg";
					}
				}

				// 问题描述：登录之前和登录之后session的id值没有变化；
				// 问题解决方法：(实现SessionAware接口)在验证登录用户名密码成功后，把session失效，新生成一个session；
				// 把当前session失效
				// ((SessionMap<String, Object>) session).invalidate();
				// // 新生成一个session
				// this.session = ActionContext.getContext().getSession();

				// session.put("AUTHENTICATED", new Boolean(true));

				// getSessionMap().put("userPermision", userPermision);
				// getSessionMap().put("user", user);
				// getSessionMap().put("role", role);
				// 下面的存放方式和上面的效果一样，都实现了存放session值
				// session.put("userPermision", userPermision);
				// session.put("user", user);
				// session.put("role", role);
				user.setSessionId(ServletActionContext.getRequest()
						.getSession().getId());
				userManagerService.updateSessionId(user);
				// 通过4A登录方式和系统原来登录方式，都共用该方法
				loginValidation(userPermision, user, role);

				getSessionMap().put("masterAccount", ""); // 不通过4A登录，也需要保存这个参数
				session.removeAttribute("4aLoginFlag");// 删除校验成功标识
				OperationLogUtil.ipAddress = getRemoteAddr();
				setLoginMessage("success");
				return SUCCESS;
			}
			setLoginMessage("请先通过4A短信认证再登录！");
			return "resultMsg";
		} catch (Exception e) {
			logger.error("4A应用自登录错误" + e.toString());
			setLoginMessage("4A应用自登录错误");
			return "resultMsg";
		}
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
		// 获取用户权限
		UserPermission userPermision = new UserPermission();
		// 获取用户权限其中的【操作权限】
		List<OperPermission> operPermission = permissionService
				.queryOperPermission();

		if (userManagerService.isSuperManager(userId)) { // 超级管理员，具有全部的数据权限和操作权限
			try {
				// 全部
				List<RolePermissionRelation> appInfo = rolePerRelationService
						.queryRolePermissionRelation();
				// 超级管理员应用权限
				List<RolePermissionRelation> adminAppPer = new ArrayList<RolePermissionRelation>();
				for (RolePermissionRelation relation : appInfo) {
					if (relation.getRoleId() == Constants.ROLE_ID) {
						adminAppPer.add(relation);
					}
				}
				userPermision.setRolePermissionList(adminAppPer); // 【应用权限】
				// userPermision.setRolePermissionList(appInfo); //【应用权限】
				userPermision.setOperPermissionList(operPermission); // 【操作权限】
				userPermision
						.setUnOperPermissionList(new ArrayList<OperPermission>()); // 【没有拥有的权限】
			} catch (PaasWebException ex) {
				throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
			}
		} else { // 普通用户
			List<RolePermissionRelation> userPermissionList = userManagerService
					.queryUserPermission(userId);
			List<OperPermission> operPermissionList = new ArrayList<OperPermission>();
			List<RolePermissionRelation> appPermissionList = new ArrayList<RolePermissionRelation>();
			for (RolePermissionRelation rolePermission : userPermissionList) {
				if (null != rolePermission) {
					// 权限类型，1表示操作权限，2表示数据权限，如应用
					if (rolePermission.getPermissionType() == Constants.ROLE_OPER_PERMISSION_TYPE) {
						for (OperPermission per : operPermission) {
							if (rolePermission.getPermissionId().equals(
									per.getId())) {
								if (!isAdd(operPermissionList, per.getId())) {
									operPermissionList.add(per);
								}
								break;
							}
						}
					} else if (rolePermission.getPermissionType() == Constants.ROLE_APP_PERMISSION_TYPE) {
						appPermissionList.add(rolePermission);
					}
				}
			}
			userPermision.setUnOperPermissionList(findUnOperPermision(
					operPermissionList, operPermission));
			sortOperMenuMethod(operPermissionList);
			userPermision.setOperPermissionList(operPermissionList);
			userPermision.setRolePermissionList(appPermissionList);
		}
		return userPermision;
	}

	/**
	 * 普通用户左侧菜单栏排序
	 * 
	 * @param list
	 */
	private static void sortOperMenuMethod(List<OperPermission> list) {
		Collections.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object obj1, Object obj2) {
				OperPermission operMenu1 = (OperPermission) obj1;
				OperPermission operMenu2 = (OperPermission) obj2;
				if (Integer.parseInt(operMenu1.getId()) > Integer
						.parseInt(operMenu2.getId())) {
					return 1;
				} else if (Integer.parseInt(operMenu1.getId()) == Integer
						.parseInt(operMenu2.getId())) {
					return 0;
				} else {
					return -1;
				}
			}
		});
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
	 * 获取从帐号列表
	 * 
	 * @throws Exception
	 */
	public void queryAccount() {
		try {
			HttpSession session = Struts2.getRequest().getSession();
			List<SelectType> list = new ArrayList<SelectType>();
			if ("1".equals(session.getAttribute("4aLoginFlag"))) {
				String[] subacct = session.getAttribute("subacct").toString()
						.split(",");
				for (int i = 0; i < subacct.length; i++) {
					SelectType selectType = new SelectType();
					selectType.setText(subacct[i]);
					selectType.setValue(subacct[i]);
					list.add(selectType);
				}

				JSONArray jsonArray = JSONArray.fromObject(list);
				logger.info("获取从帐号列表完成：" + jsonArray.toString());
				sendSuccessReslult(jsonArray.toString());
			} else {
				logger.error("获取从帐号列表错误!");
				sendFailReslutl("获取从帐号列表错误!");
			}
		} catch (Exception e) {
			logger.error("获取从帐号列表错误：" + e.toString());
			sendFailReslutl("获取从帐号列表错误！");
		}
	}

	/***
	 * 集团4A应急登录
	 * 
	 * @param map
	 * @return
	 */
	public void emergency4ALogin() {
		try {
			HttpSession session = Struts2.getRequest().getSession();
			JSONObject head = getPubHead("");
			JSONObject body = new JSONObject();
			body.put("MAINACCT", loginName);
			String password1 = AESFontUtil.desEncrypt(password);
			body.put("LOGINPWD", EncryptInterface.desEncryptData(password1));
			JSONObject requestJson = new JSONObject();
			requestJson.put("HEAD", head);
			requestJson.put("BODY", body);
			String requestXml = XmlJsonUtil.jsonToXml(requestJson.toString(),
					"REQUEST");
			logger.info("MainAcctAuthenHQService  4a account check  request params"
					+ requestXml);
			// 4A静态密码验证接口
			 String responseXml = wsUtils.SynService(
			 PropertiesUtil.getValue("authLoginUrl")
			 + "/MainAcctAuthenHQService",
			 "MainAcctAuthenHQService", new Object[] { requestXml });
//			String responseXml = "<?xml version='1.0' encoding= 'UTF-8'?><RESPONSE><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><MAINACCT>chencl</MAINACCT><SUBACCTS><SUBACCT>cll</SUBACCT></SUBACCTS><MOBILE></MOBILE><ERRCODE>错误代码</ERRCODE><ERRDESC>错误描述</ERRDESC></BODY></RESPONSE>";
			/*
			 * String responseXml =
			 * "<?xml version=\"1.0\" encoding=\"UTF-8\"?><USERMODIFYRSP><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP>"
			 * +
			 * "<SERVICEID>应用标识</SERVICEID></HEAD><BODY><MAINACCT>4a_test</MAINACCT>"
			 * +
			 * "<SUBACCTS><SUBACCT>jiayouzhi</SUBACCT><SUBACCT>jiayouzhi1</SUBACCT>"
			 * +
			 * "</SUBACCTS><MOBILE>15017900431</MOBILE></BODY></USERMODIFYRSP>";
			 */
			logger.info("MainAcctAuthenHQService  4a account check  response params"
					+ responseXml);
			// 处理返回的数据
			JSONObject repJson = XmlJsonUtil.xmlToJsonObj(responseXml);
			JSONObject rep = repJson.getJSONObject("BODY");
			if (rep.containsKey("KEY")) {// 4A静态密码验证流程校验返回失败
				sendFailReslutl(rep.getString("ERRDESC"));
			} else {// 返回成功
				Object obj = rep.get("SUBACCTS");
				if (obj instanceof JSONArray) {
					JSONArray subaccts = rep.getJSONArray("SUBACCTS");// 返回的从账号列表
					String subaccct = "";
					for (int i = 0; i < subaccts.size(); i++) {
						subaccct += subaccts.getString(i) + ",";
					}
					session.setAttribute("subacct",
							subaccct.replaceAll(",$", ""));// 将返回的从账号列表保存在SESSION
				} else {
					JSONObject subaccts = rep.getJSONObject("SUBACCTS");// 返回的从账号
					session.setAttribute("subacct",
							subaccts.getString("SUBACCT"));// 将返回的从账号列表保存在SESSION
				}
				if ("true".equals(PropertiesUtil.getValue("isEmergency"))) {// 应急情况下不用短信
					session.setAttribute("4aLoginFlag", "1");// 标识是否已经完成4A认证
				}
				if ("true".equals(PropertiesUtil.getValue("isResetPwd"))) {
					Map<String, Object> uMap = new HashMap<String, Object>();
					uMap.put("userName", loginName);
					uMap.put("updateTime", new Date());
					// List<User4AEntity> uList = user4AService.findByMap(uMap);
					// if (uList == null || uList.size() == 0) {
					// sendFailReslutl("您的密码已过期，请修改密码后再登录！！");
					// return;
					// }
				}
				sendSuccessReslult(session.getAttribute("subacct").toString());
			}
		} catch (Exception e) {
			logger.error("4A账号密码验证错误" + e.toString());
			sendFailReslutl("4A账号密码验证错误");
		}
	}

	/***
	 * 修改集团4A密码
	 * 
	 * @param map
	 * @return
	 */
	public void update4APwd() {
		try {
			// String mainAcct = map.get("mainAcct");// 4A主账号
			// String loginOldPwd = map.get("loginOldPwd");// 4A旧密码
			// String loginNewPwd = map.get("loginNewPwd");// 4A新密码
			// String loginNewPwdConfirm = map.get("loginNewPwdConfirm");//
			// 新密码确认
			// if (!password.equals(oldPassword)) {
			// sendFailReslutl("两次密码输入不一致！");
			// return;
			// }
			String loginOldPwd = AESFontUtil.desEncrypt(oldPassword);
			String loginNewPwd = AESFontUtil.desEncrypt(password);
			JSONObject head = getPubHead("");
			JSONObject body = new JSONObject();
			body.put("MAINACCT", loginName);
			body.put("OLDPWD", EncryptInterface.desEncryptData(loginOldPwd));
			body.put("NEWPWD", EncryptInterface.desEncryptData(loginNewPwd));
			JSONObject requestJson = new JSONObject();
			requestJson.put("HEAD", head);
			requestJson.put("BODY", body);
			String requestXml = XmlJsonUtil.jsonToXml(requestJson.toString(),
					"REQUEST");
			logger.info("ModifyMainAcctPwdServices  4a reset pwd  request params"
					+ requestXml);
			// 4A修改密码服务
			 String responseXml = wsUtils.SynService(
			 PropertiesUtil.getValue("authLoginUrl")
			 + "/ModifyMainAcctPwdServices",
			 "ModifyMainAcctPwdServices", new Object[] { requestXml });
//			String responseXml = "<?xml version='1.0' encoding= 'UTF-8'?><RESPONSE><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><KEY>关键标志</KEY><ERRCODE>0</ERRCODE><ERRDESC>错误描述</ERRDESC></BODY></RESPONSE>";

			/*
			 * String responseXml =
			 * "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RESPONSE><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP>"
			 * +
			 * "<SERVICEID>应用标识</SERVICEID></HEAD><BODY><MAINACCT>4a_test</MAINACCT>"
			 * +
			 * "<KEY>4a_test</KEY><ERRCODE>0</ERRCODE><ERRDESC>修改密码失败</ERRDESC>"
			 * +"</BODY></RESPONSE>";
			 */
			logger.info("ModifyMainAcctPwdServices  4a reset pwd response params"
					+ responseXml);
			// 处理返回的数据
			JSONObject repJson = XmlJsonUtil.xmlToJsonObj(responseXml);
			JSONObject rep = repJson.getJSONObject("BODY");
			String errorCode = rep.getString("ERRCODE");
			if ("0".equals(errorCode)) {// 成功
			// if ("true".equals(PropertiesUtil.getValue("isResetPwd"))) {
			// Map<String, Object> uMap = new HashMap<String, Object>();
			// uMap.put("userName", mainAcct);
			// List<User4AEntity> uList = user4AService.findByMap(uMap);
			// User4AEntity uInfo = new User4AEntity();
			// uInfo.setUserName(mainAcct);
			// uInfo.setUpdateTime(new Date());
			// if (uList != null && uList.size() > 0) {
			// user4AService.update(uInfo);
			// } else {
			// user4AService.insert(uInfo);
			// }
			// }
				sendSuccessReslult("success");
			} else {
				// 修改账号密码爆破漏洞
				String errDesc = rep.getString("ERRDESC");
				if (null != errDesc && !"".equals(errDesc)) {
					if (errDesc.indexOf("不存在") != -1
							|| errDesc.indexOf("原密码验证失败") != -1) {
						errDesc = "账号或密码错误!";
					}
				}
				sendFailReslutl(errDesc);
			}
		} catch (Exception e) {
			logger.error("update4APwd --- 修改4A密码异常" + e.toString());
			sendFailReslutl("修改4A密码错误");
		}
	}

	/***
	 * 集团4A重置密码密钥生成
	 * 
	 * @param map
	 * @return
	 */
	public void getResetPwdNotekey() {
		try {
			HttpSession session = Struts2.getRequest().getSession();
			// String mainAcct = map.get("mainAcct");// 4A主账号
			// // 对账号进行解密
			// mainAcct = AESFontUtil.desEncrypt(mainAcct);
			JSONObject head = getPubHead("");
			JSONObject body = new JSONObject();
			body.put("OPERATORID", loginName);
			body.put("OPERATORIP", getIpAddr(Struts2.getRequest()));
			JSONObject requestJson = new JSONObject();
			requestJson.put("HEAD", head);
			requestJson.put("BODY", body);
			String requestXml = XmlJsonUtil.jsonToXml(requestJson.toString(),
					"RESETCREATENOTEKEY");
			logger.info("ReSetCreateService  4a resetpwd notekey  request params"
					+ requestXml);
			// 4A静态密码验证接口
			 String responseXml = wsUtils.SynService(
			 PropertiesUtil.getValue("authLoginUrl")
			 + "/ReSetCreateService", "ReSetCreateService",
			 new Object[] { requestXml });
//			String responseXml = "<?xml version='1.0' encoding= 'UTF-8'?><RESETCREATENOTEKEY><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><RSP>0</RSP><ERRDESC>错误描述</ERRDESC><MSGID>消息序列号</MSGID></BODY></RESETCREATENOTEKEY>";

			/*
			 * String responseXml =
			 * "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RESETCREATENOTEKEY><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP>"
			 * + "<SERVICEID>应用标识</SERVICEID></HEAD><BODY>" +
			 * "<RSP>0</RSP><ERRDESC>获取验证码错误</ERRDESC><MSGID>12345</MSGID>" +
			 * "</BODY></RESETCREATENOTEKEY>";
			 */
			logger.info("ReSetCreateService  4a resetpwd notekey  response params"
					+ responseXml);
			// 处理返回的数据
			JSONObject repJson = XmlJsonUtil.xmlToJsonObj(responseXml);
			JSONObject repmap = repJson.getJSONObject("HEAD");
			if (repmap.containsKey("ERRORCODE")) {
				sendFailReslutl(repmap.getString("ERRMSG"));
				return;
			}
			JSONObject rep = repJson.getJSONObject("BODY");
			String rsp = rep.getString("RSP");// 密钥验证验证结果标志
			if ("0".equals(rsp)) {// 密钥成功
				String msgId = rep.getString("MSGID");// 消息序号，校验重置密码验证码接口时用到
				session.setAttribute("resetPwdMsgId", msgId);
				sendSuccessReslult("success");
			} else {// 密钥生成失败
				sendFailReslutl("获取短信验证码失败！");
			}
		} catch (Exception e) {
			logger.error("getResetPwdNotekey --- 获取4A重置密码短信密钥异常" + e.toString());
			sendFailReslutl("获取4A重置密码密钥错误");
		}
	}

	/***
	 * 4A重置密码验证接口
	 * 
	 * @param map
	 * @return
	 */
	public void checkResetPwdNotekey() {
		try {
			HttpSession session = Struts2.getRequest().getSession();
			// String noteKey = map.get("noteKey");
			// String mainacct = map.get("mainAcct");
			// noteKey = AESFontUtil.desEncrypt(noteKey);
			Object resetPwdMsgId = session.getAttribute("resetPwdMsgId") == null ? ""
					: session.getAttribute("resetPwdMsgId");
			JSONObject head = getPubHead(resetPwdMsgId);
			JSONObject body = new JSONObject();
			body.put("OPERATORID", loginName);
			body.put("OPERATORIP", getIpAddr(Struts2.getRequest()));
			body.put("NOTEKEY", EncryptInterface.desEncryptData(checkCode));
			JSONObject jo = new JSONObject();
			jo.put("HEAD", head);
			jo.put("BODY", body);
			String requestXml = XmlJsonUtil.jsonToXml(jo.toString(),
					"RESETNOTEKEYVALIDATE");
			logger.info("ReSetNoteKeyValidate  request params" + requestXml);
			// 4A短信密钥校验接口
			 String responseXml = wsUtils.SynService(
			 PropertiesUtil.getValue("authLoginUrl")
			 + "/ReSetNoteKeyValidate", "ReSetNoteKeyValidate",
			 new Object[] { requestXml });
//			String responseXml = "<?xml version='1.0' encoding= 'UTF-8'?><RESETNOTEKEYVALIDATE><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><RSP>0</RSP><ERRDESC>错误描述</ERRDESC></BODY></RESETNOTEKEYVALIDATE>";

			/*
			 * String responseXml =
			 * "<?xml version='1.0' encoding= 'UTF-8'?><RESETNOTEKEYVALIDATE><HEAD><CODE>消息标志</CODE>"
			 * +
			 * "<SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD>"
			 * +
			 * "<BODY><RSP>0</RSP><ERRDESC>错误描述</ERRDESC></BODY></RESETNOTEKEYVALIDATE>"
			 * ;
			 */
			logger.info("ReSetNoteKeyValidate  response params" + responseXml);
			JSONObject repJson = XmlJsonUtil.xmlToJsonObj(responseXml);
			JSONObject repBody = repJson.getJSONObject("BODY");
			String rsp = repBody.getString("RSP");
			if ("0".equals(rsp)) {// 校验成功
				if ("true".equals(PropertiesUtil.getValue("isResetPwd"))) {
					// Map<String, Object> uMap = new HashMap<String, Object>();
					// uMap.put("userName", mainacct);
					// List<User4AEntity> uList = user4AService.findByMap(uMap);
					// User4AEntity uInfo = new User4AEntity();
					// uInfo.setUserName(mainacct);
					// uInfo.setUpdateTime(new Date());
					// if (uList != null && uList.size() > 0) {
					// user4AService.update(uInfo);
					// } else {
					// user4AService.insert(uInfo);
					// }
				}
				sendSuccessReslult("success");
			} else {
				sendFailReslutl(repBody.getString("ERRDESC"));
			}
		} catch (Exception e) {
			logger.error("checkResetPwdNotekey --- 校验短信密钥异常" + e.toString());
			sendFailReslutl("checkResetPwdNotekey错误");
		}
	}
	
	/**
	 * 效验单点登录
	 */
	public String singleSignOn(){
		try {
			String token = Struts2.getRequest().getParameter("token");
			String appAcctId = Struts2.getRequest().getParameter("appAcctId");
			String flag = Struts2.getRequest().getParameter("flag");
			logger.info("singleSignOn request params token="+token +"appAcctId="+appAcctId+"flag="+flag);
			if ("1".equals(flag)) {// flag凭证为1时,4A正常状态下的SSO认证方式
				JSONObject head =getPubHead("");
				JSONObject body = new JSONObject();
				body.put("APPACCTID", appAcctId);
				body.put("TOKEN", token);
				JSONObject jo = new JSONObject();
				jo.put("HEAD", head);
				jo.put("BODY", body);
				String requestXml = XmlJsonUtil.jsonToXml(jo.toString(),"USERREQ");
				logger.info("USERREQ  request params" + requestXml);
				String responseXml =wsUtils.SynService(PropertiesUtil.getValue("authLoginUrl")+ 
						"/CheckAiuapTokenSoap", "CheckAiuapTokenSoap",new Object[] { requestXml });
				logger.info("CheckAiuapTokenSoap  response params" + responseXml);
				// 处理返回的数据
				JSONObject repJson = XmlJsonUtil.xmlToJsonObj(responseXml);
				JSONObject rep = repJson.getJSONObject("BODY");
				String rsp = rep.getString("RSP");// 密钥验证验证结果标志
				//String mainAcctId  = rep.getString("MAINACCTID");// 主账号登录名
				String account= rep.getString("APPACCTID");// 从帐号登录名
				if ("0".equals(rsp)) {// 校验成功
					if(Strings.isNotEmpty(account)){
						String[] accounts = account.split(",");
						if (accounts.length <= 0) {
							setLoginMessage("该主账号下没有此从账号！");
							return "resultMsg";
						}
						user = userManagerService.queryUserByName(accounts[0]);
						if (user == null) {
							setLoginMessage("用户名或密码错误，请重试！");
							return "resultMsg";
						}
					}
					// 获取角色信息
					Role role = null;
					List<UserRoleRelation> userRoleRelationList = userRoleService.queryByUser(user.getId());
					if (userRoleRelationList.size() > 0) {
						for (UserRoleRelation userRoleRelation : userRoleRelationList) {
							role = roleService.queryRole(userRoleRelation.getRoleId());
						}
					} else {
						setLoginMessage("该用户没有关联角色，无法登录");
						return "resultMsg";
					}

					UserPermission userPermision = getUserPermission(user.getId(),role.getType());
					if ((null == userPermision.getOperPermissionList() || 0 == userPermision
							.getOperPermissionList().size()) && role.getId() != 1) {
						setLoginMessage("该用户未被授予操作权限");
						return "resultMsg";
					} else {
						if ((null == userPermision.getRolePermissionList() || 0 == userPermision
								.getRolePermissionList().size())&& role.getId() != 1) {
							setLoginMessage("该用户未被授予应用权限");
							return "resultMsg";
						}
					}

					user.setSessionId(ServletActionContext.getRequest().getSession().getId());
					userManagerService.updateSessionId(user);
					// 通过4A登录方式和系统原来登录方式，都共用该方法
					loginValidation(userPermision, user, role);

					getSessionMap().put("masterAccount", ""); // 不通过4A登录，也需要保存这个参数
					OperationLogUtil.ipAddress = getRemoteAddr();
					setLoginMessage("success");
					return SUCCESS;
				
				} else {
					logger.error("rsp --- 4a统一凭证效验失败！");
					setLoginMessage("鉴权失败");
					return "resultMsg";
				}
			} else {	//flag!=1时的静态密码认证方式为应急状态的SSO认证方式。
				logger.error("flag --- 4a应急登录失败！");
				setLoginMessage("鉴权失败");
				return "resultMsg";
			}
			
		} catch (Exception e) {
			logger.error("validitySingleSignOn --- 验证单点登录异常" + e.toString());
			setLoginMessage("鉴权失败");
			return "resultMsg";
		}
		
	}
	
	public String getRemoteAddr() {
		try {
			HttpServletRequest request = Struts2.getRequest();
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
		} catch (Exception e) {
			logger.error("get romote ip error,error message:" + e.getMessage());
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
	 * 
	 * @param userPermision
	 * @param user
	 * @param role
	 */
	private void loginValidation(UserPermission userPermision, User user,
			Role role) {

		// 角色应用关联关系
		List<RolePermissionRelation> list = userPermision
				.getRolePermissionList();
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
		getSessionMap().put("loginName", user.getLoginName());
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

		// getSessionMap().put("localIp", localIp);

		// 当前网络环境标示
		getSessionMap()
				.put("currentContext",
						PropertiesUtil.getValue("currentContext").equals(
								"development") ? true : false);

		getSessionMap().put("operPermission",
				userPermision.getOperPermissionList()); // 操作权限
		getSessionMap().put("unOperPermission",
				userPermision.getUnOperPermissionList());
		getSessionMap().put("dataPermission",
				userPermision.getRolePermissionList()); // 应用权限

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

	public String getLoginMessage() {
		return loginMessage;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getPassword() {
		return password;
	}

	public User getUser() {
		return user;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
