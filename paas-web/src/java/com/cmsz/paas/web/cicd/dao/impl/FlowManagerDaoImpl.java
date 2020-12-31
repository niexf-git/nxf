package com.cmsz.paas.web.cicd.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.cicd.dao.FlowManagerDao;

/**
 * 流水管理dao实现
 * @author jiayz
 */
@Repository("flowManagerDao")
public class FlowManagerDaoImpl extends ResponseInfoRestDaoImpl implements FlowManagerDao{

}
