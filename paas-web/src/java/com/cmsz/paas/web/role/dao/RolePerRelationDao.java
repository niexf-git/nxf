package com.cmsz.paas.web.role.dao;

import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.dao.EntityDao;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;

/**
 * 角色权限管理DAO
 * @author zhouyunxia
 *
 */
public interface RolePerRelationDao extends EntityDao<RolePermissionRelation, Long> {
	public List<RolePermissionRelation> findUserPermission(Map<String, Long> parameter);
}
