package com.cmsz.paas.web.cicd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.AutoTest;
import com.cmsz.paas.common.model.controller.response.StepDetail;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.model.AutoTestEntity;
import com.cmsz.paas.web.cicd.model.RepositoryInfo;
import com.cmsz.paas.web.cicd.dao.AutoTestDao;
import com.cmsz.paas.web.cicd.service.AutoTestService;
import com.cmsz.paas.web.constants.Constants;

/**
 * 查询自动化测试配置信息
 * 
 * @author lin.my
 * @version 创建时间：2017年8月28日 下午3:08:42
 */
@Service("autoTestService")
public class AutoTestServiceImpl implements AutoTestService {

	private static final Logger logger = LoggerFactory
			.getLogger(AutoTestServiceImpl.class);

	@Autowired
	private AutoTestDao autoTestDao;

	@Override
	public AutoTestEntity queryAutoTest(String flowId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		AutoTestEntity autoTestEntity = null;
		try {
			String url = RestUtils.restUrlTransform("queryAutoTestUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = autoTestDao.get(url, StepDetail.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			StepDetail stepDetail = (StepDetail) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(stepDetail != null && stepDetail.getAutotest() != null){
					autoTestEntity = translateToAutoTestEntity(stepDetail.getAutotest());
					return autoTestEntity;
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询自动化测试配置信息错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_AUTOTEST_ERROR, ex.getMessage());
			}
		}
		return null;
	}
	
	/*
	 * 把控制中心的bean转换成前台AutoTestEntity对象
	 */
	private AutoTestEntity translateToAutoTestEntity(AutoTest autoTest) {
		AutoTestEntity autoTestEntity = new AutoTestEntity();
//		autoTestEntity.setCodeType(autoTest.getCodeType() == 1?"svn":"git"); // 1-svn 2-git
//		autoTestEntity.setShellath(autoTest.getShellpath());
//		autoTestEntity.setUserName(autoTest.getUserName());
//		autoTestEntity.setPassword(autoTest.getPassword());
		autoTestEntity.setTestCmd(autoTest.getTestCmd());
		autoTestEntity.setFallNumber(autoTest.getFailNumber() + "");
		autoTestEntity.setAllNumber(autoTest.getTotals() + "");
//		autoTestEntity.setBranchName(autoTest.getBranchName());
		
		RepositoryInfo repositoryInfo = new RepositoryInfo();
		repositoryInfo.setName(autoTest.getUserName());
		repositoryInfo.setPassword(autoTest.getPassword());
		// codeType是int类型，默认值是0
		repositoryInfo.setRepositoryType(autoTest.getCodeType()==0?"1":autoTest.getCodeType()+"");
		repositoryInfo.setUrl(autoTest.getShellpath());
		repositoryInfo.setBranchName(autoTest.getBranchName());
		
		autoTestEntity.setRepositoryInfo(repositoryInfo);
		
		return autoTestEntity;
	}

	/**
	 * 修改自动化测试配置信息
	 */
	@Override
	public void updateAutoTest(String flowId, AutoTestEntity autoTestEntity)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		try{
			AutoTest autoTest = translateToControllerAutoTestEntity(autoTestEntity);
			String url = RestUtils.restUrlTransform("updateAutoTestUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = autoTestDao.update(url, autoTest, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改自动化测试配置信息错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_AUTOTEST_ERROR, ex.getMessage());
			}
		}
	}
	
	/**
	 * 把前台bean转换成控制中心的bean
	 * @param autoTestEntity
	 * @return
	 */
	private AutoTest translateToControllerAutoTestEntity(AutoTestEntity autoTestEntity){
		AutoTest autoTest = new AutoTest();
		autoTest.setCodeType(Integer.parseInt(autoTestEntity.getRepositoryInfo().getRepositoryType())); // 1-svn 2-git
		autoTest.setShellpath(autoTestEntity.getRepositoryInfo().getUrl());
		autoTest.setUserName(autoTestEntity.getRepositoryInfo().getName());
		autoTest.setPassword(autoTestEntity.getRepositoryInfo().getPassword());
		autoTest.setBranchName(autoTestEntity.getRepositoryInfo().getBranchName());
		autoTest.setTestCmd(autoTestEntity.getTestCmd());
		autoTest.setSysUer(autoTestEntity.getSysUer());
		return autoTest;
	}

}
