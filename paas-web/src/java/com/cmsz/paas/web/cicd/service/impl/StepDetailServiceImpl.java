package com.cmsz.paas.web.cicd.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.StepRecordEntity;
import com.cmsz.paas.common.model.controller.response.Logs;
import com.cmsz.paas.common.model.controller.response.StepDetail;
import com.cmsz.paas.common.model.controller.response.StepRecordList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.LogProcessUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.StepDetailDao;
import com.cmsz.paas.web.cicd.model.AutoTestEntity;
import com.cmsz.paas.web.cicd.model.CodeDownloadAndCheckEntity;
import com.cmsz.paas.web.cicd.model.CompileBuildEntity;
import com.cmsz.paas.web.cicd.model.DepScanEntity;
import com.cmsz.paas.web.cicd.model.InteTestEntity;
import com.cmsz.paas.web.cicd.model.PerformanceTestEntity;
import com.cmsz.paas.web.cicd.model.ReleaseEntity;
import com.cmsz.paas.web.cicd.model.StepDetailEntity;
import com.cmsz.paas.web.cicd.model.StepDetailInfo;
import com.cmsz.paas.web.cicd.model.StepRecodeEntity;
import com.cmsz.paas.web.cicd.model.TestDeployEntity;
import com.cmsz.paas.web.cicd.service.StepDetailService;
import com.cmsz.paas.web.constants.Constants;

/**
 * 流水步骤详情service实现
 * 
 * @author ccl
 * 
 */
@Service("stepDetailService")
public class StepDetailServiceImpl implements StepDetailService {
	private static final Logger logger = LoggerFactory
			.getLogger(StepDetailServiceImpl.class);
	@Autowired
	private StepDetailDao stepDetailDao;

	@Override
	public StepDetailEntity queryStepDetail(String flowId)
			throws PaasWebException {
		StepDetailEntity stepDetailEntity = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryStepDetailUrl", flowId);
		logger.debug("开始调用查询流水步骤详情的rest接口：" + url);
		try {
			ResponseInfo responseInfo = stepDetailDao
					.get(url, StepDetail.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询流水步骤详情的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			StepDetail stepDetail = (StepDetail) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (stepDetail != null) {
					stepDetailEntity = genStepDetailEntity(stepDetail);
				}
				logger.info("调用查询流水步骤详情Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询流水步骤详情出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_STEPDETAIL_ERROR
						+ "]查询流水步骤详情出错", ex);
				throw new PaasWebException(
						Constants.QUERY_STEPDETAIL_ERROR,
						ex.getMessage());
			}
		}
		return stepDetailEntity;
	}

	/**
	 * 流水步骤详情信息转换
	 * 
	 * @param StepDetail
	 * 
	 * @return StepDetailEntity
	 */
	private StepDetailEntity genStepDetailEntity(
			StepDetail stepDetail) {
		logger.debug("开始转换流水步骤详情信息...");
		StepDetailEntity stepDetailEntity = new StepDetailEntity();
		if (stepDetail != null) {
			stepDetailEntity.setLastStep(stepDetail.getLastStep());
			//开发自动化测试实体转换
			if (stepDetail.getAutotest()!=null) {
				AutoTestEntity autoTestEntity=new AutoTestEntity();
				autoTestEntity.setAllNumber(String.valueOf(stepDetail.getAutotest().getTotals()));
				autoTestEntity.setFallNumber(String.valueOf(stepDetail.getAutotest().getFailNumber()));
				autoTestEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getAutotest().getIsChoise(),stepDetail.getAutotest().getStatus(),stepDetail.getAutotest().getTime()));
				
				stepDetailEntity.setAutoTestEntity(autoTestEntity);
			}
			//部署扫描实体转换
			if (stepDetail.getDepScan()!=null) {
				DepScanEntity depScanEntity=new DepScanEntity();
				depScanEntity.setIsCheck(String.valueOf(stepDetail.getDepScan().getIsCheck()));
				depScanEntity.setWebUrl(stepDetail.getDepScan().getScanUrl());
				depScanEntity.setAppSvcName(stepDetail.getDepScan().getAppSvcName());
				depScanEntity.setServiceID(stepDetail.getDepScan().getAppSvcId());
				depScanEntity.setInstanceNum(stepDetail.getDepScan().getTotalInstanceNum()+"");
				depScanEntity.setRunVersion(stepDetail.getDepScan().getServiceRunVersion());
				depScanEntity.setRunVersionId(stepDetail.getDepScan().getServiceRunVersionId()+"");
				depScanEntity.setState(stepDetail.getDepScan().getServiceState()+"");
				depScanEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getDepScan().getIsChoise(),stepDetail.getDepScan().getStatus(),stepDetail.getDepScan().getTime()));
				
				stepDetailEntity.setDepScanEntity(depScanEntity);
			}
			//下载审查实体转换
			if (stepDetail.getDownloadCheck()!=null) {
				CodeDownloadAndCheckEntity downloadCheckEntity=new CodeDownloadAndCheckEntity();
				downloadCheckEntity.setIsCodeCheck(String.valueOf(stepDetail.getDownloadCheck().getIsCheck()));
				downloadCheckEntity.setTriggerType(String.valueOf(stepDetail.getDownloadCheck().getTriggerType()));
				downloadCheckEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getDownloadCheck().getIsChoise(),stepDetail.getDownloadCheck().getStatus(),stepDetail.getDownloadCheck().getTime()));

				stepDetailEntity.setCodeDownloadAndCheckEntity(downloadCheckEntity);
			}
			//开发集体测试实体转换
			if (stepDetail.getInteTest()!=null) {
				InteTestEntity inteTestEntity=new InteTestEntity();
				inteTestEntity.setAllNumber(stepDetail.getInteTest().getTotals());
				inteTestEntity.setFallNumber(stepDetail.getInteTest().getFailNumber());
				inteTestEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getInteTest().getIsChoise(),stepDetail.getInteTest().getStatus(),stepDetail.getInteTest().getTime()));
				
				stepDetailEntity.setInteTestEntity(inteTestEntity);
			}
			//开发性能测试实体转换
			if (stepDetail.getPerformanceTest()!=null) {
				PerformanceTestEntity performanceTestEntity=new PerformanceTestEntity();
				performanceTestEntity.setAllNumber(String.valueOf(stepDetail.getPerformanceTest().getTotals()));
				performanceTestEntity.setFallNumber(String.valueOf(stepDetail.getPerformanceTest().getFailNumber()));
				performanceTestEntity.setMiniRespTime(String.valueOf(stepDetail.getPerformanceTest().getMiniRespTime()));
				performanceTestEntity.setAverRespTime(String.valueOf(stepDetail.getPerformanceTest().getAverRespTime()));
				performanceTestEntity.setMaxRespTime(String.valueOf(stepDetail.getPerformanceTest().getMaxRespTime()));
				performanceTestEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getPerformanceTest().getIsChoise(),stepDetail.getPerformanceTest().getStatus(),stepDetail.getPerformanceTest().getTime()));
				
				stepDetailEntity.setPerformanceTestEntity(performanceTestEntity);
			}
			
			//编译构建实体转换
			if (stepDetail.getQueryBuild()!=null) {
				CompileBuildEntity compileBuildEntity=new CompileBuildEntity();
				compileBuildEntity.setImageName(stepDetail.getQueryBuild().getImageName());
				compileBuildEntity.setImageVersion(stepDetail.getQueryBuild().getImageVersion());
				compileBuildEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getQueryBuild().getIsChoise(),stepDetail.getQueryBuild().getStatus(),stepDetail.getQueryBuild().getTime()));
				
				stepDetailEntity.setCompileBuildEntity(compileBuildEntity);
			}
			
			//开发发布实体转换
			if (stepDetail.getRelease()!=null) {
				ReleaseEntity releaseEntity=new ReleaseEntity();
				releaseEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getRelease().getIsChoise(),stepDetail.getRelease().getStatus(),stepDetail.getRelease().getTime()));
				//releaseEntity.setImageName(stepDetail.getRelease()..getImageName());
				//releaseEntity.setImageVersion(stepDetail.getRelease().getImageVersion());
				releaseEntity.setDestination(stepDetail.getRelease().getType()+"");
				releaseEntity.setType(stepDetail.getRelease().getExecMode()+"");
				releaseEntity.setVersionNumber(stepDetail.getRelease().getVersionNum());
				stepDetailEntity.setReleaseEntity(releaseEntity);
			}
			
			//测试自动化测试实体转换
			if (stepDetail.getTestAutoTest()!=null) {
				AutoTestEntity testAutoTestEntity=new AutoTestEntity();
				testAutoTestEntity.setAllNumber(String.valueOf(stepDetail.getTestAutoTest().getTotals()));
				testAutoTestEntity.setFallNumber(String.valueOf(stepDetail.getTestAutoTest().getFailNumber()));
				testAutoTestEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getTestAutoTest().getIsChoise(),stepDetail.getTestAutoTest().getStatus(),stepDetail.getTestAutoTest().getTime()));
				
				stepDetailEntity.setTestAutoTestEntity(testAutoTestEntity);
			}
			
			//测试部署实体转换
			if (stepDetail.getTestDeploy()!=null) {
				TestDeployEntity testDeployEntity=new TestDeployEntity();
				testDeployEntity.setAppSvcName(stepDetail.getTestDeploy().getAppSvcName());
				testDeployEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getTestDeploy().getIsChoise(),stepDetail.getTestDeploy().getStatus(),stepDetail.getTestDeploy().getTime()));
				testDeployEntity.setInstanceNum(stepDetail.getTestDeploy().getTotalInstanceNum()+"");
				testDeployEntity.setRunVersion(stepDetail.getTestDeploy().getServiceRunVersion());
				testDeployEntity.setRunVersionId(stepDetail.getTestDeploy().getServiceRunVersionId()+"");
				testDeployEntity.setServiceID(stepDetail.getTestDeploy().getAppSvcId());
				testDeployEntity.setState(stepDetail.getTestDeploy().getServiceState()+"");
				stepDetailEntity.setTestDeployEntity(testDeployEntity);
			}
			
			//测试集成测试实体转换
			if (stepDetail.getTestInteTest()!=null) {
				InteTestEntity testInteTestEntity=new InteTestEntity();
				testInteTestEntity.setAllNumber(stepDetail.getTestInteTest().getTotals());
				testInteTestEntity.setFallNumber(stepDetail.getTestInteTest().getFailNumber());
				testInteTestEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getTestInteTest().getIsChoise(),stepDetail.getTestInteTest().getStatus(),stepDetail.getTestInteTest().getTime()));
				
				stepDetailEntity.setTestInteTestEntity(testInteTestEntity);
			}
			//测试性能测试实体转换
			if (stepDetail.getTestPerformanceTest()!=null) {
				PerformanceTestEntity testPerformanceTestEntity=new PerformanceTestEntity();
				testPerformanceTestEntity.setAllNumber(String.valueOf(stepDetail.getTestPerformanceTest().getTotals()));
				testPerformanceTestEntity.setFallNumber(String.valueOf(stepDetail.getTestPerformanceTest().getFailNumber()));
				testPerformanceTestEntity.setMiniRespTime(String.valueOf(stepDetail.getTestPerformanceTest().getMiniRespTime()));
				testPerformanceTestEntity.setAverRespTime(String.valueOf(stepDetail.getTestPerformanceTest().getAverRespTime()));
				testPerformanceTestEntity.setMaxRespTime(String.valueOf(stepDetail.getTestPerformanceTest().getMaxRespTime()));
				testPerformanceTestEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getTestPerformanceTest().getIsChoise(),stepDetail.getTestPerformanceTest().getStatus(),stepDetail.getTestPerformanceTest().getTime()));
				
				stepDetailEntity.setTestPerformanceTestEntity(testPerformanceTestEntity);
			}
			//测试发布（生产）实体转换
			if (stepDetail.getTestRelease()!=null) {
				ReleaseEntity testReleaseEntity=new ReleaseEntity();
				testReleaseEntity.setStepDetailInfo(getStepDetailInfo(stepDetail.getTestRelease().getIschoise(),stepDetail.getTestRelease().getStatus(),stepDetail.getTestRelease().getTime()));
				//testReleaseEntity.setImageName(stepDetail.getTestRelease().getImageName());
				//testReleaseEntity.setImageVersion(stepDetail.getTestRelease().getImageVersion());
				testReleaseEntity.setDestination(stepDetail.getTestRelease().getType()+"");
				testReleaseEntity.setType(stepDetail.getTestRelease().getExecMode()+"");
				testReleaseEntity.setVersionNumber(stepDetail.getTestRelease().getVersionNum());
				
				stepDetailEntity.setTestReleaseEntity(testReleaseEntity);
			}
					
						
		}
		logger.debug("转换流水步骤详情信息成功！");
		return stepDetailEntity;
	}

	private StepDetailInfo getStepDetailInfo(int IsChoise,int status,Double time){
		StepDetailInfo stepDetailInfo=new StepDetailInfo();
		stepDetailInfo.setStatus(String.valueOf(status));
		stepDetailInfo.setTime(String.valueOf(time));
		stepDetailInfo.setIsChoise(String.valueOf(IsChoise));
		return stepDetailInfo;
	}

	
	
}
