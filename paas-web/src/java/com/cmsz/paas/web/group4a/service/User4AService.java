package com.cmsz.paas.web.group4a.service;



import java.util.List;
import java.util.Map;

import com.cmsz.paas.web.group4a.entity.User4AEntity;


/**
 * @author ccl
 * @date 
 */
public interface User4AService {
	void insert(User4AEntity entity);
	void update(User4AEntity entity);
	List<User4AEntity> findByMap(Map<String, Object> map);
}
