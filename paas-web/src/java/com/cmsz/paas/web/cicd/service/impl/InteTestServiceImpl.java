package com.cmsz.paas.web.cicd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.InteTest;
import com.cmsz.paas.common.model.controller.response.StepDetail;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.InteTestDao;
import com.cmsz.paas.web.cicd.model.InteTestEntity;
import com.cmsz.paas.web.cicd.model.RepositoryInfo;
import com.cmsz.paas.web.cicd.service.InteTestService;
import com.cmsz.paas.web.constants.Constants;

/**
 * 
 * @author lixin
 * @version 创建时间：2017年9月4日 下午15:01:40
 */
@Service("inteTestService")
public class InteTestServiceImpl implements InteTestService {

	private static final Logger logger = LoggerFactory
			.getLogger(InteTestServiceImpl.class);

	@Autowired
	private InteTestDao inteTestDao;
	
	/**
	 * 查询集成测试配置信息
	 */
	@Override
	public InteTestEntity queryInteTest(String flowId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		InteTestEntity inteTestEntity = null;
		try {
			String url = RestUtils.restUrlTransform("queryInteTestUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = inteTestDao.get(url, StepDetail.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			StepDetail stepDetail = (StepDetail) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(stepDetail != null && stepDetail.getInteTest() != null){
					inteTestEntity = translateToInteTestEntity(stepDetail.getInteTest());
					return inteTestEntity;
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询集成测试配置信息错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_INTETEST_ERROR, ex.getMessage());
			}
		}
		return null;
	}
	
	/*
	 * 把控制中心的bean转换成前台InteTestEntity对象
	 */
	private InteTestEntity translateToInteTestEntity(InteTest inteTest) {
		InteTestEntity inteTestEntity = new InteTestEntity();
		RepositoryInfo repositoryInfo = new RepositoryInfo();
		
		//如果返回的代码类型大于0，则将其转为string类型
		if(inteTest.getCodeType() > 0) {
			repositoryInfo.setRepositoryType(inteTest.getCodeType()+"");
		}
		repositoryInfo.setName(inteTest.getUserName());
		repositoryInfo.setPassword(inteTest.getPassword());
		repositoryInfo.setBranchName(inteTest.getBranchName());
		repositoryInfo.setUrl(inteTest.getShellpath());
		
		inteTestEntity.setRepositoryInfo(repositoryInfo);
		inteTestEntity.setInteType(inteTest.getInteType());
//		inteTestEntity.setCodeType(inteTest.getCodeType());
//		inteTestEntity.setShellpath(inteTest.getShellpath());
//		inteTestEntity.setUserName(inteTest.getUserName());
//		inteTestEntity.setPassword(inteTest.getPassword());
//		inteTestEntity.setBranchName(inteTest.getBranchName());
		inteTestEntity.setTestCmd(inteTest.getTestCmd());
		inteTestEntity.setAliasName(inteTest.getAliasName());
		inteTestEntity.setFallNumber(inteTest.getFailNumber());
		inteTestEntity.setAllNumber(inteTest.getTotals());
		inteTestEntity.setRelationName(inteTest.getRelationName());
		inteTestEntity.setTime(inteTest.getTime());

		return inteTestEntity;
	}

	/**
	 * 修改集成测试配置信息
	 */
	@Override
	public void updateInteTest(String flowId, InteTestEntity inteTestEntity)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		try{
			InteTest inteTest = translateToControllerInteTestEntity(inteTestEntity);
			String url = RestUtils.restUrlTransform("updateInteTestUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = inteTestDao.update(url, inteTest, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改集成测试配置信息错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_INTETEST_ERROR, ex.getMessage());
			}
		}
	}
	
	/**
	 * 把前台bean转换成控制中心的bean
	 * @param inteTestEntity
	 * @return
	 */
	private InteTest translateToControllerInteTestEntity(InteTestEntity inteTestEntity){
		InteTest inteTest = new InteTest();
		
		inteTest.setInteType(inteTestEntity.getInteType());
		inteTest.setCodeType(Integer.parseInt(inteTestEntity.getRepositoryInfo().getRepositoryType()));
		inteTest.setShellpath(inteTestEntity.getRepositoryInfo().getUrl());
		inteTest.setUserName(inteTestEntity.getRepositoryInfo().getName());
		inteTest.setPassword(inteTestEntity.getRepositoryInfo().getPassword());
		inteTest.setBranchName(inteTestEntity.getRepositoryInfo().getBranchName());
		
		inteTest.setTestCmd(inteTestEntity.getTestCmd());
		inteTest.setAliasName(inteTestEntity.getAliasName());
		inteTest.setFailNumber(inteTestEntity.getFallNumber());
		inteTest.setTotals(inteTestEntity.getAllNumber());
//		inteTest.setRelationName(inteTestEntity.getRelationName());
		inteTest.setTime(inteTestEntity.getTime());
		inteTest.setSysUer(inteTestEntity.getSysUer());
		
		return inteTest;
	}
	
}
