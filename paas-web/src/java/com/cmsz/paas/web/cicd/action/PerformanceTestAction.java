package com.cmsz.paas.web.cicd.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;

import com.cmsz.paas.web.cicd.model.PerformanceTestEntity;
import com.cmsz.paas.web.cicd.service.PerformanceTestService;


/**
 * 性能测试Action
 * 
 * @author lixin
 * @version 创建时间：2017年11月23日 下午14:42:00
 */
public class PerformanceTestAction extends AbstractAction {

	private static final long serialVersionUID = -3608262205573216013L;

	private static final Logger logger = LoggerFactory
			.getLogger(PerformanceTestAction.class);
	@Autowired
	private PerformanceTestService performanceTestService;

	/** 流水id */
	private String flowId = "";

	/** 接收页面输入的数据 */
	private PerformanceTestEntity performanceTestEntity;
	
	/**
	 * 查询性能测试配置信息
	 */
	@UnLogging
	public void queryPerformanceTest() {
		logger.info("开始查询性能测试的配置信息，流水id：" + flowId);
		try {
			PerformanceTestEntity performanceTestEntity = performanceTestService.queryPerformanceTest(flowId);
			sendSuccessReslult(performanceTestEntity);
			logger.info("查询性能测试的配置信息成功！");
		} catch (PaasWebException ex) {
			logger.error("查询性能测试的配置信息错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 修改性能测试配置信息
	 */
	public void updatePerformanceTest() throws Exception {
		logger.info("开始执行修改性能测试的配置信息...");
		//logger.info("页面提交的参数：" + performanceTestEntity.toString()+"  流水id："+flowId);
		try {
			String loginName = (String) getSessionMap().get("loginName");
			performanceTestEntity.setSysUer(loginName);
			performanceTestService.updatePerformanceTest(flowId, performanceTestEntity);
			sendSuccessReslult();
			logger.info("修改性能测试的配置信息成功！");
		} catch (PaasWebException e) {
			logger.error("修改性能测试的配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public PerformanceTestEntity getPerformanceTestEntity() {
		return performanceTestEntity;
	}

	public void setPerformanceTestEntity(PerformanceTestEntity performanceTestEntity) {
		this.performanceTestEntity = performanceTestEntity;
	}

	
}
