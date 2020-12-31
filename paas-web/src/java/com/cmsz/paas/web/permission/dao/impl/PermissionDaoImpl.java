package com.cmsz.paas.web.permission.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.permission.dao.PermissionDao;
import com.cmsz.paas.web.permission.entity.OperPermission;

/**
 * 权限DAO
 * @author zhouyunxia
 *
 */
@Repository("PermissionDao")
public class PermissionDaoImpl   extends IBatis3DaoSupport<OperPermission, String> implements PermissionDao {

}
