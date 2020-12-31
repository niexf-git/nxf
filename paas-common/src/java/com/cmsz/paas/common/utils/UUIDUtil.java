package com.cmsz.paas.common.utils;

import java.util.UUID;

/**
 * @author zhuzhu
 * 
 */
public class UUIDUtil {

	/**
	 * @return
	 */
	public static String getUUID() {

		return UUID.randomUUID().toString().replace("-", "").toUpperCase();

	}

}
