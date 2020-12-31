package com.cmsz.paas.web.role.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.role.dao.RoleDao;
import com.cmsz.paas.web.role.entity.Role;


/**
 * role表数据库实现
 * 
 * @author zhouyunxia
 * 
 */
@Repository("RoleDao")
public class RoleDaoImpl  extends IBatis3DaoSupport<Role, Long> implements RoleDao {
	
}
