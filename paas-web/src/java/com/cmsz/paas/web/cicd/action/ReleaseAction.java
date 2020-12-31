package com.cmsz.paas.web.cicd.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.ReleaseEntity;
import com.cmsz.paas.web.cicd.service.ReleaseService;

/**
 * 发布 Action.
 * 
 * @author liaohw
 * @date 2017-11-23
 */
public class ReleaseAction extends AbstractAction {

	private static final long serialVersionUID = -3204996783773856269L;

	private static final Logger logger = LoggerFactory
			.getLogger(ReleaseAction.class);

	@Autowired
	private ReleaseService releaseService;

	/** 流水id */
	private String flowId;
	
	private ReleaseEntity releaseEntity;

	/**
	 * 查询发布的配置信息
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryRelease() throws Exception {
		logger.info("开始执行查询发布的配置信息，流水id：" + flowId);
		try {
			ReleaseEntity releaseEntity = releaseService.queryRelease(flowId);
			sendSuccessReslult(releaseEntity);
			logger.info("查询发布的配置信息成功！");
		} catch (PaasWebException ex) {
			logger.error("查询发布的配置信息错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 修改发布的配置信息
	 */
	public void modifyRelease() throws Exception {
		logger.info("开始执行修改发布的配置信息...");
		logger.info("页面提交的参数：" + releaseEntity + "  流水id："+flowId);// 调试完删除
		try {
			releaseService.modifyRelease(flowId, releaseEntity);
			sendSuccessReslult();
			logger.info("修改发布的配置信息成功！");
		} catch (PaasWebException e) {
			logger.error("修改发布的配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/**
	 * 镜像版本由开发直接发布生产
	 */
	public void releaseProduction()throws Exception {
		logger.info("开始执行镜像版本由开发直接发布生产...");
		try {
			releaseService.releaseProduction(flowId);
			sendSuccessReslult();
			logger.info("镜像版本由开发直接发布生产成功！");
		} catch (PaasWebException e) {
			logger.error("镜像版本由开发直接发布生产错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public ReleaseEntity getReleaseEntity() {
		return releaseEntity;
	}

	public void setReleaseEntity(ReleaseEntity releaseEntity) {
		this.releaseEntity = releaseEntity;
	}
}
