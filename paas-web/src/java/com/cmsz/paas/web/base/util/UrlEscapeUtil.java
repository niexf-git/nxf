package com.cmsz.paas.web.base.util;

import org.apache.commons.lang.StringUtils;

public class UrlEscapeUtil {

	/**
	 * 处理rest url 里的特殊字符
	 * @param url
	 * @return
	 */
	public static String changeSpecialChar(String url){
		String str = url;
		if(StringUtils.isNotBlank(str)){
			str = str.trim();
			str = str.replaceAll(" ", "%20")
					.replaceAll("/", "%2F")
					.replaceAll(":", "%3A")
					.replaceAll("\\u0024", "%24");
		}
		return str;
	} 
	
}
