package com.cmsz.paas.web.role.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.role.dao.RolePerRelationDao;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;

/**
 * 角色权限管理DAO
 * @author zhouyunxia
 *
 */
@Repository("RolePerRelationDao")
public class RolePerRelationDaoImpl  extends IBatis3DaoSupport<RolePermissionRelation, Long> implements RolePerRelationDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<RolePermissionRelation> findUserPermission(Map<String, Long> parameter) {
        return getSqlSessionTemplate().selectList(sql("findUserPermission"), parameter);
    }
}