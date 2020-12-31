package com.cmsz.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.role.dao.RoleDao;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.vo.WorkGroupVO;
import com.cmsz.ws.GroupService;

/** 
 * @author 林绵炎
 * @version 创建时间：2016年11月23日 下午3:27:57
 */
@Service("groupService")
public class GroupServiceImpl implements GroupService {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
	
	@Autowired
	private RoleDao roleDao;

	/*
	 * 功能:
	 * 参数：为空值时返回多个对象
	 * @see com.cmsz.ws.GroupService#queryWorkGroup(java.lang.String)
	 */
	@Override
	public List<WorkGroupVO> queryWorkGroup(String groupId) {
		
		logger.info("查询工作组（角色），Id：" + groupId);
		
		List<WorkGroupVO> voList = new ArrayList<WorkGroupVO>();
		WorkGroupVO vo = null;
		
		if ("".equals(groupId) || groupId == "" || groupId == null) {
			List<Role> roleList = roleDao.findAll(); // 参数为空查全部
			for(Role role : roleList){
				vo = new WorkGroupVO();
				vo.setGroupId(role.getId().toString());
				vo.setName(role.getRoleName());
				vo.setGroupType(role.getType() + "");
				vo.setNote(role.getDescription());
				vo.setSafeMode("");
				vo.setValidBegin(""); // 接口文档是Date类型
				vo.setValidEnd(""); // 接口文档是Date类型
				voList.add(vo);
			}
		}else{ //参数不为空，根据id查询
			vo = new WorkGroupVO();
			Role role = roleDao.findById(Long.parseLong(groupId));
			vo.setGroupId(role.getId().toString());
			vo.setName(role.getRoleName());
			vo.setGroupType(role.getType() + "");
			vo.setNote(role.getDescription());
			vo.setSafeMode("");
			vo.setValidBegin(""); // 接口文档是Date类型
			vo.setValidEnd(""); // 接口文档是Date类型
			voList.add(vo);
		}
		return voList;
	}

}
