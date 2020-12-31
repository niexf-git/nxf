package com.cmsz.paas.web.cicd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.BuildKpiEntity;
import com.cmsz.paas.common.model.controller.entity.CheckKpiEntity;
import com.cmsz.paas.common.model.controller.entity.FlowEntity;
import com.cmsz.paas.common.model.controller.entity.TestKpiEntity;
import com.cmsz.paas.common.model.controller.response.Analysis;
import com.cmsz.paas.common.model.controller.response.Avgbuilds;
import com.cmsz.paas.common.model.controller.response.BuildKpiList;
import com.cmsz.paas.common.model.controller.response.BuildRecord;
import com.cmsz.paas.common.model.controller.response.BuildsPerDay;
import com.cmsz.paas.common.model.controller.response.CheckKpiList;
import com.cmsz.paas.common.model.controller.response.CodePathDetail;
import com.cmsz.paas.common.model.controller.response.CodePathList;
import com.cmsz.paas.common.model.controller.response.CodeQuaKpi;
import com.cmsz.paas.common.model.controller.response.DepRecord;
import com.cmsz.paas.common.model.controller.response.LatestBuild;
import com.cmsz.paas.common.model.controller.response.PrivateImage;
import com.cmsz.paas.common.model.controller.response.PrivateImageVersionList;
import com.cmsz.paas.common.model.controller.response.TestKpiList;
import com.cmsz.paas.common.model.controller.response.Totalbuilds;
import com.cmsz.paas.common.model.controller.response.UnitTests;
import com.cmsz.paas.common.model.hygieia.ItemsCommit;
import com.cmsz.paas.common.model.hygieia.ItemsList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.MeasureDao;
import com.cmsz.paas.web.cicd.model.AvgBuilds;
import com.cmsz.paas.web.cicd.model.BuildsPerDayEntity;
import com.cmsz.paas.web.cicd.model.CodeRepoEntity;
import com.cmsz.paas.web.cicd.model.CodeRepoInfo;
import com.cmsz.paas.web.cicd.model.DeployInfo;
import com.cmsz.paas.web.cicd.model.FlowNameEntity;
import com.cmsz.paas.web.cicd.model.LatestBuilds;
import com.cmsz.paas.web.cicd.model.MeasureBuildEntity;
import com.cmsz.paas.web.cicd.model.MeasureDeployEntity;
import com.cmsz.paas.web.cicd.model.MeasureInfo;
import com.cmsz.paas.web.cicd.model.QualityAnalysis;
import com.cmsz.paas.web.cicd.model.StaticAnalysis;
import com.cmsz.paas.web.cicd.model.TotalBuils;
import com.cmsz.paas.web.cicd.service.MeasureService;
import com.cmsz.paas.web.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service("measureService")
public class MeasureServiceImpl implements MeasureService{
	private static final Logger logger = LoggerFactory
			.getLogger(MeasureServiceImpl.class);
	@Autowired
	MeasureDao dao;
	@Override
	public List<MeasureInfo> queryDownloadCheckList(String appId,String type) {
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryDownloadCheckListUrl",appId,type);
		logger.debug("开始调用查询下载检查列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, CheckKpiList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询下载检查列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			CheckKpiList se = (CheckKpiList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<CheckKpiEntity> list = se.getCheckKpiList();
				List<MeasureInfo> mList = new ArrayList<MeasureInfo>();
				if(list.size()>0){
					for(CheckKpiEntity c : list){
						MeasureInfo mi = new MeasureInfo();
						mi.setFlowId(c.getFlowId());
						mi.setFlowName(c.getFlowName());
						mi.setProblemNumber(c.getLeakNum()+"");
						mi.setFlawNumber(c.getLeakNum()+"");
						mi.setCodeRepetitionRate(c.getCodeRepRate());
						mi.setCodeRows(c.getLineNum()+"");
						mi.setCodeComplexity(c.getComplexity());
						mList.add(mi);
					}
				}
				logger.info("调用查询下载检查列表Rest接口成功！");			
				return mList;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询下载检查列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_DOWNLOAD_CHECK_ERROR
						+ "]查询下载检查列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_DOWNLOAD_CHECK_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public List<MeasureInfo> queryCompileBuildList(String flowId, String type) {
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCompileBuildListUrl",flowId,type);
		logger.debug("开始调用查询编译构建列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, BuildKpiList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询查询编译构建列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			BuildKpiList se = (BuildKpiList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<BuildKpiEntity> list = se.getBuildKpiList();
				List<MeasureInfo> mList = new ArrayList<MeasureInfo>();
				if(list.size()>0){
					for(BuildKpiEntity c : list){
						MeasureInfo mi = new MeasureInfo();
						mi.setFlowId(c.getFlowId());
						mi.setFlowName(c.getFlowName());
						mi.setFlowCompileNumber(c.getCompTotalNum()+"");
						mi.setFlowCompileSuccessRate(c.getCompSuccNum()+"");
						mi.setFlowBuildNumber(c.getBuildTotalNum()+"");
						mi.setFlowBuildSuccessRate(c.getBuildSuccNum()+"");
						mList.add(mi);
					}
				}
				logger.info("调用查询查询编译构建列表Rest接口成功！");			
				return mList;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询编译构建列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_COMPILE_BUILD_LIST_ERROR
						+ "]查询查询编译构建列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_COMPILE_BUILD_LIST_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public List<MeasureInfo> queryAutomateTestList(String flowId, String type) {
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryAutomateTestListUrl",flowId,type);
		logger.debug("开始调用查询自动化测试列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, TestKpiList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询自动化测试列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			TestKpiList se = (TestKpiList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<TestKpiEntity> list = se.getTestKpiList();
				List<MeasureInfo> mList = new ArrayList<MeasureInfo>();
				if(list.size()>0){
					for(TestKpiEntity c : list){
						MeasureInfo mi = new MeasureInfo();
						mi.setFlowId(c.getFlowId());
						mi.setFlowName(c.getFlowName());
						mi.setFlowSeriousProblemNumber(c.getUseCaseNum()+"");
						mi.setFlowCommonProblemNumber(c.getDefectNum()+"");
						mList.add(mi);
					}
				}
				logger.info("调用查询自动化测试列表Rest接口成功！");			
				return mList;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询自动化测试列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_AUTO_MATE_TEST_ERROR
						+ "]查询自动化测试列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_AUTO_MATE_TEST_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public List<MeasureInfo> queryIntegrationTestList(String flowId, String type) {
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryIntegrationTestListUrl",flowId,type);
		logger.debug("开始调用查询集成测试列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, TestKpiList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询集成测试列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			TestKpiList it = (TestKpiList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<TestKpiEntity> list = it.getTestKpiList();
				List<MeasureInfo> mList = new ArrayList<MeasureInfo>();
				if(list.size()>0){
					for(TestKpiEntity c : list){
						MeasureInfo mi = new MeasureInfo();
						mi.setFlowId(c.getFlowId());
						mi.setFlowName(c.getFlowName());
						mi.setFlowSeriousProblemNumber(c.getUseCaseNum()+"");
						mi.setFlowCommonProblemNumber(c.getDefectNum()+"");
						mList.add(mi);
					}
				}
				logger.info("调用查询集成测试列表Rest接口成功！");			
				return mList;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询集成测试列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_INTEGRATION_TEST
						+ "]查询集成测试列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_INTEGRATION_TEST,
						ex.getMessage());
			}
		}
	}

	@Override
	public List<FlowNameEntity> queryFlowNameList(String appId,String roleTyle) {
		List<FlowNameEntity> lists = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryMeasureFlowNameListUrl",appId,roleTyle);
		logger.debug("开始调用查询流水名称列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询流水名称列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			Page<FlowEntity> entity = (Page<FlowEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<FlowEntity> fList = entity.getResult();
				
				if(null != fList){
					org.json.JSONArray array = new org.json.JSONArray(fList);
					String jsonStr = array.toString();
					List<FlowEntity> flowList = fromJsonList(jsonStr, FlowEntity.class);
					lists = new ArrayList<FlowNameEntity>();
					for (FlowEntity flowEntity : flowList) {
						FlowNameEntity fne = new FlowNameEntity();
						fne.setFlowId(flowEntity.getUuid());
						fne.setFlowName(flowEntity.getName());
						fne.setType(flowEntity.getRoleType());
						lists.add(fne);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询流水名称列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_FLOW_NAMESLIST_ERRORS
						+ "]查询流水名称列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_FLOW_NAMESLIST_ERRORS,
						ex.getMessage());
			}
		}
		return lists;
	}
	
	/**
	 * 解析的时候加行了双引号
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	private <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
		ArrayList<T> mList = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		//Gson mGson = new Gson();
		Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		for (final JsonElement elem : array) {
			mList.add(mGson.fromJson(elem, cls));
		}
		return mList;
	}

	@Override
	public MeasureBuildEntity queryBuildList(String flowId) {
		MeasureBuildEntity buildEntity = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCodeSubmitDetailUrl",flowId);
		logger.debug("开始调用查询概览构建信息的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, BuildRecord.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询概览构建信息的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			BuildRecord entity = (BuildRecord) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if(null != entity){
					buildEntity = tranBuildLists(entity);
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询概览构建信息出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_MEASURE_BUILD_ERROR
						+ "]查询概览构建信息出错", ex);
				throw new PaasWebException(
						Constants.QUERY_MEASURE_BUILD_ERROR,
						ex.getMessage());
			}
		}
		return buildEntity;
	}

	private MeasureBuildEntity tranBuildLists(BuildRecord entity){
		MeasureBuildEntity buildEntity = new MeasureBuildEntity();
		
		List<BuildsPerDayEntity> dayEntities = new ArrayList<BuildsPerDayEntity>();
		for (BuildsPerDay bPD : entity.getBuildsPerDayList()) {
			BuildsPerDayEntity entity2 = new BuildsPerDayEntity();
			entity2.setSuccessCount(bPD.getSuccessCount());
			entity2.setFailuresCount(bPD.getFailuresCount());
			List<LatestBuilds> list2 = new ArrayList<LatestBuilds>();
			for (LatestBuild latest : bPD.getLatestBuildList()) {
				LatestBuilds builds = new LatestBuilds();
				builds.setBuildDuration(latest.getBuildDuration());
				builds.setBuildName(latest.getBuildName());
				builds.setBuildNumber(latest.getBuildNumber());
				builds.setBuildTime(latest.getBuildTime());
				builds.setStatus(latest.getStatu());
				list2.add(builds);
			}
			entity2.setLatestBuilds(list2);
			entity2.setBuildDate(bPD.getBuildDate());
			dayEntities.add(entity2);
		}
		buildEntity.setBuildsPerDayEntities(dayEntities);
		List<AvgBuilds>  avgBuilds = new ArrayList<AvgBuilds>();
		buildEntity.setAvgBuilds(avgBuilds);
		for (Avgbuilds avg : entity.getAvgbuildsList()) {
			AvgBuilds builds = new AvgBuilds();
			builds.setAvgBuildTime(avg.getAvgTime());
			builds.setBuildDate(avg.getBuildDate());
			avgBuilds.add(builds);
		}
		buildEntity.setAvgBuilds(avgBuilds);
		
		Totalbuilds totalbuilds = entity.getTotalbuilds();
		TotalBuils buils = new TotalBuils();
		buils.setFourteenDaysCount(totalbuilds.getLastFourteenDayNum()+"");
		buils.setWeekCount(totalbuilds.getLastSevenDayNum()+"");
		buils.setTodayCount(totalbuilds.getTodayBuildNum()+"");
		buildEntity.setTotalBuilds(buils);
		
		return buildEntity;
	}
	@Override
	public QualityAnalysis querQualityAnalysis(String flowId) {
		QualityAnalysis qualityAnalysis = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCodeQuaKpiUrl",flowId);
		logger.debug("开始调用查询概览质量分析的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, CodeQuaKpi.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询概览质量分析的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			CodeQuaKpi entity = (CodeQuaKpi) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if(null != entity){
					qualityAnalysis = new QualityAnalysis();
					Analysis staticAnalysis =  entity.getAnalysis();
					StaticAnalysis analysis = new StaticAnalysis();
					analysis.setCheckBlocker(staticAnalysis.getCheckBlocker()+"");
					analysis.setCheckCritical(staticAnalysis.getCheckCritical()+"");
					analysis.setCheckIssues(staticAnalysis.getCheckIssues()+"");
					analysis.setCheckMajor(staticAnalysis.getCheckMajor()+"");
					qualityAnalysis.setStaticAnalysis(analysis);
					
					UnitTests unitTests = entity.getUnitTests();
					com.cmsz.paas.web.cicd.model.UnitTests unit = new com.cmsz.paas.web.cicd.model.UnitTests();
					unit.setUniErrNum(unitTests.getUniErrNum()+"");
					unit.setUniFaiNumber(unitTests.getUniFaiNumber()+"");
					unit.setUniSucNumber(unitTests.getUniSucNumber()+"");
					unit.setUniTotal(unitTests.getUniTotal()+"");
					
					qualityAnalysis.setUnitTests(unit);
					qualityAnalysis.setUniLineCovRate(unitTests.getUniLineCovRate());
					
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询概览质量分析出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_MEASURE_QUALITYANALYSIS_ERROR
						+ "]查询概览质量分析出错", ex);
				throw new PaasWebException(
						Constants.QUERY_MEASURE_QUALITYANALYSIS_ERROR,
						ex.getMessage());
			}
		}
		return qualityAnalysis;
	}

	@Override
	public MeasureDeployEntity queryDeployList(String flowId) {
		MeasureDeployEntity measureDeployEntity = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryDepRecordUrl",flowId);
		logger.debug("开始调用查询概览部署的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, DepRecord.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询概览部署的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			DepRecord record = (DepRecord) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if(null != record){
					PrivateImage priImage = record.getPrivateImage();
					if(null != priImage){
						measureDeployEntity = new MeasureDeployEntity();
						
						measureDeployEntity.setImageName(priImage.getName());
						measureDeployEntity.setTotalCount(priImage.getDeployNum()+"");
						List<PrivateImageVersionList> versionLists =
								record.getPrivateImage().getPrivateImageVersionListList();
						List<DeployInfo> infoList = new ArrayList<DeployInfo>();
						for (PrivateImageVersionList image : versionLists) {
							DeployInfo infos = new DeployInfo();
							infos.setStatu(image.getStatus());
							infos.setImageVersion(image.getVersion());
							infos.setTime(image.getTime());
							infoList.add(infos);
						}
						measureDeployEntity.setDeployList(infoList);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询概览部署出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_MEASURE_DEPLOY_ERROR
						+ "]查询概览部署出错", ex);
				throw new PaasWebException(
						Constants.QUERY_MEASURE_DEPLOY_ERROR,
						ex.getMessage());
			}
		}
		return measureDeployEntity;
	}

	@Override
	public List<CodeRepoEntity> queryCodeRepoList(String flowId) {
		List<CodeRepoEntity> list = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCodeRepoListUrl",flowId);
		logger.debug("开始调用查询代码库列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, CodePathList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询代码库列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			CodePathList entity = (CodePathList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if(null != entity){
					List<CodePathDetail> entities = entity.getCodeRegistry();
					if(entities.size()>0){
						list = new ArrayList<CodeRepoEntity>();
						for (CodePathDetail code : entities) {
							CodeRepoEntity codeRepoEntity = new CodeRepoEntity();
							codeRepoEntity.setScm(code.getType()+"");
							codeRepoEntity.setUrl(code.getUrl());
							list.add(codeRepoEntity);
						}
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询代码库列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_CODE_REPO_LIST_ERROR
						+ "]查询代码库列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_CODE_REPO_LIST_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}

	@Override
	public List<CodeRepoInfo> queryCodeRepo(String svnurl,String type) {
		List<CodeRepoInfo> list = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCodeRepoUrl",svnurl,type);
		logger.debug("开始调用查询代码库图表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, ItemsList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询代码库图表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			ItemsList itemList = (ItemsList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if(null != itemList){
					list = new ArrayList<CodeRepoInfo>();
					List<ItemsCommit> itemsCommit = itemList.getResult();
					for (ItemsCommit item : itemsCommit) {
						CodeRepoInfo codeInfo = new CodeRepoInfo();
						codeInfo.setId(item.getId());
						codeInfo.setCollectorItemId(item.getCollectorItemId());
						codeInfo.setNumberOfChanges(item.getNumberOfChanges());
						codeInfo.setScmAuthor(item.getScmAuthor());
						codeInfo.setScmCommitLog(item.getScmCommitLog());
						codeInfo.setScmCommitTimestamp(item.getScmCommitTimestamp());
						codeInfo.setScmRevisionNumber(item.getScmRevisionNumber());
						codeInfo.setScmUrl(item.getScmUrl());
						codeInfo.setTimestamp(item.getTimestamp());
						list.add(codeInfo);
					}
				}
				
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询代码库图表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_CODE_REPO_ERROR
						+ "]查询代码库图表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_CODE_REPO_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}
	
	
}
