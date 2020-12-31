package com.cmsz.paas.web.group4a.ws;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jfree.util.Log;
import org.springframework.stereotype.Component;

import com.asiainfo.uap.util.des.EncryptInterface;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.group4a.tool.WsUtils;
import com.cmsz.paas.web.group4a.tool.XmlJsonUtil;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.paas.web.role.service.RoleService;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.entity.UserRoleRelation;
import com.cmsz.paas.web.user.service.UserRoleRelationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 角色变更、从账号授权
 *
 */
@Aspect
@Component
public class UpdateAppAcctRoleServices {
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleRelationService userRoleService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Pointcut("(execution(* com.cmsz.paas.web.role.action.RoleAction.createRole(..)))"
			+" || (execution(* com.cmsz.paas.web.role.action.RoleAction.updateRole(..)))"
			+" || (execution(* com.cmsz.paas.web.role.action.RoleAction.deleteRole(..)))"
			+" || (execution(* com.cmsz.paas.web.user.action.UserManagerAction.updateUserRole(..)))"
			)
	public void executions(){
		
	};
	@Around("executions()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		HttpServletRequest request =null;
		try {
			request = ServletActionContext.getRequest();
		} catch (Exception e) {
			Log.error(e.toString());
		}
		
		Object[] args = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		
		Role deleteRole = null;
		if("deleteRole".equals(methodName)){//删除时，提前获取数据，方便发送给4A
			String roleId =request.getAttribute("roleId").toString();
			deleteRole = roleService.queryRole(Long.parseLong(roleId));
		}
		joinPoint.proceed();
		Object resultCode = request.getAttribute("resultCode");
		if(null!=resultCode){
			if("success".equals(resultCode)){ //操作成功再发给4A
				User user = (User)request.getSession().getAttribute("user");//获取登录的从账号信息
				if("updateUserRole".equals(methodName)){//授权变更
					try {
						Object userId = request.getSession().getAttribute("userId");//获取被授予角色的用户ID
						List<UserRoleRelation> listUserRoleRelation = userRoleService.queryByUser(Long.parseLong(userId.toString()));
						JSONObject jo = new JSONObject();
						jo.put("HEAD", getPubHead());
						JSONObject body = getPubBody(user.getUserName(), EncryptInterface.desEncryptData(user.getPassword()), getClientIpAddress(request), userId+"");
						JSONArray roleList= getRoleList(listUserRoleRelation);
						body.put("ROLELIST", roleList);
						jo.put("BODY", body);
						String reqXml= XmlJsonUtil.jsonToXml(jo.toString(), "USERROLEMODIFYREQ");
						reqXml = reqXml.replaceAll("<e>|</e>", "");
						String repXml = WsUtils.SynService(PropertiesUtil.getValue("authLoginUrl")+"/UpdateAppAcctRoleServices", "UpdateAppAcctRoleServices", new Object[]{reqXml});
						Log.info("updateUserRole 4A返回数据 : "+repXml);
					} catch (Exception ex) {
						Log.error("updateUserRole" + ex.toString());
					}
				}else if("createRole".equals(methodName)){//创建角色
					try {
						String roleId = request.getSession().getAttribute("roleId")+"";
						if(null!=roleId&&!"".equals(roleId)){
							Role role = roleService.queryRole(Long.parseLong(roleId));
							String reqXml = roleOper(user, role, "add");
							String repXml = WsUtils.SynService(PropertiesUtil.getValue("authLoginUrl")+"/UpdateAppRoleServices", "UpdateAppRoleServices", new Object[]{reqXml});
							Log.info( "createRole 同步数据至4A: "+repXml);
						}
					} catch (Exception ex) {
						Log.error("createRole error 同步数据至4A:" + ex.toString());
					}
				}else if("updateRole".equals(methodName)){//修改角色
					try {
						String roleId =request.getAttribute("roleId").toString();
						Role role = roleService.queryRole(Long.parseLong(roleId));
						String reqXml = roleOper(user, role, "change");
						String repXml = WsUtils.SynService(PropertiesUtil.getValue("authLoginUrl")+"/UpdateAppRoleServices", "UpdateAppRoleServices", new Object[]{reqXml});
						Log.info("updateRole 同步数据至4A : "+repXml);
					} catch (Exception ex) {
						Log.error("updateRole error 同步数据至4A:" + ex.toString());
					}
				}else if("deleteRole".equals(methodName)){//删除角色
					try {
						Role role = deleteRole;
						String reqXml = roleOper(user, role, "delete");
						String repXml = WsUtils.SynService(PropertiesUtil.getValue("authLoginUrl")+"/UpdateAppRoleServices", "UpdateAppRoleServices", new Object[]{reqXml});
						Log.info( "deleteRole 同步数据至4A : "+repXml);
					} catch (Exception ex) {
						Log.error("deleteRole error 同步数据至4A:" + ex.toString());
					}
				}
			}
		}
        return args;  
	}
	//授权的功能
	private JSONArray getRoleList(List<UserRoleRelation> listUserRoleRelation){
		JSONArray ja = new JSONArray();
		if(listUserRoleRelation.size()>0){
			for(UserRoleRelation userRoleRelation:listUserRoleRelation){
				JSONObject jo = new JSONObject();
				jo.put("ID", userRoleRelation.getRoleId());
				jo.put("NAME", "");
				jo.put("EFFECTDATE", sdf.format(new Date()));
				jo.put("EXPIREDATE", "20991230235959");
				JSONObject jo1 = new JSONObject();
				jo1.put("ROLE", jo);
				ja.add(jo1);
			}
		}
		return ja;
	}
	
	//角色操作增删改
	private String roleOper(User user,Role role,String modifymode) {
		JSONObject jo = new JSONObject();
		jo.put("HEAD", getPubHead());
		JSONObject body = getPubBody(user.getUserName(), EncryptInterface.desEncryptData(user.getPassword()), null, null);
		JSONObject roleInfo = getRoleInfo(role.getId()+"",role.getRoleName(),modifymode);
		body.put("ROLEINFOS", roleInfo);
		jo.put("BODY", body);
		String reqXml= XmlJsonUtil.jsonToXml(jo.toString(), "ROLEMODIFYREQ");
		return reqXml;
	}
	//公共HEAD属性
	private JSONObject getPubHead(){
		JSONObject headReq = new JSONObject();
		headReq.put("CODE", "");
		headReq.put("SID", "");
		headReq.put("TIMESTAMP",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		headReq.put("SERVICEID", PropertiesUtil.getValue("serviceId"));
		return headReq;
	}
	
	//公共BODY属性
	private JSONObject getPubBody(String id,String pwd,String ip,String userId){
		JSONObject bodyReq = new JSONObject();
		bodyReq.put("OPERATORID", id);
		bodyReq.put("OPERATORPWD", pwd);
		if(null!=ip){
			bodyReq.put("OPERATORIP", ip);
		}
		if(null!=userId){
			bodyReq.put("USERID", userId);
		}
		return bodyReq;
	}
	//角色增删改操作
	private JSONObject getRoleInfo(String roleId,String roleName,String modifymode){
		JSONObject roleInfo = new JSONObject();
		JSONObject role = new JSONObject();
		role.put("MODIFYMODE", modifymode);
		role.put("ROLEID", roleId);
		role.put("ROLENAME", roleName);
		roleInfo.put("ROLEINFO", role);
		return roleInfo;
	}
	private final static String getClientIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if("127.0.0.1".equals(ip)){
			ip = getLocalIPAddress();
		}
		return ip;
	} 
	private final static String getLocalIPAddress(){
			String result = "127.0.0.1";
			try {
				result = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
			}
			return result;
	}

}
