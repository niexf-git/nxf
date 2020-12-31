package com.cmsz.paas.web.group4a.ws.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;












import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.remoting.jaxrpc.ServletEndpointSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.asiainfo.uap.util.des.EncryptInterface;
import com.cmsz.paas.web.base.util.MD5Util;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.group4a.tool.XmlJsonUtil;
import com.cmsz.paas.web.group4a.ws.UpdateAppAcctSoapService;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.service.UserManagerService;
import com.cmsz.paas.web.user.service.UserRoleRelationService;

/**
 * @author ccl
 * @date 
 * 从账号变更接口
 */
@Component
@WebService(serviceName = "/UpdateAppAcctSoapService", targetNamespace = "http://127.0.0.1:8080")
public class UpdateAppAcctSoapImpl extends ServletEndpointSupport implements UpdateAppAcctSoapService {
	
	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory
			.getLogger(UpdateAppAcctSoapImpl.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private UserManagerService userInfoService;
	
	/** 用户与角色关联service对象. */
	@Autowired
	private UserRoleRelationService userRoleService;
	

	
	@WebMethod
	@WebResult(targetNamespace = "http://127.0.0.1:8080")
	@Transactional
	@Override
	public String UpdateAppAcctSoap(String RequestInfo){
		logger.info("UpdateAppAcctSoap params: "+RequestInfo);
		applicationContext = super.getApplicationContext();
		
		userInfoService = (UserManagerService) applicationContext.getBean("UserManagerService");
		userRoleService = (UserRoleRelationService) applicationContext.getBean("UserRoleRelationService");
		String operatorid="";
		try {
			JSONObject jo = XmlJsonUtil.xmlToJsonObj(RequestInfo);//解析XML参数
			JSONObject body = jo.getJSONObject("BODY");
			operatorid = body.getString("OPERATORID");//从账号
			String modifymode = body.getString("MODIFYMODE");//add、delete、change、chgstatus、resetpwd
			if("add".equals(modifymode)){
				String addUser = addUser(body, operatorid);
				logger.info("addUser: "+addUser);
				return addUser;
			}else if("delete".equals(modifymode)){
				String deleteUser = deleteUser(body, operatorid);
				logger.info("deleteUser: "+deleteUser);
				return deleteUser;
			}else if("change".equals(modifymode)){
				String updateUser = updateUser(body, operatorid);
				logger.info("updateUser: "+updateUser);
				return updateUser;
			}
			else if("chgstatus".equals(modifymode)){
				String chgstatus = chgstatus(body, operatorid);
				logger.info("chgstatus: "+chgstatus);
				return chgstatus;
			}
			else if("resetpwd".equals(modifymode)){
				String resetpwd = resetpwd(body, operatorid);
				logger.info("resetpwd: "+resetpwd);
				return resetpwd;
			}
		} catch (Exception e) {
			logger.error("updateAppAcctSoap : "+e.toString());
			return repFailXml(operatorid,"操作错误");
		}
		return repFailXml(operatorid,"操作错误");
	}

	private String resetpwd(JSONObject body, String operatorid) {
		try {
			JSONObject userInfo = body.getJSONObject("USERINFO");
			User user =userInfoService.queryUserById(Long.parseLong(userInfo.getString("USERID")));
			String password = userInfo.getString("PASSWORD");
			password = EncryptInterface.desUnEncryptData(password);
			password = MD5Util.MD5(password);//按照本系统加密
			user.setPassword(password);
			userInfoService.updateUser(user);//修改用户
			return repSuccessXml("resetpwd", userInfo.getString("USERID"), operatorid, "0", "");
		} catch (Exception e) {
			logger.error("resetpwd : "+e.toString());
			return repSuccessXml("resetpwd", body.getJSONObject("USERINFO").getString("USERID"), operatorid, "1", "");
		}
	}

	private String chgstatus(JSONObject body, String operatorid) {
		try {
			JSONObject userInfo = body.getJSONObject("USERINFO");
//			User user =userInfoService.queryUserById(Long.parseLong(userInfo.getString("USERID")));
//			uie.setUserDesc(user.getUserDesc());
//			uie.setStatus(userInfo.getString("STATUS"));
//			userInfoService.updateUser(uie);//修改用户
			return repSuccessXml("chgstatus", userInfo.getString("USERID"), operatorid, "0", "");
		} catch (Exception e) {
			logger.error("chgstatus : "+e.toString());
			return repSuccessXml("chgstatus", body.getJSONObject("USERINFO").getString("USERID"), operatorid, "1", "");
		}
	}

	private String updateUser(JSONObject body, String operatorid) {
		try {
			JSONObject userInfo = body.getJSONObject("USERINFO");
			User uie = new User();
			uie.setId(Long.parseLong(userInfo.getString("USERID")));
			uie.setUserName(userInfo.getString("USERNAME"));
			uie.setLoginName(userInfo.getString("LOGINNO"));
			uie.setPhone(userInfo.getString("MOBILE"));
			uie.setEmail(userInfo.getString("EMAIL"));
			userInfoService.updateUser(uie);//修改用户
			return repSuccessXml("change", userInfo.getString("USERID"), operatorid, "0", "");
		} catch (Exception e) {
			logger.error("updateUser : "+e.toString());
			return repSuccessXml("change", body.getJSONObject("USERINFO").getString("USERID"), operatorid, "1", "修改用户失败");
		}
	}

	private String deleteUser(JSONObject body, String operatorid) {
		try {
			JSONObject userInfo = body.getJSONObject("USERINFO");
			String userId = userInfo.getString("USERID");
			if(null!=userId&&!"".equals(userId)){
				// 先删除关联的表数据
				userRoleService.deleteByUser(Long.parseLong(userId));
				userInfoService.deleteUser(Long.parseLong(userId));
			}
			return repSuccessXml("delete", userId, operatorid, "0", "");
		} catch (Exception e) {
			logger.error("deleteUser : "+e.toString());
			return repSuccessXml("delete", body.getJSONObject("USERINFO").getString("USERID"), operatorid, "1", "");
		}
	}

	private String addUser(JSONObject body, String operatorid) {
		try {
			JSONObject userInfo = body.getJSONObject("USERINFO");
			Map<String, String> map = new HashMap<String, String>();
			map.put("userName", userInfo.getString("LOGINNO"));
			User userList = userInfoService.queryUserByName(userInfo.getString("LOGINNO"));
			if(userList!=null){
				return repSuccessXml("add", "", operatorid, "1", "新增用户错误,该用户已存在");
			}
			User uie = new User();
			uie.setLoginName(userInfo.getString("LOGINNO"));
			String password = userInfo.getString("PASSWORD");
			password = EncryptInterface.desUnEncryptData(password);
			password = MD5Util.MD5(password);//按照本系统加密
			uie.setPassword(password);
			uie.setCreateTime(sdf.format(new Date()));
			uie.setUserName(userInfo.getString("USERNAME"));
			uie.setPhone(userInfo.getString("MOBILE"));
			uie.setEmail(userInfo.getString("EMAIL"));
			userInfoService.addUser(uie);//新增用户
			Long userId = uie.getId();//新增时返回新增的ID
			return repSuccessXml("add", userId+"", operatorid, "0", "");
		} catch (Exception e) {
			logger.error("addUser : "+e.toString());
			return repSuccessXml("add", body.getJSONObject("USERINFO").getString("USERID"), operatorid, "1", "新增用户错误");
		}
	}

	private String repFailXml(String loginName,String desc) {
		JSONObject userReq = new JSONObject();
		JSONObject headReq = new JSONObject();
		headReq.put("CODE", "");
		headReq.put("SID", "");
		headReq.put("TIMESTAMP",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		headReq.put("SERVICEID", PropertiesUtil.getValue("serviceId"));
		userReq.put("HEAD", headReq);
		JSONObject bodyReq = new JSONObject();
		bodyReq.put("KEY", loginName);
		bodyReq.put("ERRCODE", "");
		bodyReq.put("ERRDESC", desc);
		userReq.put("BODY", bodyReq);
		String repXml= XmlJsonUtil.jsonToXml(userReq.toString(), "USERREQ");
		return repXml;
	}
	private String repSuccessXml(String modifymode,String userId,String loginNo,String rsp,String errDesc) {
		JSONObject userReq = new JSONObject();
		JSONObject headReq = new JSONObject();
		headReq.put("CODE", "");
		headReq.put("SID", "");
		headReq.put("TIMESTAMP",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		headReq.put("SERVICEID", PropertiesUtil.getValue("serviceId"));
		userReq.put("HEAD", headReq);
		JSONObject bodyReq = new JSONObject();
		bodyReq.put("MODIFYMODE", modifymode);
		bodyReq.put("USERID", userId);
		bodyReq.put("LOGINNO", loginNo);
		bodyReq.put("RSP", rsp);
		bodyReq.put("ERRDESC", errDesc);
		userReq.put("BODY", bodyReq);
		String repXml= XmlJsonUtil.jsonToXml(userReq.toString(), "USERMODIFYRSP");
		return repXml;
	}
	
	public static void main(String[] args) throws Exception {
		/*String  xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String xml = "<USERROLEMODIFYREQ><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP>"
				+ "<SERVICEID>业务应用标识</SERVICEID></HEAD><BODY><OPERATORID>管理员从帐号</OPERATORID><OPERATORPWD>管理员密码</OPERATORPWD>"
				+ "<USERID>从帐号登录名</USERID><ROLELIST><ROLE><ID>角色标识</ID><NAME>角色名称</NAME></ROLE>"
				+ "<ROLE><ID>角色标识</ID><NAME>角色名称</NAME></ROLE></ROLELIST></BODY></USERROLEMODIFYREQ>";

		//Document  doc = DocumentHelper.parseText(xmlHead+xml);
		JSONObject jo = XmlJsonUtil.xmlToJsonObj(xmlHead+xml);
		JSONObject body  = jo.getJSONObject("BODY");
		System.out.println("body "+body);
		Object roleList = body.get("ROLELIST");
		if(roleList instanceof JSONArray){
			roleList = body.getJSONArray("ROLELIST");
			JSONArray ja = new JSONArray();
			for(int i=0;i<body.getJSONArray("ROLELIST").size();i++){
				System.out.println("aaa  "+body.getJSONArray("ROLELIST").get(i));
				JSONObject jobj = new JSONObject();
				jobj.put("ROLE",body.getJSONArray("ROLELIST").get(i));
				ja.add(jobj);
			}
			System.out.println("ja "+ja.toString());
			String xml1 = XmlJsonUtil.jsonToXml(ja.toString(),"ROLELIST");
			System.out.println("array" + xml1.replaceAll("<e>|</e>", ""));
		}else{
			roleList = body.getJSONObject("ROLELIST");
			//<ROLELIST><ROLE><ID>角色标识</ID><NAME>角色名称</NAME></ROLE></ROLELIST>
			String xml1 = XmlJsonUtil.jsonToXml(roleList.toString(), "ROLELIST");
			System.out.println("object" + xml1);
		}*/
	}
	
	
	
}
