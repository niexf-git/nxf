package com.cmsz.paas.web.base.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SubPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String[] encryptPropNames = {"db.password"};

	private boolean isEncryptProp(String propertyName) {
		for (String encryptName : encryptPropNames) {
			if (encryptName.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}

	public static String getDecryptString(String str) {
		try {
			return JasypUtil.decrypt(str);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		// 如果在加密属性名单中发现该属性
		if (isEncryptProp(propertyName)) {
			String decryptValue = getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}
	
}
