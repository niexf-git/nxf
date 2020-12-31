package com.cmsz.paas.web.cicd.action;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.page.SortInfo;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.FlowInfo;
import com.cmsz.paas.web.cicd.model.StepDetailEntity;
import com.cmsz.paas.web.cicd.service.FlowManagerService;
import com.cmsz.paas.web.cicd.service.StepDetailService;
/**
 * 流水管理Action
 * @author jiayz
 * 
 */
public class FlowManagerAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(FlowManagerAction.class);
	private String flowId;//流水ID

	private FlowInfo flow;//流水对象
	
	/** 步骤id 添加人:jiangwei*/
	private String stepId;
    /** 步骤名称   添加人:jiangwei*/
	private String stepName;
	
	private String flowIds;
	
	/**
	 * 流水搜索关键词
	 */
	private String searchKey = "";
	
	private String flowRecordId = "";
	
	
	@Autowired
	private StepDetailService stepDetailService;
	
	
	@Autowired
	private FlowManagerService flowManagerService;
	
	/**
	 * 查询流水步骤详情
	 * 
	 * @param 流水ID
	 * 
	 * @return StepDetailEntity
	 */
	public void queryStepDetail() {
		logger.info("开始执行查询流水步骤详情");
		try {
			StepDetailEntity stepDetailEntity = stepDetailService.queryStepDetail(flowId);
			logger.info("查询流水步骤详情成功！");
			this.sendSuccessReslult(stepDetailEntity);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询流水步骤详情出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	

	
	
	/**
	 * 创建流水
	 * 
	 * @param FlowInfo
	 * 
	 * @return 
	 */
	public void createFlow() {
		logger.info("开始执行创建流水");
		try {
			if(flow==null){
				flow = new FlowInfo();
			}
			String appId = getSessionMap().get("appPerSelectedId").toString();
			String createName = getSessionMap().get("loginName").toString();
			String roleType = getSessionMap().get("selectedOpertype").toString();
			flow.setAppId(appId.split(",")[0]);
			flow.setCreateBy(createName);
			flow.setRoleType(roleType);
			flowManagerService.createFlow(flow);
			logger.info("创建流水成功！");
			this.sendSuccessReslult();
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]创建流水出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 模糊查询(流水列表)
	 * @author lin.my
	 * @throws Exception
	 */
	public void queryFlowList() throws Exception {
		
		// 获取应用Id、操作类型（1-开发、2-测试）、角色类型（0-超级管理员，1-普通用户）
		String appId = (String) getSessionMap().get("appPerSelectedId");
		String operType = (String) getSessionMap().get("selectedOpertype");
		
		// roleType存入session的时候是int类型，类型转化的时候会出错，所以不能用：
		// String roleType = (String)getSessionMap().get("roleType");
		String roleType = getSessionMap().get("roleType") + "";
		
		logger.info("开始执行模糊查询(流水列表)，应用Id：" + appId + "，操作类型：" + operType
				+ "，角色类型：" + operType);
		try {
			// 获取前端页面分页及排序参数
			PageContext pc = Struts2.buildPageContext();
			// 普通用户还没授予应用的时候，不需要去请求控制中心，直接返回
			if("1".equals(roleType) && "".equals(appId) && "".equals(operType)){
				Page<FlowInfo> flowList = new Page<FlowInfo>();
				flowList.setResult(Collections.<FlowInfo> emptyList());
				flowList.setTotalCount(0);
				sendSuccessReslult(flowList);
			} else {
				Page<FlowInfo> flowList = flowManagerService.queryFlowList(appId, operType,
						searchKey,pc);
				sendSuccessReslult(flowList);
				logger.info("模糊查询(流水列表)成功！");
			}
		} catch (PaasWebException ex) {
			logger.error("模糊查询(流水列表)错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	
	/***
	 * 多流水列表查询
	 * 局部刷新调用
	 */
	public void queryFlowListByIds() {
		logger.info("开始执行查询多流水列表");
		try {
			List<FlowInfo> list = flowManagerService.queryFlowByIds(flowIds);
			sendSuccessReslult(list);
		} catch (PaasWebException e) {
			logger.error("多流水列表查询错误 ", e);
			sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/**
	 * 修改流水步骤详情
	 * 
	 * @param FlowInfo
	 * 
	 * @return 
	 */
	public void updateFlow() {
		logger.info("开始执行修改流水");
		try {
			if(flow==null){
				flow = new FlowInfo();
			}
			String appId = getSessionMap().get("appPerSelectedId").toString();
			String createName = getSessionMap().get("loginName").toString();
			String roleType = getSessionMap().get("selectedOpertype").toString();
			flow.setAppId(appId.split(",")[0]);
			flow.setCreateBy(createName);
			flow.setRoleType(roleType.split(",")[0]);
			flowManagerService.modifyFlow(flow);
			logger.info("修改流水成功！");
			this.sendSuccessReslult();
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]修改流水出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 删除流水详情
	 * 
	 * @param FlowId
	 * 
	 * @return 
	 */
	public void deleteFlow() {
		logger.info("开始执行删除流水");
		try {
			flowManagerService.deleteFlow(flowId);
			logger.info("删除流水成功！");
			this.sendSuccessReslult();
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]删除流水出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	/**
	 *查询单个流水详情
	 * 
	 * @param FlowId
	 * 
	 * @return 
	 */
	public void queryFlowDetail() {
		logger.info("开始执行查询单个流水详情");
		try {
			FlowInfo fe = flowManagerService.queryFlowById(flowId);
			this.sendSuccessReslult(fe);
			logger.info("查询单个流水详情成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询单个流水详情出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 立即构建
	 * @author lin.my
	 * @throws Exception
	 */
	public void executeBuild() throws Exception {
		logger.info("开始执行构建，流水ID：" + flowId);
		try {
			flowManagerService.promptBuild(flowId);
			this.sendSuccessReslult();
			logger.info("执行构建成功！");
		} catch (PaasWebException ex) {
			logger.error("执行构建错误", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	/**
	 * 停止流水
	 * @author lin.my
	 * @throws Exception
	 */
	public void stopFlow() throws Exception {
		logger.info("开始停止流水，流水ID：" + flowId);
		try {
			flowManagerService.stopFlow(flowId);
			this.sendSuccessReslult();
			logger.info("停止流水成功！");
		} catch (PaasWebException ex) {
			logger.error("停止流水错误", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	/**
	 * 复制流水
	 * @author lin.my
	 * @throws Exception
	 */
	public void copyFlow() throws Exception {
		logger.info("开始复制流水，流水ID：" + flowId);
		try {
			if(flow == null){
				flow = new FlowInfo();
			}
//			String appId = getSessionMap().get("appPerSelectedId").toString();
			String createName = getSessionMap().get("loginName").toString();
//			String operType = getSessionMap().get("selectedOpertype").toString();
//			flow.setAppId(appId.split(",")[0]);
			flow.setCreateBy(createName);
//			flow.setOperType(operType.split(",")[0]);
			flowManagerService.copyFlow(flow);
			logger.info("复制流水成功！");
			this.sendSuccessReslult();
			
		} catch (PaasWebException ex) {
			logger.error("复制流水错误", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	public FlowInfo getFlow() {
		return flow;
	}

	public void setFlow(FlowInfo flow) {
		this.flow = flow;
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

	public String getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(String flowRecordId) {
		this.flowRecordId = flowRecordId;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}




	public String getFlowIds() {
		return flowIds;
	}




	public void setFlowIds(String flowIds) {
		this.flowIds = flowIds;
	}

}
