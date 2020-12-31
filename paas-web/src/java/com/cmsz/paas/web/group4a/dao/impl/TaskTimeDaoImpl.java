package com.cmsz.paas.web.group4a.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.group4a.dao.TaskTimeDao;
import com.cmsz.paas.web.group4a.entity.TaskTimeEntity;


/**
 * @author jiayz
 * @date 2019年11月6日
 */
@Repository("taskTimeDao")
public class TaskTimeDaoImpl extends IBatis3DaoSupport<TaskTimeEntity, Integer> implements TaskTimeDao{

	@Override
	public void deleteTimeDiff(String time) {
		getSqlSessionTemplate().delete("deleteTimeDiff", time);
	}

}
