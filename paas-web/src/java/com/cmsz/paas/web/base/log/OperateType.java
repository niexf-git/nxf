package com.cmsz.paas.web.base.log;


import java.util.List;

import com.cmsz.paas.common.lang.Lang;
import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.lang.collection.Collections;
import com.cmsz.paas.common.lang.collection.Lists;
import com.cmsz.paas.web.base.util.PropertiesUtil;

/**
 * 日志操作类型，用来区分是导出、导入、查询、打印等等，默认情况下会从application.properties读取配置信息，
 * 配置规格如下：
 * <p>
 *  logging.operateType=操作类型方法名前缀：操作类型名称;.....
 * </p>
 * @author Sam
 *
 */
public class OperateType {
	public static OperateType create(String config){
		if (Strings.isNotBlank(config)) {
			String[] strs = config.split(":");
			OperateType t = new OperateType();
			t.setName(strs[0]);
			t.setMethodNamePrefix(Lists.of(strs[1].split(",")));
			return t;
		}
		return null;
	}
	public static List<OperateType> getAllDefined() {
		List<OperateType> types = Lists.newList();
		//String config = ApplicationConfig.getOperateType();
		String config = PropertiesUtil.getValue("logging.operateType");
		if (Strings.isNotBlank(config)) {
			String [] strs = config.split(";");
			for (String s : strs) {
				OperateType t = create(s);
				if (t != null) {
					types.add(t);
				}
			}
		}
		return types;
	}
	
	public static void main(String[] s) {
		Lang.println(OperateType.getAllDefined());
	}
	private String name;
	private List<String> methodNamePrefix;
	public List<String> getMethodNamePrefix() {
		return methodNamePrefix;
	}
	public String getName() {
		return name;
	}
	
	public boolean isMe(String methodName) {
		if (Collections.isNotEmpty(methodNamePrefix) && Strings.isNotBlank(methodName)){
			for (String prefix:methodNamePrefix) {
				if (methodName.startsWith(prefix)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setMethodNamePrefix(List<String> methodNamePrefix) {
		this.methodNamePrefix = methodNamePrefix;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("类型：%s,方法名前缀定义:%s\n", name,methodNamePrefix);
	}
}
