package com.cmsz.paas.web.group4a.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.group4a.dao.User4ADao;
import com.cmsz.paas.web.group4a.entity.User4AEntity;
import com.cmsz.paas.web.group4a.service.User4AService;


/**
 * @author ccl
 * @date 
 */
@Service("user4AService")
public class User4AServiceImpl implements User4AService{
	@Autowired
	private User4ADao user4ADao;

	@Override
	public void insert(User4AEntity entity) {
		user4ADao.insert(entity);
	}

	@Override
	public List<User4AEntity> findByMap(Map<String, Object> map){
		return user4ADao.findBy(map);
	}

	@Override
	public void update(User4AEntity entity) {
		user4ADao.update(entity);
	}

}
