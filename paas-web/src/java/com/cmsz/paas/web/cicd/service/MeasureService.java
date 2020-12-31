package com.cmsz.paas.web.cicd.service;

import java.util.List;

import com.cmsz.paas.web.cicd.model.CodeRepoEntity;
import com.cmsz.paas.web.cicd.model.CodeRepoInfo;
import com.cmsz.paas.web.cicd.model.FlowNameEntity;
import com.cmsz.paas.web.cicd.model.MeasureBuildEntity;
import com.cmsz.paas.web.cicd.model.MeasureDeployEntity;
import com.cmsz.paas.web.cicd.model.MeasureInfo;
import com.cmsz.paas.web.cicd.model.QualityAnalysis;
import com.sun.org.apache.bcel.internal.classfile.Code;

/**
 * 度量service
 * @author jiayz
 * 
 */
public interface MeasureService {
	List<MeasureInfo> queryDownloadCheckList(String appId, String type);
	List<MeasureInfo> queryCompileBuildList(String appId, String type);
	List<MeasureInfo> queryAutomateTestList(String appId, String type);
	List<MeasureInfo> queryIntegrationTestList(String appId, String type);
	
	/**
	 * 流水名称查询
	 * @return
	 */
	List<FlowNameEntity> queryFlowNameList(String appId,String roleTyle);
	
	/***
	 * 构建图表结果集查询
	 * @param flowId 流水ID
	 * @return
	 */
	MeasureBuildEntity queryBuildList(String flowId);
	
	/***
	 * 概览质量分析查询
	 * @return
	 */
	QualityAnalysis querQualityAnalysis(String flowId);
	
	/***
	 * 概览-部署查询
	 * @param flowId
	 * @return
	 */
	MeasureDeployEntity queryDeployList(String flowId);
	
	/***
	 * 概览-查询代码库列表
	 * @param flowId
	 * @return
	 */
	List<CodeRepoEntity> queryCodeRepoList(String flowId);
	
	/**
	 * 概览-查询代码库图表
	 * @param flow
	 * @return
	 */
	List<CodeRepoInfo> queryCodeRepo(String url,String type);
	
}
