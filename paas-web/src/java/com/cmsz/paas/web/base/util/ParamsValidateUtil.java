package com.cmsz.paas.web.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.constants.Constants;

public class ParamsValidateUtil {

	/**
	 * 判断是否包含关键字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isIncludeWord(String value) {
		String keyWords = PropertiesUtil.getValue("keyWords");
		if (Strings.isBlank(keyWords)) {
			return false;
		}
		String[] params = keyWords.split(",");
		for (int j = 0; j < params.length; j++) {
			if (params[j].startsWith("<")) {// js关键字
				if (value.toLowerCase().contains(params[j].toLowerCase())) {
					return true;
				}
			} else {// SQL关键字
				if (value.trim().toLowerCase()
						.contains(" " + params[j].toLowerCase())
						|| value.trim().toLowerCase()
								.contains(params[j].toLowerCase() + " ")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 转义rest url 中的特殊字符
	 * 
	 * @param src
	 * @return 转义后的字符串
	 */
	public static String escapeSpecialChar(String source) {
		String result = "";
		if (Strings.isBlank(source)) {
			return result;
		}
		// 格式 %:%25;?:%3F;
		String value = PropertiesUtil.getValue("restUrlSpecial");
		if (Strings.isBlank(value)) {
			return source;
		} else {
			String[] escapeCharPair = value.split(";");
			for (int i = 0; i < escapeCharPair.length; i++) {
				String[] strArray = escapeCharPair[i].split(":");
				source = source.replaceAll(strArray[0], strArray[1]);
			}
			// paas.properties配置文件没有配置的转义字符
			result = source.replaceAll("\\u0024", "%24").replaceAll(";", "%3B");
		}
		return result;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return
	 */
	public static String sendFailResult() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("resultCode", Constants.PARAMS_VALIDATE_ERROR);
			obj.put("resultMessage", "输入的内容中包含非法字符，如:insert、delete和truncate等");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return obj.toString();
	}

	/**
	 * 把对象里的属性转化存储到Map里，key是字段名称，value是字段值
	 * 
	 * @param object
	 *            任意对象
	 * @return Map对象
	 * @throws Exception
	 */
	public static Map<String, Object> getFiledsMap(Object object)
			throws Exception {
		if (object == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Class<? extends Object> cls = object.getClass();
		// 得到类中的所有属性集合
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();// 属性名称
			// 获取getter方法名
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			Method method = cls.getDeclaredMethod(methodName);
			Object value = method.invoke(object);
			map.put(fieldName, value);
		}
		return map;
	}

	/**
	 * 一个通用的工具方法，实现的功能：传入一个Map（key:类的属性名称，value:对应的值）， 再传入一个任意的数据Model类
	 * 
	 * @param map
	 *            用来包含数据值（key:类的属性名称，value:对应的值）
	 * @param cls
	 *            数据Model类
	 * @return 已经赋了值的对象实例
	 * @throws Exception
	 */
	public static Object setValues(Map<String, Object> map,
			Class<? extends Object> cls) throws Exception {
		// 得到cls类的对象实例
		Object obj = cls.newInstance();
		// 1、使用反射获取这个类的所有属性
		Field field[] = cls.getDeclaredFields();
		// 2、循环这些属性，判断在Map中是否有对应的值
		for (Field f : field) {
			String fieldName = f.getName();// 属性名称
			Object value = map.get(fieldName); // 根据属性名称到Map中获取对应的值
			// 3、如果没有对应的值，就下一个（属性）
			if (value == null) {
				continue;
			} else {
				// 4、如果有对应的值，使用反射的方法，把这个值赋值到对象对应的属性中
				// 4.1、使用属性的名称得到对应的方法名称
				String methodName = "set"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				// 4.2、根据方法名称和属性类型得到方法
				// Method method = cls.getMethod(methodName,
				// f.getType());//公共成员方法
				Class<?> ps[] = new Class[1];
				ps[0] = f.getType();
				Method method = cls.getDeclaredMethod(methodName, ps);
				// 4.3、使用反射来动态调用这个方法，并传入值
				Object os[] = new Object[1];
				os[0] = value;
				method.invoke(obj, os); // method.invoke(obj, value);
			}
		}
		return obj;
	}

	public static Object doMyFilter(Object object) throws Exception {
		Map<String, Object> map = getFiledsMap(object);
		// 字段值处理
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			Object value = map.get(key);
			if (value instanceof String[]) {
				String[] strArray = (String[]) value;
				for (int i = 0; i < strArray.length; i++) {
					if (isIncludeWord(strArray[i])) {
						return sendFailResult();
					} else {
						strArray[i] = strArray[i].replaceAll("<", "&lt;")
								.replaceAll("'", "&apos;")
								.replaceAll("\"", "&quot;");
					}
				}
			} else if (value instanceof String) {
				String str = (String) value;
				if (isIncludeWord(str)) {
					return sendFailResult();
				} else {
					str = str.replaceAll("<", "&lt;").replaceAll("'", "&apos;")
							.replaceAll("\"", "&quot;");
					map.put(key, str);
				}
			}
		}
		return setValues(map, object.getClass());
	}

	public static void main(String[] args) throws Exception {
		AppService v = new AppService();
		v.setApp_id("<100");
		String[] envKeys = { "a<", "b" };
		String[] envValues = { "c", "d<" };
		v.setAppConfKey(envKeys);
		v.setAppConfValue(envValues);
		v.setConfig_effect("123");
		Object obj = doMyFilter(v);
		System.out.println(obj);
	}

}
