/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.lang;

import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.lang.collection.Collections;
import com.cmsz.paas.common.lang.collection.Maps;
import com.cmsz.paas.common.lang.reflect.ClassWrapper;

/**
 * 主要提供一些常用的方法，如异常封装，对象是否为空等
 * 
 * @author sam
 * 
 */
public abstract class Lang {
    
    public static void print(Object objs) {

        System.out.print(objs);
    }
    
    public static void println(Object objs) {

        System.out.println(objs);
    }
    
    public static void println() {

        System.out.println();
    }
    
    public static void printf(String fmt, Object... args) {

        System.out.print(String.format(fmt, args));
    }
    
    public static void printlnf(String fmt, Object... args) {

        System.out.println(String.format(fmt, args));
    }
    
    public static RuntimeException makeThrow(String fmt, Object... args) {

        return new RuntimeException(String.format(fmt, args));
    }
    
    public static RuntimeException wrapThrow(Exception e) {

        return new RuntimeException(e);
    }
    
    public static RuntimeException wrapThrow(Exception e, String fmt, Object... args) {

        return new RuntimeException(String.format(fmt, args), e);
    }
    
    public static <T extends Exception> T wrapThrow(Class<T> throwClass, Exception ex) {

        return ClassWrapper.wrap(throwClass).newOne(ex);
    }
    
    public static <T extends Exception> T wrapThrow(Class<T> throwClass, Exception ex, String fmt,
        Object... args) {

        return ClassWrapper.wrap(throwClass).newOne(ex, String.format(fmt, args));
    }
    
    public static <T extends Exception> T wrapThrow(Class<T> throwClass, String fmt, Object... args) {

        return ClassWrapper.wrap(throwClass).newOne(String.format(fmt, args));
    }
    
    public static <T> List<T> list(T... objs) {

        return Collections.list(objs);
    }
    
    public static <K, V> Map<K, V> map(Object... keyValues) {

        return Maps.map(keyValues);
    }
}
