/**
 * @author li.lv
 */
package com.cmsz.paas.web.log.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.log.dao.SysLogDao;
import com.cmsz.paas.web.log.entity.SysLogEntity;

/**
 * @author li.lv
 * 2015-4-16	
 */
@Repository("sysLogDao")
public class SysLogDaoImpl extends IBatis3DaoSupport<SysLogEntity, String> implements SysLogDao{

}
