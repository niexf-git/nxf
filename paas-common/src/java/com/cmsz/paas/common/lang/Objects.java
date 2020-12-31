/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.cmsz.paas.common.lang.collection.Collections;
import com.cmsz.paas.common.lang.collection.Maps;

/**
 * object操作工具类
 * 
 * @author sam
 * 
 */
public abstract class Objects extends ObjectUtils {
    
    public static final Object EMPTY = new Object();
    
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(T o) {

        return (Class<T>) (o != null ? o.getClass() : Object.class);
    }
    
    public static boolean isArray(Object o) {

        return getClass(o).isArray();
    }
    
    public static boolean isAnnotation(Object o) {

        return getClass(o).isAnnotation();
    }
    
    public static boolean isInterface(Object o) {

        return getClass(o).isInterface();
    }
    
    public static Object nvl(Object o, Object v) {

        return (o == null) ? v : o;
    }
    
    public static String toString(Object[] objs) {

        if (isEmpty(objs))
            return "{}";
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(Strings.join(objs, ",")).append("}");
        return sb.toString();
    }
    
    public static boolean isEmpty(Object o) {

        if (o == null)
            return true;
        if (o instanceof String)
            return Strings.isEmpty((String) o);
        else if (o instanceof Collection<?>)
            return Collections.isEmpty(Collection.class.cast(o));
        else if (o instanceof Map<?, ?>)
            return Maps.isEmpty(Map.class.cast(o));
        else {
            Class<?> t = o.getClass();
            if (t.isArray())
                return Array.getLength(o) <= 0;
            else
                return false;
        }
        
    }
    
    public static <T> T newObject(Class<T> type) {

        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw Lang.wrapThrow(e, "实例化类型为\"%s\"时出错了！", type.getName());
        } catch (IllegalAccessException e) {
            throw Lang.wrapThrow(e, "实例化类型为\"%s\"时出错了！", type.getName());
        }
    }
    
    public static boolean isNotEmpty(Object o) {

        return !isEmpty(o);
    }
    
}
