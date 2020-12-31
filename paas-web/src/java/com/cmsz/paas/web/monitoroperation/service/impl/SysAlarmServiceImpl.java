package com.cmsz.paas.web.monitoroperation.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.monitor.request.AlarmCondition;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.dao.SysAlarmDao;
import com.cmsz.paas.web.monitoroperation.model.SysAlarm;
import com.cmsz.paas.web.monitoroperation.model.SysAlarmConditionInfo;
import com.cmsz.paas.web.monitoroperation.model.SysAlarmInfo;
import com.cmsz.paas.web.monitoroperation.service.SysAlarmService;

/**
 * 监控运维模块Service.
 * 
 * @author ccl
 */
@Service("sysAlarmService")
public class SysAlarmServiceImpl implements SysAlarmService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(SysAlarmServiceImpl.class);
	/** The sysMonitoroperationDao. */
	@Autowired
	private SysAlarmDao SysAlarmDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<SysAlarmInfo> queryAlarmList(PageContext buildPageContext)
			throws PaasWebException {
		Page<SysAlarmInfo> list = new Page<SysAlarmInfo>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl("queryAlarmListUrl");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = SysAlarmDao.update(url,buildPageContext,
					Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			list = (Page) responseInfo.getData();
		} catch (Exception ex) {
			logger.error("查询告警详情错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_SYSALARM_LIST_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}

	@Override
	public SysAlarmConditionInfo queryAlarmCondition() throws PaasWebException {
		SysAlarmConditionInfo alarmConditionInfo = new SysAlarmConditionInfo();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.monitorOperationRestUrl("queryAlarmConditionUrl");
		logger.debug("开始调用查询告警条件的rest接口：" + url);
		try {
			ResponseInfo responseInfo = SysAlarmDao.get(url,
					AlarmCondition.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询告警条件的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			AlarmCondition alarmCondition = (AlarmCondition) responseInfo
					.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (alarmCondition != null) {
					alarmConditionInfo.setCpu(alarmCondition.getCpu());
					alarmConditionInfo.setFilesystem(alarmCondition.getFilesystem());
					alarmConditionInfo.setMemory(alarmCondition.getMemory());
				}
				logger.info("调用查询告警条件Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询告警条件出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_SYSALARM_CONDITION_ERROR
						+ "]查询告警条件出错", ex);
				throw new PaasWebException(
						Constants.QUERY_SYSALARM_CONDITION_ERROR, ex.getMessage());
			}
		}
		return alarmConditionInfo;
	}

	@Override
	public void modifyAlarmCondition(SysAlarmConditionInfo alarmConditionInfo)
			throws PaasWebException {
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			AlarmCondition alarmCondition = translateSysAlarmConditionInfo(alarmConditionInfo);
			String url = RestUtils.monitorOperationRestUrl(
					"modifyAlarmConditionUrl");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = SysAlarmDao.update(url,
					alarmCondition, AlarmCondition.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问修改告警条件的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用修改告警条件的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用修改告警条件的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("修改告警条件错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_SYSALARM_CONDITION_ERROR,
						e.getMessage());
			}
		}

	}

	private AlarmCondition translateSysAlarmConditionInfo(
			SysAlarmConditionInfo alarmConditionInfo) {
		AlarmCondition alarmCondition = new AlarmCondition();
		alarmCondition.setCpu(alarmConditionInfo.getCpu());
		alarmCondition.setMemory(alarmConditionInfo.getMemory());
		alarmCondition.setFilesystem(alarmConditionInfo.getFilesystem());
		return alarmCondition;
	}



}
