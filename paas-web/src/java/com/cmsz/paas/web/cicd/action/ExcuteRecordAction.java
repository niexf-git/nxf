package com.cmsz.paas.web.cicd.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.cicd.model.FlowExcuteRecordEntity;
import com.cmsz.paas.web.cicd.model.FlowStepRecordEntity;
import com.cmsz.paas.web.cicd.model.StepRecodeEntity;
import com.cmsz.paas.web.cicd.service.ExcuteRecordService;

/**
 * 执行记录Action
 * 
 * @author lin.my
 * @version 创建时间：2017年9月5日 下午4:35:26
 */
public class ExcuteRecordAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ExcuteRecordAction.class);
	private String flowId;// 流水ID
	private String uuid;// sonarID
	private String problem;// 问题类型
	private String codePath;// 问题代码路径

	/** 步骤id 添加人:jiangwei */
	private String stepId;
	/** 步骤名称 添加人:jiangwei */
	private String stepName;

	private String flowRecordId = "";

	@Autowired
	private ExcuteRecordService flowManagerService;

	/***
	 * 查询步骤构建记录列表
	 */
	public void queryExcuteRecord() {
		logger.info("开始执行查询步骤构建记录列表，流水id:" + flowId + ",步骤id:" + stepId
				+ ",步骤名称:" + stepName);
		try {
			List<StepRecodeEntity> stepRecodeList = flowManagerService
					.queryStepRecodeList(flowId, stepName);
			logger.info("查询执行记录构建列表成功！");
			this.sendSuccessReslult(stepRecodeList);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询步骤构建记录列表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 流水执行记录列表展示
	 * 
	 * @author lin.my
	 */
	public void queryFlowExcuteRecordList() {

		logger.info("开始执行流水执行记录列表，流水id：" + flowId);
		try {
			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			String page = pc.getBound().getOffset() + ""; // 当前页数
			String rows = pc.getBound().getLimit() + ""; // 每页条数

			Page<FlowExcuteRecordEntity> flowExcuteRecordList = flowManagerService
					.queryFlowExcuteRecordList(flowId, page, rows);
			sendSuccessReslult(flowExcuteRecordList);
			logger.info("流水执行记录列表成功！");
		} catch (PaasWebException ex) {
			logger.error("流水执行记录列表错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/***
	 * 流水执行记录
	 * 
	 * @author lin.my
	 */
	public void queryFlowExcuteRecord() {
		logger.info("开始执行流水执行记录，流水id:" + flowId + ",流水步骤id:" + flowRecordId);
		try {
			List<FlowStepRecordEntity> flowExcuteRecord = flowManagerService
					.queryFlowExcuteRecord(flowId, flowRecordId);
			logger.info("查询流水执行记录成功！");
			this.sendSuccessReslult(flowExcuteRecord);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询流水执行记录出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/***
	 * 查询单步骤构建记录日志
	 */
	public void queryBuildLogs() {
		logger.info("开始执行单步骤构建日志，流水ID：" + flowId + ",步骤ID：" + stepId);
		try {
			RestResult result = flowManagerService.queryBuildLogs(flowId,
					stepId, stepName);
			logger.info("查询单步骤记录日志完成");
			sendSuccessReslult(result.getData());
		} catch (PaasWebException ex) {
			logger.error("查询单步骤记录日志错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public String getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(String flowRecordId) {
		this.flowRecordId = flowRecordId;
	}

}
