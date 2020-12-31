/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20110111 增加修改历史注释<br/>
 * sam 20110117 修改类注释及增加取Long型参数的接口<br/>
 */

package com.cmsz.paas.common.struts2;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cmsz.paas.common.lang.Arrays;
import com.cmsz.paas.common.lang.Dates;
import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.lang.collection.Lists;
import com.cmsz.paas.common.lang.reflect.Beans;
import com.cmsz.paas.common.lang.reflect.ClassWrapper;



/**
 * 请求参数处理,因为request.getParameter()取出来的参数都为String型，在实际处理中不方便，所以提供一些方便的方法使用 如：
 * <p>
 * 
 * <pre>
 *  Paramter paramter = Paramter.create(request);
 *  Long userId = paramter.getLong("userId");//取出一个Long型的参数;
 *  String[] aaParams = parameter.findByStartsWith("aa");//找出参数名为aa前缀的所有参数
 *  Date d = paramter.getDate("date");//取出一个Date型的参数 
 * </pre>
 * <p>
 * 
 * @author Sam
 * 
 */
public class Parameter {
    
    public static Parameter create(HttpServletRequest request) {

        return new Parameter(request);
    }
    
    private final HttpServletRequest request;
    
    private Parameter(HttpServletRequest request) {

        this.request = request;
    }
    
    @SuppressWarnings("unchecked")
    public String[] findByEndsWith(String suffix) {

        List<String> founds = Lists.newList();
        Enumeration r = request.getParameterNames();
        while (r.hasMoreElements()) {
            String pName = r.nextElement().toString();
            if (pName.endsWith(suffix)) {
                founds.add(request.getParameter(pName));
            }
        }
        return Lists.toArray(founds);
    }
    
    @SuppressWarnings("unchecked")
    public String[] findByStartsWith(String prefix) {

        List<String> founds = Lists.newList();
        Enumeration r = request.getParameterNames();
        while (r.hasMoreElements()) {
            String pName = r.nextElement().toString();
            if (pName.startsWith(prefix)) {
                founds.add(request.getParameter(pName));
            }
        }
        return Lists.toArray(founds);
    }
    
    @SuppressWarnings("unchecked")
    public Map getAll(String... noNeedParamNames) {

        Map map = new HashMap();
        Enumeration r = request.getParameterNames();
        while (r.hasMoreElements()) {
            String pName = r.nextElement().toString();
            map.put(pName, request.getParameter(pName));
        }
        for (String s : noNeedParamNames) {
            map.remove(s);
        }
        return map;
    }
    
    public <T> T[] getArray(Class<T> type, String paramName) {

        String[] strArray = getStringArray(paramName);
        return Arrays.to(type, strArray);
    }
    
    public Date getDate(String paramName) {

        return Dates.parse(getString(paramName));
    }
    
    public Date getDate(String paramName, String format) {

        return Dates.parse(getString(paramName), format);
    }
    
    public Double getDouble(String paramName) {

        return Double.valueOf(getString(paramName));
    }
    
    public Double[] getDoubleArray(String paramName) {

        return getArray(Double.class, paramName);
    }
    
    public Integer getInt(String paramName) {

        return Integer.valueOf(getString(paramName));
    }
    
    public Integer getInt(String paramName, int defaultValue) {

        return Strings.isEmpty(getString(paramName)) ? defaultValue : getInt(paramName);
    }
    
    public Integer[] getIntArray(String paramName) {

        return getArray(Integer.class, paramName);
    }
    
    public Long getLong(String paramName) {

        return Long.valueOf(getString(paramName));
    }
    
    public Long getLong(String paramName, long defaultValue) {

        return Strings.isEmpty(getString(paramName)) ? defaultValue : getLong(paramName);
    }
    
    public Long[] getLongArray(String paramName) {

        return getArray(Long.class, paramName);
    }
    
    public String getString(String paramName) {

        return request.getParameter(paramName);
    }
    
    public String[] getStringArray(String paramName) {

        return request.getParameterValues(paramName);
    }
    
	public <T> T paramsTo(Class<T> type) throws IllegalAccessException,
			InvocationTargetException {

        T t = ClassWrapper.wrap(type).newOne();
        Beans.populate(t, request.getParameterMap());
        return t;
    }
}
