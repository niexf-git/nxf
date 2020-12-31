package com.cmsz.paas.web.cicd.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.CodeDetailsEntity;
import com.cmsz.paas.web.cicd.model.CodeProblemDetailsInfo;
import com.cmsz.paas.web.cicd.model.CodeProblemInfo;
import com.cmsz.paas.web.cicd.service.CodeDetailsService;
/**
 * 代码详情action
 * 
 * @author ccl
 * 
 */
public class CodeDetailsAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(CodeDetailsAction.class);
	/** 流水ID */
	private String flowId;
	/** 问题类型 */
	private String problemType;
	/** 问题代码路径 */
	private String codePath;
	/** sonarUUID */
	private String sonarUUID;
	/** 流水记录ID */
	private String flowRecordId;
	
	@Autowired
	private CodeDetailsService codeDetailsService;
	/**
	 * 查询代码详情列表
	 * 
	 * @param 流水ID
	 * 
	 * @return List<CodeCheckEntity>
	 */
	public void queryCodeDetailsList() {
		logger.info("开始执行查询代码详情列表");
		try {
			List<CodeDetailsEntity> list = codeDetailsService.queryCodeDetailsList(flowId,flowRecordId);
			logger.info("查询代码详情列表成功！");
			this.sendSuccessReslult(list);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询代码详情列表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询代码详情
	 * 
	 * @param 流水ID,问题类型,sonarUUID
	 * 
	 * @return SonarProblemInfo
	 */
	public void queryCodeDetails() {
		logger.info("开始执行查询代码详情");
		try {
			List<CodeProblemInfo> CodeProblemDetailInfoList = codeDetailsService.queryCodeDetails(flowId, problemType,sonarUUID);
			logger.info("查询代码详情成功！");
			this.sendSuccessReslult(CodeProblemDetailInfoList);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询代码详情出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询问题代码详情
	 * 
	 * @param 流水ID,问题类型，问题代码路径
	 * 
	 * @return CodeProblemDetailsInfo
	 */
	@UnLogging
	public void queryProblemDetails() {
		logger.info("开始执行查询问题代码详情");
		try {
			CodeProblemDetailsInfo codeProblemDetailInfoList = codeDetailsService.queryProblemDetails(flowId, problemType, codePath);
			logger.info("查询问题代码详情成功！");
			this.sendSuccessReslult(codeProblemDetailInfoList);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询问题代码详情出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}



	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public String getSonarUUID() {
		return sonarUUID;
	}

	public void setSonarUUID(String sonarUUID) {
		this.sonarUUID = sonarUUID;
	}

	public String getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(String flowRecordId) {
		this.flowRecordId = flowRecordId;
	}
	
	
}
