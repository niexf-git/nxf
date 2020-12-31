package com.cmsz.paas.web.monitoroperation.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoMonitorOperationImpl;
import com.cmsz.paas.web.monitoroperation.dao.GlobalMonitorDao;
@Repository("globalMonitoringDao")
public class GlobalMonitorDaoImpl extends ResponseInfoRestDaoMonitorOperationImpl implements GlobalMonitorDao{

}
