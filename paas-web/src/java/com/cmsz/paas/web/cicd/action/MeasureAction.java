package com.cmsz.paas.web.cicd.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.CodeRepoEntity;
import com.cmsz.paas.web.cicd.model.CodeRepoInfo;
import com.cmsz.paas.web.cicd.model.FlowNameEntity;
import com.cmsz.paas.web.cicd.model.MeasureBuildEntity;
import com.cmsz.paas.web.cicd.model.MeasureDeployEntity;
import com.cmsz.paas.web.cicd.model.MeasureInfo;
import com.cmsz.paas.web.cicd.model.QualityAnalysis;
import com.cmsz.paas.web.cicd.service.MeasureService;
/**
 * 度量action.
 * 
 * @author jiayz
 */
public class MeasureAction extends AbstractAction{
	private static final Logger logger = LoggerFactory
			.getLogger(MeasureAction.class);
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MeasureService measureService;
	
	private String appId;
	private String type;
	private String flowId;
	private String url;
	
	/**
	 * 下载检查
	 */
	@UnLogging
	public void queryDownloadCheckList() {
		logger.info("开始执行下载检查列表查询");
		try {
			assignValueToField();
			List<MeasureInfo> list  = measureService.queryDownloadCheckList(appId,type);
			this.sendSuccessReslult(list);
			logger.info("查询下载检查列表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询下载检查列表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	private void assignValueToField() {
		String appIdArr = getSessionMap().get("appPerSelectedId").toString();
		appId= appIdArr.split(",").length>1 ? "":appIdArr.split(",")[0];
		type = getSessionMap().get("selectedOpertype").toString();
	}

	/**
	 * 编译构建
	 */
	@UnLogging
	public void queryCompileBuildList() {
		logger.info("开始执行编译构建列表查询");
		try {
			assignValueToField();
			List<MeasureInfo> list  = measureService.queryCompileBuildList(flowId,type);
			this.sendSuccessReslult(list);
			logger.info("查询编译构建列表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询编译构建列表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 自动化测试列表查询
	 */
	@UnLogging
	public void queryAutomateTestList() {
		logger.info("开始执行自动化测试列表查询");
		try {
			assignValueToField();
			List<MeasureInfo> list  = measureService.queryAutomateTestList(flowId,type);
			this.sendSuccessReslult(list);
			logger.info("查询自动化测试列表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询自动化测试列表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 集成测试列表查询
	 */
	@UnLogging
	public void queryIntegrationTestList() {
		logger.info("开始执行集成测试列表查询");
		try {
			assignValueToField();
			List<MeasureInfo> list  = measureService.queryIntegrationTestList(flowId,type);
			this.sendSuccessReslult(list);
			logger.info("查询集成测试列表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询集成测试列表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询流水名称集合 （概览筛选条件）
	 */
	@UnLogging
	public void queryFlowNameList(){
		logger.info("开始执行流水名称集合查询");
		try {
			String appId = getSessionMap().get("appPerSelectedId").toString();
			String type = getSessionMap().get("selectedOpertype").toString();
			List<FlowNameEntity> list = measureService.queryFlowNameList(appId,type);
			logger.info("查询流水名称列表成功！");
			this.sendSuccessReslult(list);
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询流水名称列表出错", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/***
	 * 概览-构建
	 */
	@UnLogging
	public void queryBuildList(){
		logger.info("开始执行概览构建信息查询");
		try {
			MeasureBuildEntity entity = measureService.queryBuildList(flowId);
			logger.info("查询概览构建信息成功！");
			this.sendSuccessReslult(entity);
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询概览构建信息出错", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/**
	 * 概览-质量分析
	 */
	@UnLogging
	public void queryQualityAnalysis(){
		logger.info("开始执行概览质量分析查询");
		try {
			QualityAnalysis analysis = measureService.querQualityAnalysis(flowId);
			logger.info("查询概览质量分析成功！");
			this.sendSuccessReslult(analysis);
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询概览质量分析出错", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
		
	}
	
	
	/***
	 * 概览-部署
	 */
	@UnLogging
	public void queryDeployList(){
		logger.info("开始执行概览部署查询");
		try {
			MeasureDeployEntity entity = measureService.queryDeployList(flowId);
			logger.info("查询概览部署成功！");
			this.sendSuccessReslult(entity);
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询概览部署出错", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	
	/***
	 * 查询代码仓库列表
	 */
	@UnLogging
	public void queryCodeRepoList(){
		logger.info("开始执行代码库列表查询");
		try {
			List<CodeRepoEntity> list = measureService.queryCodeRepoList(flowId);
			logger.info("查询代码库列表成功！");
			this.sendSuccessReslult(list);
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询代码库列表出错", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 查询代码库图表
	 */
	@UnLogging
	public void queryCodeRepo(){
		logger.info("开始执行代码库图表查询");
		try {
			List<CodeRepoInfo> list = measureService.queryCodeRepo(url,type);
			logger.info("查询代码库图表成功！");
			this.sendSuccessReslult(list);
		} catch (PaasWebException e) {
			logger.error("[" + e.getKey() + "]查询代码库图表出错", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
