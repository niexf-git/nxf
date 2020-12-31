package com.cmsz.paas.web.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.user.dao.UserRoleRelationDao;
import com.cmsz.paas.web.user.entity.UserRoleRelation;

/**
 * 用户角色管理DAO
 * @author zhouyunxia
 *
 */
@Repository("UserRoleRelationDao")
public class UserRoleRelationDaoImpl  extends IBatis3DaoSupport<UserRoleRelation, Long> implements UserRoleRelationDao {

}
