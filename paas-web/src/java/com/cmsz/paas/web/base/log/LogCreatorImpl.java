package com.cmsz.paas.web.base.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.log.LogCreator;
import com.cmsz.paas.common.log.OperationLog;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.web.base.util.syslog4jUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 日志创建者
 * 
 */
public class LogCreatorImpl  implements LogCreator{

	private static final Logger logger = LoggerFactory
			.getLogger(LogCreatorImpl.class);

	@Override
	public OperationLog create(){
//		if (getOperateType().equals("查询")) {
//			return null;
//		}
		MyOperationLog mlog = new MyOperationLog();
		HttpServletRequest strutsRequest = Struts2.getRequest();
		if (strutsRequest != null) {
			mlog.setClientIp(getRemoteAddr());
//			mlog.setClientIp(ActionContext.getContext().getSession()
//					.get("localIp")+"");
//			mlog.setClientIp(getIpAddr(strutsRequest));
			mlog.setSessionId(ServletActionContext.getRequest().getSession().getId());
			//输出参数
			Enumeration<String> paraNames = ServletActionContext.getRequest().getParameterNames();
			StringBuilder sb=new StringBuilder();
			for(Enumeration<String> e=paraNames;e.hasMoreElements();){
			      String thisName=e.nextElement().toString();
			      String thisValue=ServletActionContext.getRequest().getParameter(thisName);
			      sb.append(thisName+":"+thisValue+";");
			}
			if(null != sb.toString() && sb.toString()!="" ){
				mlog.setInputArgs(sb.toString());
			}
			Object data = ActionContext.getContext().getSession().get("data");
			Object resultMessage = ActionContext.getContext().getSession().get("resultMessage");
			System.out.println("========="+resultMessage);
			System.out.println("========="+data);
//			ResponseWrapper responseWrapper;
//			try {
//				responseWrapper = new  ResponseWrapper(Struts2.getResponse());
//				byte[] bytes = responseWrapper.getResponseData();
//				String responseResult = new String(bytes, "UTF-8");
//				System.out.println("===========:"+responseResult);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			try {
//				PrintWriter writer = Struts2.getResponse().getWriter();
//				writer.print("data");
//				//writer.write("data");
//				System.out.println(writer);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			// 操作者
			Object loginName = ActionContext.getContext().getSession().get("loginName");
			if (loginName != null) {
				mlog.setOperator(loginName + "");
			} else {
				mlog.setOperator(strutsRequest.getParameter("loginName"));
			}
			// 操作时间
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String operateTime = df.format(new Date());
			mlog.setOperateTime(operateTime);
			mlog.setOperateType(getOperateType());

			mlog.setStatus("1");
			String actionPath = strutsRequest.getServletPath();
			String nameSpace = actionPath.substring(0,actionPath.lastIndexOf("/"));
			mlog.setFunc(getOperateFunc(nameSpace));
			mlog.setOperatePath(actionPath);

			// 详情字段：应用名称|操作类型|服务名称
			String serviceIds = "";
			if (!("".equals(strutsRequest.getParameter("ipaasServiceId")))
					&& strutsRequest.getParameter("ipaasServiceId") != null) {
				serviceIds = strutsRequest.getParameter("ipaasServiceId");
			}
			if (!("".equals(strutsRequest.getParameter("appServiceId")))
					&& strutsRequest.getParameter("appServiceId") != null) {
				serviceIds = strutsRequest.getParameter("appServiceId");
			}
			if (Strings.isNotBlank(serviceIds)) {
				mlog.setDetail(serviceIds);
			} else {
				mlog.setDetail("");
			}
			Object primary_user = ActionContext.getContext().getSession().get("masterAccount");
			syslog4jUtil.sendSyslog4(mlog,loginName,primary_user);
		}
		logger.info("记录审计日志信息：（" + mlog.getOperator() + ","
				+ mlog.getOperateTime() + "," + mlog.getClientIp() + ","
				+ mlog.getFunc() + "," + mlog.getOperateType() + ","
				+ mlog.getDetail() + "）");
		return mlog;
	}

	public String getOperateFunc(String nameSpace) {
		Map<String, String> operateFuncMap = OperateFunc.getAllDefined();
		String string = operateFuncMap.get(nameSpace);
		if (Strings.isNotBlank(string)) {
			return string;
		}
		return "未知功能模块";
	}

	public String getOperateType() {
		String actionName = ((ActionMapping) ActionContext.getContext()
				.getContextMap().get("struts.actionMapping")).getName();
		String method = ((ActionMapping) ActionContext.getContext()
				.getContextMap().get("struts.actionMapping")).getMethod();
		if (method == null) {
			method = actionName;
		}
		List<OperateType> types = OperateType.getAllDefined();
		for (OperateType t : types) {
			if (t.isMe(method)) {
				return t.getName();
			}
		}
		return "未知操作类型";
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

	
	public String getRemoteAddr() {
        try{
            HttpServletRequest request =  Struts2.getRequest();
            String remoteAddr = request.getRemoteAddr();
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getHeader("X-Forwarded-For");
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
            }
            if (!isEffective(remoteAddr)) {
                remoteAddr = request.getHeader("X-Real-IP");
                logger.info("X-Real-IP " + remoteAddr);
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

}
