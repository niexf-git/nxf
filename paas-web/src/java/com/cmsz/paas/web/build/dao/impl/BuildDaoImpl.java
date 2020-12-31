package com.cmsz.paas.web.build.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.build.dao.BuildDao;

@Repository("buildDao")
public class BuildDaoImpl extends ResponseInfoRestDaoImpl implements BuildDao {

}
