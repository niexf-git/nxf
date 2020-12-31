package com.cmsz.paas.web.overview.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.overview.model.ActualResourceRate;
import com.cmsz.paas.web.overview.model.AssignedResourceRate;
import com.cmsz.paas.web.overview.model.FlowBuildState;
import com.cmsz.paas.web.overview.model.InstanceState;
import com.cmsz.paas.web.overview.model.ServiceState;
import com.cmsz.paas.web.overview.model.AlarmDetailsList;
import com.cmsz.paas.web.overview.model.TotalResource;
import com.cmsz.paas.web.overview.service.OverviewService;




/**
 * 总览模块 Action.
 * 
 * @author liaohw
 * @date 2018-5-8
 */
@UnLogging
public class OverviewAction extends AbstractAction {

	private static final long serialVersionUID = -4473823827841190819L;

	private static final Logger logger = LoggerFactory.getLogger(OverviewAction.class);

	/** 总览 service对象 . */
	@Autowired
	private OverviewService overviewService;

	/**
	 * 查询服务状态统计数据
	 * 
	 * @throws Exception
	 */
	public void queryServiceState() throws Exception {
		logger.info("开始执行查询服务状态统计数据");
		try {
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			ServiceState serviceState = overviewService.queryServiceState(appIds, operType);
			sendSuccessReslult(serviceState);
			logger.info("查询服务状态统计数据完成");
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询服务状态统计数据错误", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 查询实例状态统计数据
	 * 
	 * @throws Exception
	 */
	public void queryInstanceState() throws Exception {
		logger.info("开始执行查询实例状态统计数据");
		try {
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			InstanceState instanceState = overviewService.queryInstanceState(appIds, operType);
			sendSuccessReslult(instanceState);
			logger.info("查询实例状态统计数据完成");
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询实例状态统计数据错误", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 查询已分配资源利用率数据
	 * 
	 * @throws Exception
	 */
	public void queryAssignedResourceRate() throws Exception {
		logger.info("开始执行查询已分配资源利用率数据");
		try {
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			AssignedResourceRate assignedResourceRate = overviewService.queryAssignedResourceRate(appIds, operType);
			sendSuccessReslult(assignedResourceRate);
			logger.info("查询已分配资源利用率数据完成");
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询已分配资源利用率数据错误", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 查询实际使用资源利用率
	 * 
	 * @throws Exception
	 */
	public void queryActualResourceRate() throws Exception {
		logger.info("开始执行查询实际使用资源利用率数据");
		try {
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			ActualResourceRate actualResourceRate = overviewService.queryActualResourceRate(appIds, operType);
			sendSuccessReslult(actualResourceRate);
			logger.info("查询实际使用资源利用率数据完成");
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询实际使用资源利用率数据错误", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 查询流水构建状态
	 * 
	 * @throws Exception
	 */
	public void queryFlowBuildState() throws Exception {
		logger.info("开始执行查询流水构建状态数据");
		try {
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			FlowBuildState flowBuildState = overviewService.queryFlowBuildState(appIds, operType);
			sendSuccessReslult(flowBuildState);
			logger.info("查询查询流水构建状态数据完成");
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询查询流水构建状态数据错误", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/**
	 * 查询告警统计数据
	 * 
	 * @throws Exception
	 */
	public void queryAlarmDetails() throws Exception {
		logger.info("开始执行查询告警统计数据");
		try {
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			AlarmDetailsList alarmDetailsList = overviewService.queryAlarmDetails(appIds, operType);
			sendSuccessReslult(alarmDetailsList);
			logger.info("查询告警统计数据完成");
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询告警统计数据错误", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/**
	 * 查询资源统计数据
	 * 
	 * @throws Exception
	 */
	public void queryTotalResource() throws Exception {
		logger.info("开始执行查询资源统计数据");
		try {
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			TotalResource alarmDetailsList = overviewService.queryTotalResource(appIds, operType);
			sendSuccessReslult(alarmDetailsList);
			logger.info("查询资源统计数据完成");
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询资源统计数据错误", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

}


