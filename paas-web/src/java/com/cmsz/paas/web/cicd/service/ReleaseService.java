package com.cmsz.paas.web.cicd.service;

import com.cmsz.paas.web.cicd.model.ReleaseEntity;

public interface ReleaseService {

	/**
	 * 查询发布的配置信息
	 * 
	 * @param flowId 流水id
	 * @return
	 */
	public ReleaseEntity queryRelease(String flowId) throws Exception;

	/**
	 * 修改发布的配置信息
	 * 
	 * @param flowId 流水id
	 * @param releaseEntity
	 * @throws Exception
	 */
	public void modifyRelease(String flowId, ReleaseEntity releaseEntity)
			throws Exception;
	
	/**
	 * 镜像版本由开发直接发布生产
	 * 
	 * @param flowId 流水id
	 * @throws Exception
	 */
	public void releaseProduction(String flowId) throws Exception;

}
