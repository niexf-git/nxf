package com.cmsz.paas.web.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogProcessUtil {
	
	/**
	 * 处理日志内容用于页面显示
	 * @param logContent 日志内容
	 * @return
	 */
	public static String processLogContent(String logContent){
		String result = "";
		//String lineSeparator = System.getProperty("line.separator");
		//替换换行符
		result = logContent.replaceAll("\r\n", "<br>").replaceAll("\r", "<br>");
		Pattern p = Pattern.compile("<a[^>]*>([^<]*)</a>"); 
	    Matcher m = p.matcher(result); 
	    //替换所有的超链接，只保留文字
	    while(m.find()){
	    	result = result.replace(m.group(0), m.group(1));
	    }
	    //处理前面的空格
	    result = result.replaceAll("<br>   ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	    return result;
	}

}
