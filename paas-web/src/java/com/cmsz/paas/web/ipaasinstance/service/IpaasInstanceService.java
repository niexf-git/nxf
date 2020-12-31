package com.cmsz.paas.web.ipaasinstance.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.ipaasinstance.model.IpaasInstance;

/**
 * 基础服务实例接口
 * 
 * @author ccl 2016-5-3
 */
public interface IpaasInstanceService {
	/**
	 * 诊断
	 * 
	 * @param 基础服务id
	 * @return
	 */
	public String diagnosisIpass(String ipaasServiceId) throws PaasWebException;

	/**
	 * 查询基础服务实例列表
	 * 
	 * @param 基础服务id
	 * @return 基础服务实例列表
	 */
	public List<IpaasInstance> queryIpaasServiceInstsById(String ipaasServiceId)
			throws PaasWebException;
	
}
