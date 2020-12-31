package com.cmsz.paas.web.cicd.service;

import java.util.List;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.cicd.model.FlowInfo;
import com.cmsz.paas.web.cicd.model.FlowExcuteRecordEntity;
import com.cmsz.paas.web.cicd.model.FlowStepRecordEntity;
import com.cmsz.paas.web.cicd.model.StepRecodeEntity;
/**
 * 执行记录Service
 * 
 * @author lin.my
 * @version 创建时间：2017年9月5日 下午4:39:31
 */
public interface ExcuteRecordService {

	/**
	 * 流水执行记录列表展示
	 */
	public Page<FlowExcuteRecordEntity> queryFlowExcuteRecordList(String flowId, String page,
			String rows) throws PaasWebException;

	/***
	 *  流水执行记录
	 */
	List<FlowStepRecordEntity> queryFlowExcuteRecord(String flowId,
			String flowRecordId) throws PaasWebException;
	
	/***
	 * 查询步骤执行记录集合(所有单步骤都可调用)
	 * @param flowId 流水id
	 * @param stepId 步骤id
	 * @param stepName 步骤名称
	 * @return
	 * @throws PaasWebException
	 */
	public List<StepRecodeEntity> queryStepRecodeList(String flowId,String stepName) throws PaasWebException;
	
	/***
	 * 查询单步骤构建记录日志信息(所有单步骤的日志信息都可调用)
	 * @param flowId 流水id
	 * @param stepId 步骤Id
	 * @return
	 * @throws PaasWebException
	 */
	public RestResult queryBuildLogs(String flowId,String stepId,String asName) throws PaasWebException;
}
