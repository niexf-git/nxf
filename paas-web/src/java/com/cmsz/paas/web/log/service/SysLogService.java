package com.cmsz.paas.web.log.service;

import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.log.entity.SysLogEntity;

/**
 * 审计日志服务接口
 * @author li.lv
 * 2015-4-16	
 */
public interface SysLogService {
	/**
	 * 分页查询审计日志信息
	 * @param pageContext
	 * @return
	 * @throws Exception
	 */
	public Page<SysLogEntity> findPage(PageContext pc) throws Exception;
	
	/**
	 * 按时间查询审计日志信息
	 * @param pageContext
	 * @return
	 * @throws Exception
	 */
	public List<SysLogEntity> findByMap(Map<String,Object> map) throws Exception;
}
