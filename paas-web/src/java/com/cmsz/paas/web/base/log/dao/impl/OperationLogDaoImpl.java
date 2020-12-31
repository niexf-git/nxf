package com.cmsz.paas.web.base.log.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.common.log.OperationLog;
import com.cmsz.paas.web.base.log.dao.OperationLogDao;

/**
 * 审计日志(长连接使用)
 * @author liaohw
 * @date 2015-10-27
 */
@Repository("operationLogDao")
public class OperationLogDaoImpl extends IBatis3DaoSupport<OperationLog, Long> implements OperationLogDao{

}
