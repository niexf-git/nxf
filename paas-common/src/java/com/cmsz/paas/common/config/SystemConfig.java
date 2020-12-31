package com.cmsz.paas.common.config;

import java.util.Properties;

public class SystemConfig {
	public static Properties p;

	public static String getValue(String key) {
		if (p.get(key) != null) {
			return String.valueOf(p.get(key));
		} else {
			return null;
		}
	}
}
