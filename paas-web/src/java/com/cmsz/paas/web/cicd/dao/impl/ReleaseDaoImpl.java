package com.cmsz.paas.web.cicd.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.cicd.dao.ReleaseDao;

@Repository("releaseDao")
public class ReleaseDaoImpl extends ResponseInfoRestDaoImpl implements
		ReleaseDao {

}
