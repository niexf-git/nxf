package com.cmsz.paas.web.cicd.service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.PerformanceTestEntity;

public interface PerformanceTestService {
	
	/**
	 * 查询性能测试的配置信息
	 * 
	 * @param flowId 流水id
	 * @return
	 */
	public PerformanceTestEntity queryPerformanceTest(String flowId) throws PaasWebException;

	/**
	 * 修改性能测试配置信息
	 */
	public void updatePerformanceTest(String flowId, PerformanceTestEntity performanceTestEntity) throws PaasWebException;

}
