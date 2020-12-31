package com.cmsz.paas.web.group4a.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.exception.ApplicationException;
import com.cmsz.paas.web.group4a.dao.TaskTimeDao;
import com.cmsz.paas.web.group4a.entity.TaskTimeEntity;
import com.cmsz.paas.web.group4a.service.TaskTimeService;


/**
 * @author jiayz
 * @date 2019年11月6日
 */
@Service
public class TaskTimeServiceImpl implements TaskTimeService{
	@Autowired
	private TaskTimeDao dao;
	@Override
	public void insert(TaskTimeEntity entity) throws ApplicationException {
		dao.insert(entity);
	}

	@Override
	public void update(TaskTimeEntity entity) throws ApplicationException {
		dao.update(entity);
	}

	@Override
	public void delete(TaskTimeEntity entity) throws ApplicationException {
		dao.delete(entity);
	}

	@Override
	public List<TaskTimeEntity> findByMap(Map<String, Object> map)
			throws ApplicationException {
		return dao.findBy(map);
	}

	@Override
	public void deleteTimeDiff(String time)
			throws ApplicationException {
		dao.deleteTimeDiff(time);
	}

}
