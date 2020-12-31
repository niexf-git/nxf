package com.cmsz.paas.common.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 读取配置文件类
 * @author fubl
 * 
 */
public class BasePropertiesUtil {
	
	protected static Properties prop;
	
	static {
		prop = new Properties();
		try {
			InputStream paas_common_exp = BasePropertiesUtil.class.getResourceAsStream("/config/exception.properties");
			prop.load(paas_common_exp);
			System.out.println("Paas common Load Properties ok");
		} catch (IOException e) {
			System.out.println("Paas common Load Properties error:"+e.getMessage());
		}
	}

	public static String getValue(String key) {
		try {
			return new String(prop.getProperty(key,"").getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Get paas common Properties value error:"+e.getMessage());
		}
		return "";
	}
	
}
