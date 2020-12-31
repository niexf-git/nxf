package com.cmsz.paas.web.base.log;


import java.util.Map;

import com.cmsz.paas.common.lang.Lang;
import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.google.common.collect.Maps;

/**
 * 日志操作功能，用来区分是应用管理、实例管理等等，默认情况下会从application.properties读取配置信息，
 * 配置规格如下：
 * <p>
 *  logging.operateFunc=操作功能：namespace;.....
 * </p>
 * @author Sam
 *
 */
public class OperateFunc {
	public static Map<String,String> getAllDefined() {
		Map<String,String> funcs = Maps.newHashMap();
		String config = PropertiesUtil.getValue("logging.operateFunc");
		if (Strings.isNotBlank(config)) {
			String [] strs = config.split(";");
			for (String s : strs) {
				if (Strings.isNotBlank(s)) {
					String[] str = s.split(":");
					funcs.put(str[1], str[0]);
				}
			}
		}
		return funcs;
	}
	
	public static void main(String[] s) {
		Lang.println(OperateFunc.getAllDefined());
		Lang.println(OperateFunc.getAllDefined().get("/app"));
	}
}
