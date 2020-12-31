package com.cmsz.paas.web.build.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.build.model.BuildEntity;
import com.cmsz.paas.web.build.model.BuildInfo;
import com.cmsz.paas.web.build.model.BuildRecordInfo;
import com.cmsz.paas.web.build.model.RepositoryInfo;

/**
 * 构建管理Service.
 * 
 * @author liaohw
 */
public interface BuildService {
	
	/**
	 * 查询构建详情
	 * 
	 * @param buildId   构建id
	 * @throws PaasWebException
	 */
	public BuildEntity queryBuild(String buildId) throws PaasWebException;

	/**
	 * 查询构建列表
	 * 
	 * @param appIds  应用id串，多个用逗号分隔
	 * @param token   页面输入需要查询的字符串
	 * @return 构建列表
	 * @throws PaasWebException
	 */
	public List<BuildInfo> queryBuildList(String appIds, String token) throws PaasWebException;
	
	
	/**
	 * 创建构建
	 * 
	 * @param BuildEntity  构建实体
	 * @throws PaasWebException
	 */
	public RestResult createBuild(BuildEntity build) throws PaasWebException;
	
	/**
	 * 修改构建
	 * 
	 * @param BuildEntity  构建实体
	 * @throws PaasWebException
	 */
	public RestResult modifyBuild(BuildEntity build) throws PaasWebException;
	
	/**
	 * 删除构建
	 * 
	 * @param buildId   构建id
	 * @throws PaasWebException
	 */
	public RestResult deleteBuild(String buildId) throws PaasWebException;
	
	/**
	 * 根据url查询当前登录用户曾使用过的代码库
	 * @param RepositoryInfo 代码库信息
	 * @param loginName 登录名
	 * @return
	 * @throws PaasWebException
	 */
	public RepositoryInfo queryRepository(RepositoryInfo repositoryInfo, String loginName) throws PaasWebException;
	
	/**
	 * 验证输入的用户名和密码
	 * @param RepositoryInfo 代码库信息
	 * @param loginName 登录名
	 * @return
	 * @throws PaasWebException
	 */
	public RepositoryInfo verifyRepositoryCertificate(RepositoryInfo repositoryInfo, String loginName) throws PaasWebException;
	
	/**
	 * 执行构建
	 * 
	 * @param buildId   构建id
	 * @throws PaasWebException
	 */
	public RestResult executeBuild(String buildId, String loginName) throws PaasWebException;
	
	/**
	 * 查询构建记录列表
	 * 
	 * @param buildId  构建id
	 * @param token   页面输入需要查询的字符串
	 * @return 构建记录列表
	 * @throws PaasWebException
	 */
	public List<BuildRecordInfo> queryBuildRecordList(String buildId, String token) throws PaasWebException;
	
	/**
	 * 查询构建记录日志
	 * 
	 * @param buildId   构建id
	 * @param buildRecordId   构建记录id
	 * @throws PaasWebException
	 */
	public RestResult queryBuildRecordLog(String buildId, String buildRecordId) throws PaasWebException;
	
	/**
	 * 查询分支名称
	 * 
	 * @param RepositoryInfo 代码库信息
	 * @throws PaasWebException
	 */
	public List<SelectType> queryBranches(RepositoryInfo repositoryInfo) throws PaasWebException;
	
}
