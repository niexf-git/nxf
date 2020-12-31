package com.cmsz.paas.common.utils;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.ObjectUtils;

/**
 * @author zhuzhu
 * 
 */
public class BooleanUtil extends ObjectUtils {

	/**
	 * @param obj
	 * @param objects
	 * @return
	 */
	public static boolean anyEmpty(Object obj, Object... objects) {

		if (null == obj) {
			return true;
		}
		if (obj instanceof String && 0 == ((String) obj).trim().length()) {
			return true;
		}
		if (obj instanceof Object[] && 0 == ((Object[]) obj).length) {
			return true;
		} else if(obj instanceof Object[]) {
			for (Object object : (Object[]) obj) {
				if (anyEmpty(object)) {
					return true;
				}
			}
		} else if (obj instanceof Collection<?> && ((Collection<?>) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Collection<?>){
			for (Object object : (Collection<?>) obj) {
				if (anyEmpty(object)) {
					return true;
				}
			}
		} else if (obj instanceof Map<?, ?> && ((Map<?, ?>) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map<?, ?>) {
			for (Object object : ((Map<?, ?>) obj).values()) {
				if (anyEmpty(object)) {
					return true;
				}
			}
		}
		if (objects.length > 0) {
			for (Object object : objects) {
				if (anyEmpty(object)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param obj
	 * @param objects
	 * @return
	 */
	public static boolean isEmpty(Object obj, Object... objects) {

		if (null == obj) {
			return true;
		}
		if (obj instanceof Collection<?> && 0 == ((Collection<?>) obj).size()) {
			return true;
		}
		if (obj instanceof String && ((String) obj).trim().equals("")) {
			return true;
		}
		if (objects.length > 0) {
			for (Object object : objects) {
				if (null == object) {
					return true;
				}
				if (object instanceof Collection<?> && 0 == ((Collection<?>) object).size()) {
					return true;
				}
				if (object instanceof String && ((String) object).trim().equals("")) {
					return true;
				}
			}
		}
		return false;
	}
}
