package com.cmsz.paas.web.cicd.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.cicd.dao.InteTestDao;

/**
 * 集成测试dao实现
 * @author lixin
 * @date 2017-09-04
 */
@Repository("inteTestDaoImpl")
public class InteTestDaoImpl extends ResponseInfoRestDaoImpl
		implements InteTestDao {

}
