package com.cmsz.paas.web.base.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.util.ParamsValidateUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.role.entity.Role;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ParamsValidateInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -7952022147816596057L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ActionContext
				.getContext().get(StrutsStatics.HTTP_RESPONSE);
		String uri = request.getRequestURI();
		Map<String, Object> session = ActionContext.getContext().getSession();
		Role role = (Role) session.get("role");
//		if (uri.contains("/user/login") || uri.contains("/log/querySysLog")
//				|| uri.contains("/alarm/queryAlarm")
//				|| (role == null?true:role.getId() == 1 && uri.contains("/user/queryUserList"))
//				|| (role == null?true:role.getId() == 1 && uri.contains("/role/queryRoleList"))
//				|| uri.contains("/imageSync/queryImageSyncList")
//				|| uri.contains("/log/queryAppLog")
//				|| uri.contains("/log/queryIpaasLog")
//				|| uri.contains("/sysAlarm/queryAlarmList")) {// 不拦截的请求为前台直接操作数据库
//																// 无需转义请求参数
//			return actionInvocation.invoke();
//		}
		
		if(role != null){
			//对普通用户开放的操作权限
			if(role.getId() != 1){//普通用户在以下模块 中只对部分功能有操作权限
				if(uri.contains("/user/") ||
				   uri.contains("/role/") ||
				   uri.contains("/application/") ||
				   uri.contains("/monitoroperation/")){
					if(uri.contains("/user/login") ||
					   uri.contains("/user/verifyLoginParams") ||
					   uri.contains("/user/getImage") ||
					   uri.contains("/user/queryCurrenEvn") ||
					   uri.contains("/user/updatePassword") ||
					   uri.contains("/user/queryAppInfo") ||
					   uri.contains("/user/changeAppInfo") ||
					   uri.contains("/application/queryAppList") || 
					   uri.contains("/monitoroperation/") ||
					   uri.contains("/application/queryDnsAndClusterById") ||
					   uri.contains("/application/importApp") ||
					   uri.contains("/application/queryDNSList") ||
					   uri.contains("application/createDNS") ||
					   uri.contains("application/queryDetailsList")){
						return actionInvocation.invoke();
					}else{
						sendFailResult(Constants.USER_AUTHORITY_ERROR,
								"用户权限不足！", response,
								"");
						return null;
					}
				}
			}
		}
		HttpParameters hp = actionInvocation.getInvocationContext().getParameters();
		Map<String,String[]> ps = hp.toMap();
		
		/*Map<String, Object> ps = actionInvocation.getInvocationContext()
				.getParameters();*/
		Set<String> keys = ps.keySet();
		String operateType = getOperateType();
		// 循环所有的key
		for (String key : keys) {
			// 基础服务的配置文件不校验
			if ("ipaasService.config".equals(key)) {
				continue;
			}
			// 得到参数对应值
			String[] value = (String[]) ps.get(key);
			for (int i = 0; i < value.length; i++) {// 对每一个值
				if (ParamsValidateUtil.isIncludeWord(value[i])) {
					sendFailResult(Constants.PARAMS_VALIDATE_ERROR,
							"输入的内容中包含非法字符，如:insert、delete和truncate等", response,
							operateType);
					return null;
				} else {
					if (uri.contains("/user/login") || uri.contains("/log/querySysLog")
							|| uri.contains("/alarm/queryAlarm")
							|| (role == null?true:role.getId() == 1 && uri.contains("/user/queryUserList"))
							|| (role == null?true:role.getId() == 1 && uri.contains("/role/queryRoleList"))
							|| uri.contains("/imageSync/queryImageSyncList")
							|| uri.contains("/log/queryAppLog")
							|| uri.contains("/log/queryIpaasLog")
							|| uri.contains("/sysAlarm/queryAlarmList")) {// 不拦截的请求为前台直接操作数据库
																			// 无需转义请求参数
					}else{
						if ("query".equals(operateType)) {
							value[i] = ParamsValidateUtil
									.escapeSpecialChar(value[i]); // 处理url中的转义字符
						} else {
							value[i] = value[i].replaceAll("<", "&lt;")
									.replaceAll("'", "&apos;")
									.replaceAll("\"", "&quot;");
						}
					}
				}
			}
		}
		actionInvocation.getInvocationContext().setParameters(HttpParameters.create(ps).build());
		return actionInvocation.invoke();
	}

	/*
	 * 输出错误信息
	 */
	public void sendFailResult(String errCode, String errMsg,
			HttpServletResponse response, String operateType) {
		String responseMsg = "";
		// 查询与其它操作的错误提示方式不同
		if ("query".equals(operateType)) {
			responseMsg = JsonUtil
					.parseObjectToJSON("{'userdata':{'errorCode':'" + errCode
							+ "','errorMsg':'" + errMsg + "'}}");
		} else {
			JSONObject obj = new JSONObject();
			try {
				obj.put("resultCode", errCode);
				obj.put("resultMessage", errMsg);
				responseMsg = obj.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(responseMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 操作的类型
	 */
	public String getOperateType() {
		String result = "";
		String actionName = ((ActionMapping) ActionContext.getContext()
				.getContextMap().get("struts.actionMapping")).getName();
		String method = ((ActionMapping) ActionContext.getContext()
				.getContextMap().get("struts.actionMapping")).getMethod();
		if (method == null) {
			method = actionName;
		}
		if (method.startsWith("query") || method.startsWith("fill")) {
			result = "query";
		}
		return result;
	}

}
