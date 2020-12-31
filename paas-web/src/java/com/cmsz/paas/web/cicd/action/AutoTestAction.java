package com.cmsz.paas.web.cicd.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.AutoTestEntity;
import com.cmsz.paas.web.cicd.service.AutoTestService;

/**
 * 自动化测试Action
 * 
 * @author lin.my
 * @version 创建时间：2017年8月28日 上午11:23:10
 */
public class AutoTestAction extends AbstractAction {

	private static final long serialVersionUID = -3608262205573216013L;

	private static final Logger logger = LoggerFactory
			.getLogger(AutoTestAction.class);

	@Autowired
	private AutoTestService autoTestService;

	/** 流水id */
	private String flowId = "";

	/** 接收页面输入的数据 */
	private AutoTestEntity autoTestInfo;

	/**
	 * 查询自动化测试配置信息
	 * 
	 * @throws Exception
	 */
	public void queryAutoTest() throws Exception {
		logger.info("开始执行自动化测试的配置信息，流水id：" + flowId);
		try {
			AutoTestEntity autoTestEntity = autoTestService.queryAutoTest(flowId);
			sendSuccessReslult(autoTestEntity);
			logger.info("查询自动化测试的配置信息成功！");
		} catch (PaasWebException ex) {
			logger.error("查询自动化测试的配置信息错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 修改自动化测试配置信息
	 * 
	 * @throws Exception
	 */
	public void updateAutoTest() throws Exception {
		logger.info("开始执行修改自动化测试的配置信息...");
		try {
			String loginName = (String) getSessionMap().get("loginName");
			autoTestInfo.setSysUer(loginName);
			
			autoTestService.updateAutoTest(autoTestInfo.getFlowId(), autoTestInfo);
			sendSuccessReslult();
			logger.info("修改自动化测试的配置信息成功！");
		} catch (PaasWebException e) {
			logger.error("修改自动化测试的配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public AutoTestService getAutoTestService() {
		return autoTestService;
	}

	public void setAutoTestService(AutoTestService autoTestService) {
		this.autoTestService = autoTestService;
	}

	public AutoTestEntity getAutoTestInfo() {
		return autoTestInfo;
	}

	public void setAutoTestInfo(AutoTestEntity autoTestInfo) {
		this.autoTestInfo = autoTestInfo;
	}

}
