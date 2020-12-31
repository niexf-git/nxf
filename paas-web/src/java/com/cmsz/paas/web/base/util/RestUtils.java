package com.cmsz.paas.web.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.web.constants.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class RestUtils.
 */
public class RestUtils {
	
	/** The Constant log. */
	public static final Logger log = Logger.getLogger(RestUtils.class);
	
	/** The pass proper. */
	public String passProper = "/paas.properties";

	/**
	 * 封装一个rest接口所需的URL.
	 *
	 * @param obj 
	 *    eg:queryAppListUrl
	 * @param objId 
	 *    eg:clusterId
	 * @return the rest interface url
	 */
	public static String restUrlTransform(String obj, Object... objId) {
		
		String restUrl = "";	
		if(Strings.isNotBlank(System.getenv(Constants.HOST))
		   && Strings.isNotBlank(System.getenv(Constants.PORT))){
			 restUrl = Constants.HTTP+
					   Constants.COLON+
					   Constants.SEPARATORS+
					   System.getenv(Constants.HOST)+
					   Constants.COLON+
					   System.getenv(Constants.PORT);
		}else{
			 restUrl = PropertiesUtil.getValue("restServerConn");
		}
		restUrl = restUrl + PropertiesUtil.getValue(obj);		
		if(objId!=null && objId.length!=0){			
			restUrl = MessageFormat.format(restUrl, objId);			
		}	

		return restUrl;
	}
	
	/**
	 * 获取监控运维URL的完整地址.
	 * @param obj
	 * @param objId
	 * @return
	 */
	public static String monitorOperationRestUrl(String obj, Object... objId) {
		String restUrl = PropertiesUtil.getValue("monitorOperationServerConn");			
		restUrl = restUrl + PropertiesUtil.getValue(obj);		
		 
		if(objId!=null && objId.length!=0){			
			restUrl = MessageFormat.format(restUrl, objId);			
		}	

		return restUrl;
	}
	
	/**
	 * 获取grafana url 的完整地址
	 * @param obj
	 * @param objId
	 * @return
	 */
	public static String grafanaRestUrl(String obj, Object... objId) {
		
		String restUrl = PropertiesUtil.getValue("grafanaServerConn");
		
//		String address = "";
//		try {
//			//根据别名返回【别名/ip】
//			address = InetAddress.getByName(Constants.GRAFANA_BYNAME).toString();
//			address=address.split("/")[1];
//			restUrl = restUrl.replaceAll(Constants.GRAFANA_BYNAME, address);
//		} catch (UnknownHostException e) {
//			log.error("获取ip异常", e);
//		}

		restUrl = restUrl + PropertiesUtil.getValue(obj);
		
		if(objId!=null && objId.length!=0){
			restUrl = MessageFormat.format(restUrl, objId);
		}	

		return restUrl;
	}
	
	/**
	 * 获取grafana url 的完整地址  页面使用
	 * @param obj
	 * @param objId
	 * @return
	 */
	public static String grafanaUrl(String obj, Object... objId) {
		
		String restUrl = PropertiesUtil.getValue("grafanaServerIp");
		

		restUrl = restUrl + PropertiesUtil.getValue(obj);
		
		if(objId!=null && objId.length!=0){
			restUrl = MessageFormat.format(restUrl, objId);
		}	

		return restUrl;
	}
	
}
