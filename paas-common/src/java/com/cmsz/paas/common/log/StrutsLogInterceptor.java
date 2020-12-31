package com.cmsz.paas.common.log;



import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.cmsz.paas.common.lang.reflect.ClassWrapper;
import com.cmsz.paas.common.log.defaults.DefaultLogProcessor;
import com.cmsz.paas.common.log.defaults.DefaultOperationLog;
import com.cmsz.paas.common.log.defaults.LogHolder;
import com.cmsz.paas.common.spring.Springs;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 基于struts2的日志拦截处理，但这只是一个入口点，最终实现决定和日志处理的是LogProcessor
 * 
 * @author Sam
 * 
 */
public class StrutsLogInterceptor extends AbstractInterceptor {
    
    private static final long serialVersionUID = 7839545385551974611L;
    
    @SuppressWarnings("unchecked")
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        Class target = invocation.getAction().getClass();
        Method method = getMethod(target, invocation.getProxy().getMethod());
        HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
        String result;
        try {
            getLogProcessor().init(target, method);
            result = invocation.invoke();
            String isError = (String) request.getAttribute("isError");
            String errCode = (String) request.getAttribute("errCode");
            if("Y".equals(isError)){
            	//创建应用的时候需要在每个数据中心都创建harbor对应的项目，
            	//因为有个数据中心没有配置harbor地址，所以应用创建成功后
            	//需要提示该信息，而且审计日志需要记录是成功，并且要描述信息
            	if("PAAS-20338".equals(errCode)){
            		OperationLog log = LogHolder.getLog();
                	String msg=(String) request.getAttribute("errorMsg");
                	if (log != null) {
                   	 ((DefaultOperationLog) log).setOperateDesc(msg);
                    }
                	getLogProcessor().process(target, method, OperationStatus.SUCCESS);
            	} else {
            		OperationLog log = LogHolder.getLog();
                	String msg=(String) request.getAttribute("errorMsg");
                	if (log != null) {
                   	 ((DefaultOperationLog) log).setOperateDesc(msg);
                    }
                	getLogProcessor().process(target, method, OperationStatus.FAIL);
            	}
            }else{
            	getLogProcessor().process(target, method, OperationStatus.SUCCESS);
            }
            return result;
        } catch (Exception e) {
        	OperationLog log = LogHolder.getLog();
        	String msg=(String) request.getAttribute("errorMsg");
        	if (log != null) {
           	 ((DefaultOperationLog) log).setOperateDesc(msg);
            }
            getLogProcessor().process(target, method, OperationStatus.FAIL);
            throw e;
        }
    }
    
    @SuppressWarnings("unchecked")
    public Method getMethod(Class target, String methodName) {

        return ClassWrapper.wrap(target).getMethod(methodName);
    }
    
    public LogProcessor getLogProcessor() {

        LogProcessor logProcessor = Springs.getBean(LogProcessor.class);
        if (logProcessor != null)
            return logProcessor;
        return new DefaultLogProcessor();
    }
    
}
