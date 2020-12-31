package com.cmsz.paas.web.cicd.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.CodeDetailsEntity;
import com.cmsz.paas.web.cicd.model.CodeProblemDetailsInfo;
import com.cmsz.paas.web.cicd.model.CodeProblemInfo;

/**
 * 代码详情service
 * @author ccl
 * 
 */
public interface CodeDetailsService {
	/**
	 * 查询代码详情列表.
	 * 
	 * @param 流水ID
	 * @return 代码详情列表
	 */
	public List<CodeDetailsEntity> queryCodeDetailsList(String flowId,String flowRecordId) throws PaasWebException;
	/**
	 * 查询代码详情.
	 * 
	 * @param 流水ID,问题类型,sonarUUID
	 * @return 代码详情
	 */
	public List<CodeProblemInfo> queryCodeDetails(String flowId,String problem,String uuid) throws PaasWebException;
	/**
	 * 查询问题代码详情.
	 * 
	 * @param 流水ID,问题类型，问题代码路径
	 * @return 问题代码详情
	 */
	public CodeProblemDetailsInfo queryProblemDetails(String flowId,String problem,String codePath) throws PaasWebException;
}
