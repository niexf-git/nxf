package com.cmsz.paas.web.log.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.log.dao.SysLogDao;
import com.cmsz.paas.web.log.entity.SysLogEntity;
import com.cmsz.paas.web.log.service.SysLogService;

/**
 * 审计日志服务类
 * @author li.lv
 * 2015-4-16	
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService{
	
	@Autowired
	private SysLogDao sysLogDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);


	@Override
	public Page<SysLogEntity> findPage(PageContext pc) throws Exception {
		Page<SysLogEntity> page = new Page<SysLogEntity>();
		try{
			page = sysLogDao.findPage(pc);
			return page;
		} catch (PaasWebException ex) {
			logger.error("分页查询审计日志信息异常",ex);
			throw new PaasWebException(Constants.QUERY_SYS_LOG_ERROR,
					ex.getMessage());
		}
	}


	@Override
	public List<SysLogEntity> findByMap(Map<String, Object> map)
			throws Exception {
		try{
			return sysLogDao.findBy(map);
		} catch (PaasWebException ex) {
			logger.error("查询审计日志信息异常",ex);
			throw new PaasWebException(Constants.QUERY_SYS_LOG_ERROR,
					ex.getMessage());
		}
	}
	
}
