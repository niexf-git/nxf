package com.cmsz.paas.web.group4a.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.group4a.dao.User4ADao;
import com.cmsz.paas.web.group4a.entity.User4AEntity;


/**
 * @author CCL
 * @date 
 */
@Repository("user4ADao")
public class User4ADaoImpl extends IBatis3DaoSupport<User4AEntity, Integer> implements User4ADao{
	
}
