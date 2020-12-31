package com.cmsz.paas.web.cicd.service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.InteTestEntity;

public interface InteTestService {

	/**
	 * 查询集成测试的配置信息
	 * 
	 * @param flowId 流水id
	 * @return
	 */
	public InteTestEntity queryInteTest(String flowId) throws PaasWebException;

	/**
	 * 修改集成测试配置信息
	 */
	public void updateInteTest(String flowId, InteTestEntity inteTestEntity) throws PaasWebException;

}
