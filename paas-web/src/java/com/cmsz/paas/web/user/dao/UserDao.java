package com.cmsz.paas.web.user.dao;

import com.cmsz.paas.common.dao.EntityDao;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.user.entity.User;

/**
 *@Author:zhouyunxia
 *@Date:2015年4月7日 下午5:56:11
 *@Desc: 
 */
public interface UserDao  extends EntityDao<User, Long> {
	
	public void updateSessionId(User user) throws PaasWebException;
}
