package com.cmsz.paas.web.cicd.service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.CompileBuildEntity;

public interface CompileBuildService {

	/**
	 * 查询编译&构建的配置信息
	 * 
	 * @param flowId 流水id
	 * @return
	 */
	public CompileBuildEntity queryCompileBuild(String flowId) throws PaasWebException;
	
	/**
	 * 修改编译&构建的配置信息
	 * 
	 * @param flowId 流水id
	 * @param compileBuildEntity
	 * @return
	 */
	public void modifyCompileBuild(String flowId,CompileBuildEntity compileBuildEntity) throws PaasWebException;

}
