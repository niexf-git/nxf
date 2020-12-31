package com.cmsz.paas.web.cicd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.PerformanceTest;
import com.cmsz.paas.common.model.controller.response.StepDetail;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.model.PerformanceTestEntity;
import com.cmsz.paas.web.cicd.model.RepositoryInfo;
import com.cmsz.paas.web.cicd.service.PerformanceTestService;
import com.cmsz.paas.web.cicd.dao.PerformanceTestDao;
import com.cmsz.paas.web.constants.Constants;

/**
 * 查询性能测试配置信息
 * 
 * @author lixin
 * @version 创建时间：2017年11月23日 
 */
@Service("PerformanceTestService")
public class PerformanceTestServiceImpl implements PerformanceTestService {

	private static final Logger logger = LoggerFactory
			.getLogger(PerformanceTestServiceImpl.class);

	@Autowired
	private PerformanceTestDao performanceTestDao;

	@Override
	public PerformanceTestEntity queryPerformanceTest(String flowId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		PerformanceTestEntity PerformanceTestEntity = null;
		try {
			String url = RestUtils.restUrlTransform("queryPerformanceTestUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = performanceTestDao.get(url, StepDetail.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			StepDetail stepDetail = (StepDetail) responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(stepDetail != null && stepDetail.getPerformanceTest() != null){
					PerformanceTestEntity = translateToPerformanceTestEntity(stepDetail.getPerformanceTest());
					return PerformanceTestEntity;
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询性能测试配置信息错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_PERFORMANCETEST_ERROR, ex.getMessage());
			}
		}
		return null;
	}
	
	/*
	 * 把控制中心的bean转换成前台PerformanceTestEntity对象
	 */
	private PerformanceTestEntity translateToPerformanceTestEntity(PerformanceTest performanceTest) {
		PerformanceTestEntity PerformanceTestEntity = new PerformanceTestEntity();
//		PerformanceTestEntity.setCodeType(performanceTest.getCodeType() == 1?"svn":"git"); // 1-svn 2-git
//		PerformanceTestEntity.setShellath(performanceTest.getShellpath());
//		PerformanceTestEntity.setUserName(performanceTest.getUserName());
//		PerformanceTestEntity.setPassword(performanceTest.getPassword());
		PerformanceTestEntity.setTestCmd(performanceTest.getTestCmd());
		PerformanceTestEntity.setFallNumber(performanceTest.getFailNumber() + "");
		PerformanceTestEntity.setAllNumber(performanceTest.getTotals() + "");
//		PerformanceTestEntity.setBranchName(PerformanceTest.getBranchName());
		
		RepositoryInfo repositoryInfo = new RepositoryInfo();
		repositoryInfo.setName(performanceTest.getUserName());
		repositoryInfo.setPassword(performanceTest.getPassword());
		// codeType是int类型，默认值是0
		repositoryInfo.setRepositoryType(performanceTest.getCodeType()==0?"1":performanceTest.getCodeType()+"");
		repositoryInfo.setUrl(performanceTest.getShellpath());
		repositoryInfo.setBranchName(performanceTest.getBranchName());
		
		PerformanceTestEntity.setRepositoryInfo(repositoryInfo);
		
		return PerformanceTestEntity;
	}

	/**
	 * 修改性能测试配置信息
	 */
	@Override
	public void updatePerformanceTest(String flowId, PerformanceTestEntity performanceTestEntity)
			throws PaasWebException {
		
		//flowId = performanceTestEntity.getFlowId();
		logger.info("flowId=" + flowId);
		
		String retCode = "";
		String msg = "";
		try{
			PerformanceTest performanceTest = translateToControllerPerformanceTestEntity(performanceTestEntity);
			String url = RestUtils.restUrlTransform("updatePerformanceTestUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = performanceTestDao.update(url, performanceTest, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改性能测试配置信息错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_PERFORMANCETEST_ERROR, ex.getMessage());
			}
		}
	}
	
	/**
	 * 把前台bean转换成控制中心的bean
	 * @param PerformanceTestEntity
	 * @return
	 */
	private PerformanceTest translateToControllerPerformanceTestEntity(PerformanceTestEntity performanceTestEntity){
		PerformanceTest PerformanceTest = new PerformanceTest();
		PerformanceTest.setCodeType(Integer.parseInt(performanceTestEntity.getRepositoryInfo().getRepositoryType())); // 1-svn 2-git
		PerformanceTest.setShellpath(performanceTestEntity.getRepositoryInfo().getUrl());
		PerformanceTest.setUserName(performanceTestEntity.getRepositoryInfo().getName());
		PerformanceTest.setPassword(performanceTestEntity.getRepositoryInfo().getPassword());
		PerformanceTest.setBranchName(performanceTestEntity.getRepositoryInfo().getBranchName());
		PerformanceTest.setTestCmd(performanceTestEntity.getTestCmd());
		PerformanceTest.setSysUer(performanceTestEntity.getSysUer());
		return PerformanceTest;
	}

}
