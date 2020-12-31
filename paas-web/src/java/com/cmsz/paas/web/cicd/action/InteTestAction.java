package com.cmsz.paas.web.cicd.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.InteTestEntity;
import com.cmsz.paas.web.cicd.service.InteTestService;

/**
 * 集成测试Action
 * 
 * @author lixin
 * @version 创建时间：2017年9月4日 下午14:02:00
 */
public class InteTestAction extends AbstractAction {

	private static final long serialVersionUID = -3608262205573216013L;

	private static final Logger logger = LoggerFactory
			.getLogger(InteTestAction.class);
	@Autowired
	private InteTestService inteTestService;

	/** 流水id */
	private String flowId = "";

	/** 接收页面输入的数据 */
	private InteTestEntity inteTestEntity;
	
	/**
	 * 查询集成测试配置信息
	 */
	@UnLogging
	public void queryInteTest() {
		logger.info("开始执行集成测试的配置信息，流水id：" + flowId);
		try {
			InteTestEntity inteTestEntity = inteTestService.queryInteTest(flowId);
			sendSuccessReslult(inteTestEntity);
			logger.info("查询集成测试的配置信息成功！");
		} catch (PaasWebException ex) {
			logger.error("查询集成测试的配置信息错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 修改集成测试配置信息
	 */
	public void updateInteTest() throws Exception {
		logger.info("开始执行修改集成测试的配置信息...");
		//logger.info("页面提交的参数：" + inteTestEntity.toString()+"  流水id："+flowId);
		try {
			String loginName = (String) getSessionMap().get("loginName");
			inteTestEntity.setSysUer(loginName);
			
			inteTestService.updateInteTest(flowId, inteTestEntity);
			sendSuccessReslult();
			logger.info("修改集成测试的配置信息成功！");
		} catch (PaasWebException e) {
			logger.error("修改集成测试的配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public InteTestEntity getInteTestEntity() {
		return inteTestEntity;
	}

	public void setInteTestEntity(InteTestEntity inteTestEntity) {
		this.inteTestEntity = inteTestEntity;
	}

	
}
