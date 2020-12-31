package com.cmsz.paas.web.build.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.BuildRecordEntity;
import com.cmsz.paas.common.model.controller.entity.BuildStepEntity;
import com.cmsz.paas.common.model.controller.entity.CodeAccountEntity;
import com.cmsz.paas.common.model.controller.entity.CodePathEntity;
import com.cmsz.paas.common.model.controller.response.BranchesList;
import com.cmsz.paas.common.model.controller.response.BuildDetail;
import com.cmsz.paas.common.model.controller.response.BuildList;
import com.cmsz.paas.common.model.controller.response.BuildRecordDetail;
import com.cmsz.paas.common.model.controller.response.BuildRecordList;
import com.cmsz.paas.common.model.controller.response.CodeAccountDetail;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.model.controller.response.Logs;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.LogProcessUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.base.util.UrlEscapeUtil;
import com.cmsz.paas.web.build.dao.BuildDao;
import com.cmsz.paas.web.build.model.BuildEntity;
import com.cmsz.paas.web.build.model.BuildInfo;
import com.cmsz.paas.web.build.model.BuildRecordInfo;
import com.cmsz.paas.web.build.model.RepositoryInfo;
import com.cmsz.paas.web.build.service.BuildService;
import com.cmsz.paas.web.constants.Constants;

/**
 * 构建管理service实现.
 * 
 * @author liaohw
 */
@Service("buildService")
public class BuildServiceImpl implements BuildService {

	private static final Logger logger = LoggerFactory
			.getLogger(BuildServiceImpl.class);

	@Autowired
	private BuildDao buildDao;
	
	@Override
	public BuildEntity queryBuild(String buildId) throws PaasWebException {
		BuildEntity buildEntity = new BuildEntity();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryBuildUrl", buildId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.get(url, BuildDetail.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			BuildDetail buildDetail = (BuildDetail)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(buildDetail != null && buildDetail.getBuildDetail() != null){
					buildEntity = translateToBuildEntity(buildDetail.getBuildDetail());
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询构建详情错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_BUILD_ERROR, ex.getMessage());
			}
		}
		return buildEntity;
	}
	
	/*
	 * 把控制中心的构建bean转为前台BuildEntity对象
	 */
	private BuildEntity translateToBuildEntity(com.cmsz.paas.common.model.controller.entity.BuildEntity build){
		BuildEntity buildEntity = new BuildEntity();
		buildEntity.setId(build.getId()+"");
		buildEntity.setName(build.getName());
		buildEntity.setAppId(build.getAppId()+"");
		buildEntity.setCreator(build.getCreateBy());
		buildEntity.setDockerFilePath(build.getDockerfilePath());
		buildEntity.setDescription(build.getDescription());
		buildEntity.setType(build.getType()+"");
		//代码库信息
		List<RepositoryInfo> repositoryInfoList = new ArrayList<RepositoryInfo>();
		if(build.getCodes() != null){
			for(CodePathEntity codePathEntity : build.getCodes()){
				RepositoryInfo repositoryInfo = new RepositoryInfo();
				repositoryInfo.setUrl(codePathEntity.getPath());
				repositoryInfo.setStorePath(codePathEntity.getLocalDir());
				repositoryInfo.setBranchName(codePathEntity.getBranchName());
				if(codePathEntity.getCodeAccount() != null){
					repositoryInfo.setId(codePathEntity.getCodeAccount().getId()+"");
					repositoryInfo.setName(codePathEntity.getCodeAccount().getCodeUser());
					repositoryInfo.setPassword(codePathEntity.getCodeAccount().getCodePswd());
				}
				repositoryInfoList.add(repositoryInfo);
			}
		}
		buildEntity.setRepositoryInfo(repositoryInfoList);
		//构建步骤
		List<String> executeCommand = new ArrayList<String>();
		if(build.getBuildSteps() != null){
			for(BuildStepEntity buildStepEntity : build.getBuildSteps()){
				executeCommand.add(buildStepEntity.getCmd());
			}
		}
		buildEntity.setExecuteCommand(executeCommand);
		return buildEntity;
	}
	
	@Override
	public List<BuildInfo> queryBuildList(String appIds, String token)
			throws PaasWebException {
		List<BuildInfo> list = new ArrayList<BuildInfo>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryBuildListUrl", appIds, token);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.get(url, BuildList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			BuildList buildList = (BuildList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if( buildList != null && buildList.getBuildList() != null){
					for(com.cmsz.paas.common.model.controller.entity.BuildEntity buildEntity : buildList.getBuildList()){
						BuildInfo buildInfo = translateToBuildInfo(buildEntity);
						list.add(buildInfo);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询构建列表错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_BUILD_LIST_ERROR,ex.getMessage());
			}
		}
		return list;
	}
	
	/*
	 * 把控制中心的构建bean转为前台BuildInfo对象
	 */
	private BuildInfo translateToBuildInfo(com.cmsz.paas.common.model.controller.entity.BuildEntity buildEntity){
		BuildInfo buildInfo = new BuildInfo();
		buildInfo.setId(buildEntity.getId()+"");
		buildInfo.setName(buildEntity.getName());
		buildInfo.setAppId(buildEntity.getAppId()+"");
		buildInfo.setAppName(buildEntity.getAppName());
		buildInfo.setType(buildEntity.getType()+"");
		//最后一次构建的信息 
		buildInfo.setStatus(buildEntity.getLatestBuildStatus()+"");
		if(buildEntity.getLatestBuildStartTime().getTime() != 0){
			buildInfo.setLastStartTime(DateUtil.tranformDate(buildEntity.getLatestBuildStartTime().toString()));
		}else{
			buildInfo.setLastStartTime("-");
		}
		if(buildEntity.getLatestBuildEndTime().getTime() != 0){
			buildInfo.setLastEndTime(DateUtil.tranformDate(buildEntity.getLatestBuildEndTime().toString()));
		}else{
			buildInfo.setLastEndTime("-");
		}
		buildInfo.setBuildNumber(buildEntity.getBuildNum()+"");

		//镜像信息
		buildInfo.setImageId(buildEntity.getPrivateImageId()+"");
		buildInfo.setImageName(buildEntity.getPrivateImageName());
		
		buildInfo.setCreator(buildEntity.getCreateBy());
		//空时传过来的是1970-01-01 00:00:00
		if(buildEntity.getCreateTime().getTime() != 0){
			buildInfo.setCreateTime(DateUtil.tranformDate(buildEntity.getCreateTime().toString()));
		}else{
			buildInfo.setCreateTime("-");
		}
		return buildInfo;
	}
	

	@Override
	public RestResult createBuild(BuildEntity build) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			com.cmsz.paas.common.model.controller.entity.BuildEntity buildEntity = translateToControllerBuildEntity(build);
			String url = RestUtils.restUrlTransform("createBuildUrl");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.create(url, buildEntity, IdValue.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			IdValue buildId = (IdValue)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(buildId != null){
					result.setMsg(msg);
					result.setRetCode(retCode);
					result.setData(buildId.getId()+"");
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("创建构建错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.CREATE_BUILD_ERROR,ex.getMessage());
			}
		}
		return result;
	}
	
	/*
	 * 把前台数据转换成控制中心需要的bean
	 */
	private com.cmsz.paas.common.model.controller.entity.BuildEntity translateToControllerBuildEntity(BuildEntity buildEntity){
		com.cmsz.paas.common.model.controller.entity.BuildEntity build = new com.cmsz.paas.common.model.controller.entity.BuildEntity();
		//修改的时候会为null
		if(buildEntity.getAppId() != null){
			build.setAppId(Long.parseLong(buildEntity.getAppId()));
		}
		build.setCreateBy(buildEntity.getCreator());
		build.setName(buildEntity.getName());
		build.setDockerfilePath(buildEntity.getDockerFilePath().trim());
		build.setDescription(buildEntity.getDescription());
		build.setType(Integer.parseInt(buildEntity.getType()));
		//代码库列表
		List<CodePathEntity> codeList = new ArrayList<CodePathEntity>();
		if(buildEntity.getRepositoryInfo() != null){
			for(RepositoryInfo repositoryInfo : buildEntity.getRepositoryInfo()){
				if(repositoryInfo != null){
					CodePathEntity codePathEntity = new CodePathEntity();
					codePathEntity.setPath(repositoryInfo.getUrl());
					codePathEntity.setLocalDir(repositoryInfo.getStorePath().trim());
					codePathEntity.setBranchName(repositoryInfo.getBranchName());
					//账号信息
					CodeAccountEntity codeAccountEntity = new CodeAccountEntity();
					codeAccountEntity.setId(Long.parseLong(repositoryInfo.getId()));
					codeAccountEntity.setCodeUser(repositoryInfo.getName().trim());
					codeAccountEntity.setCodePswd(repositoryInfo.getPassword());
					codePathEntity.setCodeAccount(codeAccountEntity);
					
					codeList.add(codePathEntity);
				}
			}
		}
		build.setCodes(codeList);
		//执行命令列表
		List<BuildStepEntity> buildStepList = new ArrayList<BuildStepEntity>();
		if(buildEntity.getExecuteCommand() != null){
			List<String> executeCommandList = buildEntity.getExecuteCommand();
			for(int i=0; i<executeCommandList.size(); i++){
				BuildStepEntity buildStepEntity = new BuildStepEntity();
				buildStepEntity.setStep(i);
				buildStepEntity.setCmd(executeCommandList.get(i));
				buildStepList.add(buildStepEntity);
			}
		}
		build.setBuildSteps(buildStepList);
		
		return build;
	}
	
	@Override
	public RestResult modifyBuild(BuildEntity build) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			com.cmsz.paas.common.model.controller.entity.BuildEntity buildEntity = translateToControllerBuildEntity(build);
			String url = RestUtils.restUrlTransform("modifyBuildUrl", build.getId());
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.update(url, buildEntity, ResponseInfo.class);//第三个参数如何传 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			//便于以后扩展
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(retCode);
				result.setMsg(msg);
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改构建错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.MODIFY_BUILD_ERROR,ex.getMessage());
			}
		}
		return result;
	}
	
	@Override
	public RestResult deleteBuild(String buildId) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("deleteBuildUrl", buildId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.delete(url, ResponseInfo.class);//第三个参数如何传 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(retCode);
				result.setMsg(msg);
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("删除构建错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_BUILD_ERROR,ex.getMessage());
			}
		}
		return result;
	}

	@Override
	public RepositoryInfo queryRepository(RepositoryInfo repositoryInfo, String loginName)
			throws PaasWebException {
		RepositoryInfo repository = null;//new RepositoryInfo();
		String retCode = "";
		String msg = "";
		String url = repositoryInfo.getUrl();
		logger.info("开始调用Rest接口：" + url);
		/*if(StringUtils.isNotBlank(url)){
			url = UrlEscapeUtil.changeSpecialChar(url);
		}*/
		try {
			String restUrl = RestUtils.restUrlTransform("queryRepositoryUrl", url, loginName, repositoryInfo.getType());
			ResponseInfo responseInfo = buildDao.get(restUrl, CodeAccountDetail.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			CodeAccountDetail codeAccountDetail = (CodeAccountDetail)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if( codeAccountDetail != null && codeAccountDetail.getCodeAccountDetail() != null){
					repository = translateToRepositoryInfo(codeAccountDetail.getCodeAccountDetail());
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询代码库用户名密码错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_REPOSITORY_ERROR,ex.getMessage());
			}
		}
		return repository;
	}
	
	/*
	 * 把控制中心的bean转换成前台RepositoryInfo对象
	 */
	private RepositoryInfo translateToRepositoryInfo(CodeAccountEntity codeAccountEntity){
		RepositoryInfo repositoryInfo = new RepositoryInfo();
		repositoryInfo.setId(codeAccountEntity.getId()+"");
		repositoryInfo.setName(codeAccountEntity.getCodeUser());
		repositoryInfo.setPassword(codeAccountEntity.getCodePswd());
		return repositoryInfo;
	}
	
	@Override
	public RepositoryInfo verifyRepositoryCertificate(RepositoryInfo repositoryInfo,
			String loginName) throws PaasWebException {
		RepositoryInfo repository = new RepositoryInfo();
		String retCode = "";
		String msg = "";
		try {
			CodePathEntity codePathEntity = translateToCodePathEntity(repositoryInfo, loginName);
			String url = RestUtils.restUrlTransform("verifyRepositoryUrl");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.create(url, codePathEntity, IdValue.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			IdValue repositoryId = (IdValue)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(repositoryId != null){
					repository.setId(repositoryId.getId()+"");
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("验证代码库用户名密码错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.VERIFY_REPOSITORY_ERROR,ex.getMessage());
			}
		}
		return repository;
	}
	
	/*
	 * 把前台数据转换成控制中心需要的bean
	 */
	private CodePathEntity translateToCodePathEntity(RepositoryInfo repositoryInfo, String loginName){
		CodePathEntity codePathEntity = new CodePathEntity();
		codePathEntity.setPath(repositoryInfo.getUrl());
		//代码库信息
		CodeAccountEntity codeAccountEntity = new CodeAccountEntity();
		codeAccountEntity.setCodeUser(repositoryInfo.getName());
		codeAccountEntity.setCodePswd(repositoryInfo.getPassword());
		codeAccountEntity.setSysUser(loginName);//系统登录用户
		codePathEntity.setCodeAccount(codeAccountEntity);
		
		codePathEntity.setType(Integer.parseInt(repositoryInfo.getType()));
		return codePathEntity;
	}

	@Override
	public RestResult executeBuild(String buildId, String loginName) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		BufferedReader reader = null;
		try {
			String url = RestUtils.restUrlTransform("executeBuildUrl", buildId, loginName);
			logger.info("开始调用Rest接口：" + url);
			
			String currentLine = null;
			reader = buildDao.update(url);
			while((currentLine = reader.readLine()) != null){
				ResponseInfo responseInfo = JsonUtil.json2ResponseInfoBean(currentLine, BuildRecordDetail.class);
				retCode = responseInfo.getRetCode();
				msg = PropertiesUtil.getValue(retCode);
				logger.info("调用执行构建的rest接口返回码：" + retCode + ", 返回信息：" + msg);
				// 出错就断开长连接
				if (!retCode.contains("PAAS-00") && !retCode.equals(Constants.REST_CODE_SUCCESS)){//不是PAAS-00开头的都是错误码
					throw new PaasWebException(retCode, msg);
				}
				//只循环一次，不走长连接，构建记录状态在列表上刷新
				break;
			}
		} catch (Exception ex) {
			logger.error("执行构建错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, "执行构建错误！");
			} else {
				throw new PaasWebException(Constants.EXECUTE_BUILD_ERROR,ex.getMessage());
			}
		}finally {
			try {
				if(reader != null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new PaasWebException(Constants.WEB_SOCKET_STREAM_CLOSE_ERROR,e.getMessage());
			}
		}
		//此处的RestResult没有用到,用于以后扩展
		return result;
	}

	@Override
	public List<BuildRecordInfo> queryBuildRecordList(String buildId,
			String token) throws PaasWebException {
		List<BuildRecordInfo> list = new ArrayList<BuildRecordInfo>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryBuildRecordListUrl", buildId, token);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.get(url, BuildRecordList.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			BuildRecordList buildRecordList = (BuildRecordList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if( buildRecordList != null && buildRecordList.getBuildRecordList() != null){
					for(BuildRecordEntity buildRecordEntity : buildRecordList.getBuildRecordList()){
						BuildRecordInfo buildRecordInfo = translateToBuildRecordInfo(buildRecordEntity);
						list.add(buildRecordInfo);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询构建记录列表错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_BUILD_RECORD_LIST_ERROR,ex.getMessage());
			}
		}
		return list;
	}
	
	/*
	 * 把控制中心的bean转换成前台BuildRecordInfo对象
	 */
	private BuildRecordInfo translateToBuildRecordInfo(BuildRecordEntity buildRecordEntity){
		BuildRecordInfo buildRecordInfo = new BuildRecordInfo();
		buildRecordInfo.setId(buildRecordEntity.getId()+"");
		buildRecordInfo.setStatus(buildRecordEntity.getStatus()+"");
		buildRecordInfo.setImageVersion(buildRecordEntity.getVersion());
		buildRecordInfo.setSvnVersion(buildRecordEntity.getRevision());
		if(buildRecordEntity.getStartTime().getTime() != 0){
			buildRecordInfo.setStartTime(DateUtil.tranformDate(buildRecordEntity.getStartTime().toString()));
		}else{
			buildRecordInfo.setStartTime("-");
		}
		if(buildRecordEntity.getEndTime().getTime() != 0){
			buildRecordInfo.setEndTime(DateUtil.tranformDate(buildRecordEntity.getEndTime().toString()));
		}else{
			buildRecordInfo.setEndTime("-");
		}
		buildRecordInfo.setOperator(buildRecordEntity.getCreateBy());
		return buildRecordInfo;
	}

	@Override
	public RestResult queryBuildRecordLog(String buildId, String buildRecordId)
			throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		BufferedReader reader = null;
		try {
			String url = RestUtils.restUrlTransform("queryBuildRecordLogUrl", buildId, buildRecordId);
			logger.info("开始调用Rest接口：" + url);
			
			String logContent = "";    //日志内容
			String currentLine = null;
			reader = buildDao.get(url);      
			while((currentLine = reader.readLine()) != null){
				ResponseInfo responseInfo = JsonUtil.json2ResponseInfoBean(currentLine, Logs.class);
				retCode = responseInfo.getRetCode();
				msg = PropertiesUtil.getValue(retCode);
				Logs logs = (Logs)responseInfo.getData();
				logger.debug("查询构建记录日志的rest接口返回码：" + retCode + ", 返回信息：" + msg);
				
				if("PAAS-00004".equals(retCode) && logs != null){
					logContent += logs.getLogs();
				}else if (Constants.REST_CODE_SUCCESS.equals(retCode)){
					result.setRetCode(retCode);
					result.setMsg(msg);
				    result.setData(LogProcessUtil.processLogContent(logContent));
				}else{
					throw new PaasWebException(retCode, msg);
				}
			}
		} catch (Exception ex) {
			logger.error("查询构建记录日志错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_BUILD_RECORD_LOG_ERROR,ex.getMessage());
			}
		}finally {
			try {
				if(reader != null){
					reader.close();
				}
			} catch (IOException e) {
				logger.error("长连接流关闭错误",e);
				throw new PaasWebException(Constants.WEB_SOCKET_STREAM_CLOSE_ERROR,e.getMessage());
			}
		}
		return result;
	}

	@Override
	public List<SelectType> queryBranches(RepositoryInfo repositoryInfo)
			throws PaasWebException {
		List<SelectType> list = new ArrayList<SelectType>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryBranchesUrl", repositoryInfo.getUrl(), repositoryInfo.getName(),repositoryInfo.getPassword());
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = buildDao.get(url, BranchesList.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			BranchesList branchesList = (BranchesList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if( branchesList != null && branchesList.getBranchesList() != null){
					for(String branch : branchesList.getBranchesList()){
						SelectType selectType = new SelectType();
						selectType.setText(branch);
						selectType.setValue(branch);
						list.add(selectType);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询分支名称错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_BRANCHES_ERROR,ex.getMessage());
			}
		}
		return list;
	}
	
}
