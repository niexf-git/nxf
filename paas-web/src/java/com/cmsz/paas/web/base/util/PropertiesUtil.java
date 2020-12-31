package com.cmsz.paas.web.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoMonitorOperationImpl;
import com.cmsz.paas.common.utils.BasePropertiesUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.constants.Constants;

/**
 * 读取配置文件类
 * 
 * @author fubl
 * 
 */
public class PropertiesUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesUtil.class);

	protected static Properties prop;
	protected static Properties appConfProp;

	static {
		prop = new Properties();
		try {
			InputStream appliaction = PropertiesUtil.class
					.getResourceAsStream("/application.properties");
			InputStream paas = PropertiesUtil.class
					.getResourceAsStream("/paas.properties");
			prop.load(appliaction);
			prop.load(paas);
			logger.debug("Load Properties ok");
		} catch (IOException e) {
			logger.error("Load Properties error:" + e.getMessage(), e);
		}
//		logger.debug("common读取PAAS-20001：" + getValue("PAAS-20001"));
//		logger.debug("web读取db.url：" + getValue("db.url"));
//		logger.debug("web读取queryAppListUrl：" + getValue("queryAppListUrl"));
		ResponseInfoRestDaoImpl.username = prop.getProperty("paasControl.username", "");
		ResponseInfoRestDaoImpl.password =JasypUtil.decrypt(prop.getProperty("paasControl.password", ""));
		
		ResponseInfoRestDaoMonitorOperationImpl.username = prop.getProperty("monitorOperation.username", "");
		ResponseInfoRestDaoMonitorOperationImpl.password =JasypUtil.decrypt(prop.getProperty("monitorOperation.password", ""));
	}

	public static String getValue(String key) {
		try {
			String value = new String(prop.getProperty(key, "").getBytes("iso-8859-1"), "UTF-8");
			if (StringUtils.isBlank(value)) {
				return BasePropertiesUtil.getValue(key);
			}
			return value;
			
		} catch (UnsupportedEncodingException e) {
			logger.error("Get Properties value error:" + e.getMessage(), e);
		}
		return "";
	}

	public static Properties setPropertiesFile(String propfileName) throws PaasWebException {
		appConfProp = new Properties();
		try {			
			InputStream propFileIS = PropertiesUtil.class
					.getResourceAsStream("/" + propfileName);
			appConfProp.load(propFileIS);
			
		} catch (IOException ex) {
			throw new PaasWebException(
					Constants.SYS_LOAD_FILE_IO_EXCEPTION,
					ex.getMessage());
		}
		return appConfProp;
	}
	
	public static String getPropValue(String key) throws PaasWebException{
		String value="";
		try {
			value = new String(appConfProp.getProperty(key, "").getBytes(
					"iso-8859-1"), "UTF-8");
			
		} catch (UnsupportedEncodingException ex) {
			throw new PaasWebException(
					Constants.SYS_LOAD_FILE_IO_EXCEPTION,
					ex.getMessage());
		}
		return value;
	}
}
