package com.cmsz.paas.web.cicd.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.cicd.dao.QueryReportDao;
/**
 * 查询报告dao
 * @author jiayz
 */
@Repository("queryReportDao")
public class QueryReportDaoImpl extends ResponseInfoRestDaoImpl implements QueryReportDao{

}
