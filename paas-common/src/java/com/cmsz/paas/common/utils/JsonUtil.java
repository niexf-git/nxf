package com.cmsz.paas.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.cmsz.paas.common.model.response.ResponseInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json 简单操作的工具类
 * 
 * @author cmsz
 * 
 */
public class JsonUtil {

	/**
	 * Json转化成bean
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static ResponseInfo json2ResponseInfoBean(String jsonStr, Class<?> cls) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		JSONObject jsonObjectData = jsonObject.getJSONObject("data");
		ResponseInfo responseInfo = (ResponseInfo) JSONObject.toBean(
				jsonObject, ResponseInfo.class);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		responseInfo.setData(gson.fromJson(JsonUtil.parseTimeObjectToJson(jsonObjectData), cls));

		return responseInfo;
	}

	/**
	 * 用json将Object转化为json
	 * 
	 * @param obj
	 * @return
	 */
	public static String gsonParseObjToJson(Object obj) {
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.disableHtmlEscaping()
				.create();
		return gson.toJson(obj);
	}

	/**
	 * 用gson将json转化为obj
	 * 
	 * @param json
	 * @param c
	 * @return
	 */
	public static Object gsonFromJson(String json, Class c) {
		Gson gson = new Gson();
		return gson.fromJson(json.toString(), c);
	}

	/**
	 * 将OBJ用JSONObject的String来描述
	 * 
	 * @param obj
	 * @return
	 */
	public static String parseObjectToJSON(Object obj) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		return jsonObject.toString();
	}

	/**
	 * 将OBJ用JSONObject来描述
	 * 
	 * @param obj
	 * @return
	 */
	public static JSONObject parseObjectToJSONObject(Object obj) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		return jsonObject;
	}

	/**
	 * 将数组转为JSONArray格式
	 * 
	 * @param objs
	 * @return
	 */
	public static JSONArray parseArrayToJsonText(Object[] objs) {
		JSONArray jsonArray = JSONArray.fromObject(objs);
		return jsonArray;
	}

	/**
	 * 将JSON格式的字符串转为JSON对象 格式:("['JSON','is','easy']")
	 * 
	 * @param text
	 * @return
	 */
	public static JSONArray parseJsonTextToArray(String text) {
		JSONArray jsonArray = JSONArray.fromObject(text);
		return jsonArray;
	}

	/**
	 * 将List集合转为JSONArray格式
	 * 
	 * @param list
	 * @return
	 */
	public static JSONArray parseListToJsonText(List list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray;
	}

	/**
	 * 将Map集合转为JSON格式数据
	 * 
	 * @param map
	 * @return
	 */
	public static JSONObject parseMapToJsonText(Map map) {
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	// 将JSON格式的字符串转为JAVABEAN
	public static Object format(String json, Class c) {

		// JSONObject jb = JSONObject.fromObject(json);
		// return JSONObject.toBean(jb, c);
		Gson gson = new Gson();
		return gson.fromJson(json.toString(), c);
	}

	// 将带时间格式的Object转化为json格式
	// add by hehm
	public static String parseTimeObjectToJson(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessor() {
					private SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");

					public Object processObjectValue(String key, Object value,
							JsonConfig jsonConfig) {
						return value == null ? sd.format(new Date(0)) : sd.format(value);
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return value == null ? sd.format(new Date(0)) : sd.format(value);
					}
				});

		return JSONObject.fromObject(object, jsonConfig).toString();
	}

	// 将带时间格式的Array转化为json格式
	// add by hehm
	public static String parseTimeArrayToJson(List list) {

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessor() {
					private SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");

					public Object processObjectValue(String key, Object value,
							JsonConfig jsonConfig) {
						return value == null ? "1970-01-01 00:00:00" : sd.format(value);
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return value == null ? "1970-01-01 00:00:00" : sd.format(value);
					}
				});

		return JSONArray.fromObject(list, jsonConfig).toString();
	}
}