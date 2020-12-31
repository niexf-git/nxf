package com.cmsz.paas.web.cicd.service;





import java.util.List;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.FlowInfo;
import com.cmsz.paas.web.cicd.model.FlowExcuteRecordEntity;
import com.cmsz.paas.web.cicd.model.FlowStepRecordEntity;

/**
 * 流水管理service
 * @author jiayz
 * 
 */
public interface FlowManagerService {
	void createFlow(FlowInfo flow);
	
	/**
	 * 模糊查询(流水列表)
	 * @param appId 应用ID
	 * @param operType 操作类型
	 * @param searchKey 模糊查询
	 * @param page 当前页数
	 * @param rows 每页条数
	 */
	public com.cmsz.paas.web.base.dao.page.Page<FlowInfo> queryFlowList(String appId, String operType,
			String searchKey, PageContext pc) throws PaasWebException;
	
	void modifyFlow(FlowInfo flow);
	void deleteFlow(String flowId);
	FlowInfo queryFlowById(String flowId);
	
	/**
	 * 立即构建
	 * @author lin.my
	 * @throws PaasWebException
	 */
	public void promptBuild(String flowId) throws PaasWebException;

	/**
	 * 停止流水
	 * @author lin.my
	 * @throws PaasWebException
	 */
	public void stopFlow(String flowId) throws PaasWebException;

	/**
	 * 复制流水
	 * @author lin.my
	 * @throws PaasWebException
	 */
	public void copyFlow(FlowInfo flow) throws PaasWebException;
	
	/***
	 * 多流水查询
	 * @return
	 * @throws PaasWebException
	 */
	public List<FlowInfo> queryFlowByIds(String flowIds) throws PaasWebException;

}
