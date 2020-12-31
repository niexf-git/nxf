package com.cmsz.paas.web.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.user.dao.UserDao;
import com.cmsz.paas.web.user.entity.User;

/**
 *@Author:zhouyunxia
 *@Date:2015年4月7日 下午5:57:55
 *@Desc: 
 */
@Repository("userDao")
public class UserDaoImpl extends IBatis3DaoSupport<User, Long> implements UserDao {

	@Override
	public void updateSessionId(User user) throws PaasWebException {
		 getSqlSessionTemplate().update(User.class.getName()+".updateSessionId",user);
		
	}
	
}
