package com.cmsz.paas.web.cicd.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.cicd.model.RepositoryInfo;
import com.cmsz.paas.web.cicd.model.CodeDownloadAndCheckEntity;

public interface CodeDownloadAndCheckService {

	/**
	 * 查询代码下载&审查的配置信息
	 * 
	 * @param flowId 流水id
	 * @return
	 */
	public CodeDownloadAndCheckEntity queryCodeDownloadAndCheck(String flowId)
			throws PaasWebException;
	
	/**
	 * git-查询分支名称
	 * @param repositoryInfo
	 * @return
	 * @throws PaasWebException
	 */
	public List<SelectType> queryBranches(RepositoryInfo repositoryInfo)
		throws PaasWebException;

	/**
	 * 根据url查询当前登录用户曾使用过的代码库
	 * @param RepositoryInfo 代码库信息
	 * @param loginName 登录名
	 * @return
	 * @throws PaasWebException
	 */
	public RepositoryInfo queryRepository(RepositoryInfo repositoryInfo, String loginName) 
			throws PaasWebException;
	
	/**
	 * 验证输入的用户名和密码
	 * @param RepositoryInfo 代码库信息
	 * @param loginName 登录名
	 * @return
	 * @throws PaasWebException
	 */
	public RepositoryInfo verifyRepositoryCertificate(RepositoryInfo repositoryInfo, String loginName) 
			throws PaasWebException;
	
	/**
	 * 验证多个代码库的用户名和密码
	 * @param CodeDownloadAndCheckEntity 代码库信息 和 用户名密码
	 * @param loginName 登录名
	 * @return
	 * @throws PaasWebException
	 */
	public List<RepositoryInfo> verifyMultRepositoryCertificate(CodeDownloadAndCheckEntity codeDownloadAndCheckEntity, String loginName) 
			throws PaasWebException;
	
	/**
	 * 修改代码下载&审查的配置信息
	 * @param flowId
	 * @param codeDownloadAndCheckEntity
	 * @throws PaasWebException
	 */
	public void modifyCodeDownloadAndCheck(String flowId, CodeDownloadAndCheckEntity codeDownloadAndCheckEntity)
			throws PaasWebException;
	
	/**
	 * 修改是否代码审查状态
	 * @param flowId
	 * @param isCodeCheck
	 * @throws PaasWebException
	 */
	public void modifyCodeCheckState(String flowId, String isCodeCheck)
			throws PaasWebException;
}
