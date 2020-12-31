package com.cmsz.paas.web.cicd.service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.AutoTestEntity;

public interface AutoTestService {
	
	/**
	 * 查询自动化测试的配置信息
	 * 
	 * @param flowId 流水id
	 * @return
	 */
	public AutoTestEntity queryAutoTest(String flowId) throws PaasWebException;

	/**
	 * 修改自动化测试配置信息
	 */
	public void updateAutoTest(String flowId, AutoTestEntity autoTestEntity) throws PaasWebException;

}
