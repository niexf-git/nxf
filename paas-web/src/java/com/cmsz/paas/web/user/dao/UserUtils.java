package com.cmsz.paas.web.user.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.spring.Springs;

public class UserUtils {
	private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);
	
	private static UserDao userDao = Springs
			.getBean("userDao");
	
	
	public static String getNewUserSessionId(Long id){
		return userDao.findById(id).getSessionId();
	}
}
