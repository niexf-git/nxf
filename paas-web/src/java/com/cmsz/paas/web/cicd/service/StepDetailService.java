package com.cmsz.paas.web.cicd.service;



import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.StepDetailEntity;


/**
 * 流水步骤详情service
 * @author ccl
 * 
 */
public interface StepDetailService {
	/**
	 * 查询流水步骤详情.
	 * 
	 * @param 流水ID
	 * @return 流水步骤详情
	 */
	public StepDetailEntity queryStepDetail(String flowId) throws PaasWebException;
	
}
