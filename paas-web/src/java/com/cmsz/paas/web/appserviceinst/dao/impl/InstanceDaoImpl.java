package com.cmsz.paas.web.appserviceinst.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.appserviceinst.dao.InstanceDao;

@Repository("instanceDao")
public class InstanceDaoImpl extends ResponseInfoRestDaoImpl implements
		InstanceDao {

}
