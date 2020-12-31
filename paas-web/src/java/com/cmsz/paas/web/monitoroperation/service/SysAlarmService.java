package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.monitoroperation.model.SysAlarm;
import com.cmsz.paas.web.monitoroperation.model.SysAlarmConditionInfo;
import com.cmsz.paas.web.monitoroperation.model.SysAlarmInfo;

/**
 * 监控运维模块Service.
 * 
 * @author ccl
 */
public interface SysAlarmService {
	/**
	 * 查询告警详情
	 * 
	 * @throws Exception
	 */
	public Page<SysAlarmInfo> queryAlarmList(PageContext buildPageContext) throws PaasWebException;
	/**
	 * 查询告警条件
	 * 
	 * @throws Exception
	 */
	public SysAlarmConditionInfo queryAlarmCondition() throws PaasWebException;
	/**
	 * 修改告警条件
	 * 
	 * @throws Exception
	 */
	public void modifyAlarmCondition(SysAlarmConditionInfo alarmConditionInfo) throws PaasWebException;
}
