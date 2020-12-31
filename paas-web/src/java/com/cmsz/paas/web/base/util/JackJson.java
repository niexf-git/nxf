package com.cmsz.paas.web.base.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author pengfan
 * 
 */
public class JackJson {

	private static final Logger logger = LoggerFactory
			.getLogger(JackJson.class);
	/** 格式化时间的string */
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static <T> T fromJsonToObject(String json, Class<T> valueType) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonParseException e) {
			logger.error("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static <T> T fromJsonToObject(String json,
			TypeReference<T> typeReference) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			return (T)mapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			logger.error("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectHasDateToJson(Object object) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setDateFormat(
				new SimpleDateFormat(DATE_TIME_FORMAT));
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectHasDateToJson(Object object,
			String dateTimeFormatString) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setDateFormat(
				new SimpleDateFormat(dateTimeFormatString));
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectToJson(Object object) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}
}
