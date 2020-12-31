package com.cmsz.paas.web.group4a.dao;

import com.cmsz.paas.common.dao.EntityDao;
import com.cmsz.paas.web.group4a.entity.TaskTimeEntity;


/**
 * @author jiayz
 * @date 2019年11月6日
 */
public interface TaskTimeDao extends EntityDao<TaskTimeEntity, Integer>{
	void deleteTimeDiff(String time);
}
