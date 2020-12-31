package com.cmsz.paas.web.application.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.application.dao.ApplicationDao;

@Repository("applicationRestDao")
public class ApplicationDaoImpl extends ResponseInfoRestDaoImpl implements ApplicationDao{

}
