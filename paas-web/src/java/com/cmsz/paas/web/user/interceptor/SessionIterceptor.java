package com.cmsz.paas.web.user.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;

import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.group4a.action.Group4aAction;
import com.cmsz.paas.web.user.action.CreateImageAction;
import com.cmsz.paas.web.user.action.UserManagerAction;
import com.cmsz.paas.web.user.entity.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionIterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -3261518692067409164L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Log.info("启用拦截器");
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		String uri = request.getRequestURI();
		if (uri.endsWith(".action")) {
			String referer="";
			if (request.getHeader("Origin") != null) {
				referer = request.getHeader("Origin");
			} else if (request.getHeader("referer") != null) {
				referer = request.getHeader("referer").substring(0,
						request.getHeader("referer").lastIndexOf(":"));
				String port = request.getHeader("referer").substring(
						request.getHeader("referer").lastIndexOf(":"),
						request.getHeader("referer").length());
				String port1 = port.substring(0, port.indexOf("/"));
				referer = referer + port1;
			}
			if (referer.equals("")) {
				response.setStatus(403);
				return Action.LOGIN;
			}else{
				String value = PropertiesUtil.getValue("referer");
				String[] split = value.split(",");
				boolean falg=false;
				for (int i = 0; i < split.length; i++) {
					if(referer.equals(split[i].toString())){
						falg=true;
					}
					
				}
				if (!falg) {
					response.setStatus(403);
					return Action.LOGIN;
				}
				
			}
		}
		if (actionInvocation.getAction() instanceof UserManagerAction||
				actionInvocation.getAction() instanceof CreateImageAction||
				actionInvocation.getAction() instanceof Group4aAction) {
			return actionInvocation.invoke();
		}
		User user = (User) ctx.getSession().get(Constants.CURRENT_USER);
		if (user == null) {
			return Action.LOGIN;
		} 
		else if(user.getId() == null || "".equals(user.getId())){
			return Action.LOGIN;
		}
		else {
			return actionInvocation.invoke();
		}
	}
}
