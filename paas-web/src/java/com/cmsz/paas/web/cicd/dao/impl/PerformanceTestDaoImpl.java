package com.cmsz.paas.web.cicd.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.cicd.dao.PerformanceTestDao;

@Repository("performanceTestDaoImpl")
public class PerformanceTestDaoImpl extends ResponseInfoRestDaoImpl
		implements PerformanceTestDao {

}
