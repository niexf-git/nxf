package com.cmsz.paas.common.struts2;
/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.cmsz.paas.common.lang.Arrays;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.page.Paginations;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 基于struts2的上下文环境工具类，可通过此类取得request\response等对象及一些工具方法
 * 
 * @author Sam
 * 
 */
public abstract class Struts2 {
    
    /**
     * 构建分页上下文对象
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static PageContext buildPageContext(String... paramNames) {

        HttpServletRequest request = getRequest();
        Parameter param = Parameter.create(request);
        int pageNo = param.getInt("page", 1);
        int pageSize = param.getInt("rows", 10);
		String sortname = underscoreName(param.getString("sidx"));
		String sortvalue = param.getString("sord");
        Map conditions = new HashMap();
        if (Arrays.isNotEmpty(paramNames)) {
            for (String pName : paramNames) {
                conditions.put(pName, request.getParameter(pName));
            }
        } else {
            
            conditions = param.getAll("page", "rows", "sortname", "sortorder");
        }
        Set keySet = conditions.keySet();
        for (Iterator it = keySet.iterator(); it.hasNext();) {
            Object key = it.next();
            Object value = conditions.get(key);
            if (value != null && value instanceof String) {
                conditions.put(key, value.toString().replaceAll("'", "''"));
            }
        }
        
        return Paginations.createContext(conditions, sortname, sortvalue, pageNo, pageSize);
    }
    
    public static ActionMapping getActionMapping() {

        return ServletActionContext.getActionMapping();
    }
    
    public static String getCurrentActionName() {

        return ActionContext.getContext().getName();
    }
    
    public static HttpServletRequest getRequest() {

        return ServletActionContext.getRequest();
    }
    
    public static HttpServletResponse getResponse() {

        HttpServletResponse response = ServletActionContext.getResponse();
        return response;
    }
    
    public static ServletContext getServletContext() {

        return getSession().getServletContext();
    }
    
    public static HttpSession getSession() {

        return ServletActionContext.getRequest().getSession();
    }
    
    public static ValueStack getValueStack() {

        return ServletActionContext.getValueStack(getRequest());
    }
    
    public static void render(String contentType, String string) throws IOException {

        HttpServletResponse response = getResponse();
        response.setContentType(contentType);
        response.getWriter().println(string);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    public static void renderHTML(String html) {

        try {
            render("text/html;charset=utf-8", html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
	    StringBuilder result = new StringBuilder();
	    if (name != null && name.length() > 0) {
	        // 将第一个字符处理成大写
	        result.append(name.substring(0, 1).toUpperCase());
	        // 循环处理其余字符
	        for (int i = 1; i < name.length(); i++) {
	            String s = name.substring(i, i + 1);
	            // 在大写字母前添加下划线
	            if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
	                result.append("_");
	            }
	            // 其他字符直接转成大写
	            result.append(s.toUpperCase());
	        }
	    }
	    return result.toString();
	}
}
