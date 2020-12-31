package com.cmsz.paas.web.cicd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.CodeAccountEntity;
import com.cmsz.paas.common.model.controller.entity.CodeAccountStatEntity;
import com.cmsz.paas.common.model.controller.entity.CodePathAccountEntity;
import com.cmsz.paas.common.model.controller.entity.CodePathEntity;
import com.cmsz.paas.common.model.controller.entity.DownloadCheckEntity;
import com.cmsz.paas.common.model.controller.response.BranchesList;
import com.cmsz.paas.common.model.controller.response.CodeAccountDetail;
import com.cmsz.paas.common.model.controller.response.CodeStatList;
import com.cmsz.paas.common.model.controller.response.DownloadCheckDetail;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.CodeDownloadAndCheckDao;
import com.cmsz.paas.web.cicd.model.CodeDownloadAndCheckEntity;
import com.cmsz.paas.web.cicd.model.RepositoryInfo;
import com.cmsz.paas.web.cicd.service.CodeDownloadAndCheckService;
import com.cmsz.paas.web.constants.Constants;

/**
 * 代码下载&审查 Service.
 * 
 * @author liaohw
 */
@Service("codeDownloadAndCheckService")
public class CodeDownloadAndCheckServiceImpl implements
		CodeDownloadAndCheckService {

	private static final Logger logger = LoggerFactory
			.getLogger(CodeDownloadAndCheckServiceImpl.class);

	@Autowired
	private CodeDownloadAndCheckDao codeDownloadAndCheckDao;

	@Override
	public CodeDownloadAndCheckEntity queryCodeDownloadAndCheck(String flowId)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform(
					"queryCodeDownloadAndCheckUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = codeDownloadAndCheckDao.get(url,
					DownloadCheckDetail.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			DownloadCheckDetail downloadCheckDetail = (DownloadCheckDetail) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				 if (downloadCheckDetail != null && downloadCheckDetail.getDownLoadCheck() != null) {
					 return translateToCodeDownloadAndCheckEntity(downloadCheckDetail.getDownLoadCheck());
				 }else{
					 return null;
				 }
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询代码下载&审查配置信息错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_CODE_DOWNLOAD_AND_CHECK_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/**
	 * 把控制中心的bean转换成前台使用的bean
	 * @param downloadCheckEntity
	 * @return
	 */
	private CodeDownloadAndCheckEntity translateToCodeDownloadAndCheckEntity(DownloadCheckEntity downloadCheckEntity){
		CodeDownloadAndCheckEntity codeDownloadAndCheckEntity = new CodeDownloadAndCheckEntity();
		codeDownloadAndCheckEntity.setRepositoryType(downloadCheckEntity.getCodeType()+"");
		codeDownloadAndCheckEntity.setTriggerType(downloadCheckEntity.getTriggerType()+"");
		codeDownloadAndCheckEntity.setTriggerTime(downloadCheckEntity.getPollScm());
		codeDownloadAndCheckEntity.setIsCodeCheck(downloadCheckEntity.getIsCheck()+"");
		codeDownloadAndCheckEntity.setScanDir(downloadCheckEntity.getCheckPath());   //扫描目录
		if(downloadCheckEntity.getCodeAccount() != null){
			codeDownloadAndCheckEntity.setName(downloadCheckEntity.getCodeAccount().getCodeUser());
			codeDownloadAndCheckEntity.setPassword(downloadCheckEntity.getCodeAccount().getCodePswd());
		}
		//代码库信息
		List<RepositoryInfo> repositoryInfoList = new ArrayList<RepositoryInfo>();
		if(downloadCheckEntity.getCodes() != null){
			for(CodePathEntity codePathEntity : downloadCheckEntity.getCodes()){
				RepositoryInfo repositoryInfo = new RepositoryInfo();
				repositoryInfo.setUrl(codePathEntity.getPath());
				repositoryInfo.setStorePath(codePathEntity.getLocalDir());
				repositoryInfo.setBranchName(codePathEntity.getBranchName());
				repositoryInfo.setVersionNumber(codePathEntity.getVersionNum());  //版本号
				repositoryInfo.setId(codePathEntity.getCodeAccountId()+"");
//				if(codePathEntity.getCodeAccount() != null){
//					repositoryInfo.setId(codePathEntity.getCodeAccount().getId()+"");
//					repositoryInfo.setName(codePathEntity.getCodeAccount().getCodeUser());
//					repositoryInfo.setPassword(codePathEntity.getCodeAccount().getCodePswd());
//				}
				repositoryInfoList.add(repositoryInfo);
			}
		}
		codeDownloadAndCheckEntity.setRepositoryInfo(repositoryInfoList);
		return codeDownloadAndCheckEntity;
	}
	
	@Override
	public RepositoryInfo queryRepository(RepositoryInfo repositoryInfo,
			String loginName) throws PaasWebException {
		RepositoryInfo repository = null;// new RepositoryInfo();
		String retCode = "";
		String msg = "";
		String url = repositoryInfo.getUrl();
		/*
		 * if(StringUtils.isNotBlank(url)){ url =
		 * UrlEscapeUtil.changeSpecialChar(url); }
		 */
		try {
			String restUrl = RestUtils.restUrlTransform("queryRepositoryUrl",
					url, loginName, repositoryInfo.getRepositoryType());
			logger.info("开始调用Rest接口：" + restUrl);
			
			ResponseInfo responseInfo = codeDownloadAndCheckDao.get(restUrl,
					CodeAccountDetail.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			CodeAccountDetail codeAccountDetail = (CodeAccountDetail) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (codeAccountDetail != null
						&& codeAccountDetail.getCodeAccountDetail() != null) {
					repository = translateToRepositoryInfo(codeAccountDetail
							.getCodeAccountDetail());
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询代码库用户名密码错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_REPOSITORY_ERROR,
						ex.getMessage());
			}
		}
		return repository;
	}

	/*
	 * 把控制中心的bean转换成前台RepositoryInfo对象
	 */
	private RepositoryInfo translateToRepositoryInfo(
			CodeAccountEntity codeAccountEntity) {
		RepositoryInfo repositoryInfo = new RepositoryInfo();
		repositoryInfo.setId(codeAccountEntity.getId() + "");
		//查询代码库信息，用于根据输入的url自动填充用户名密码    这个接口已经不使用
//		repositoryInfo.setName(codeAccountEntity.getCodeUser());
//		repositoryInfo.setPassword(codeAccountEntity.getCodePswd());
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
			ResponseInfo responseInfo = codeDownloadAndCheckDao.create(url, codePathEntity, IdValue.class);
			
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
		
		codePathEntity.setType(Integer.parseInt(repositoryInfo.getRepositoryType()));
		return codePathEntity;
	}

	@Override
	public void modifyCodeDownloadAndCheck(String flowId, CodeDownloadAndCheckEntity codeDownloadAndCheckEntity)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		try{
			DownloadCheckEntity downloadCheckEntity = translateToControllerDownloadCheckEntity(codeDownloadAndCheckEntity);
			String url = RestUtils.restUrlTransform("modifyCodeDownloadAndCheckUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = codeDownloadAndCheckDao.update(url, downloadCheckEntity, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改代码下载&审查的配置信息错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.MODIFY_CODE_DOWNLOAD_AND_CHECK_ERROR,ex.getMessage());
			}
		}
	}
	
	/**
	 * 把前台bean转换成控制中心的bean
	 * @param codeDownloadAndCheckEntity
	 * @return
	 */
	private DownloadCheckEntity translateToControllerDownloadCheckEntity(CodeDownloadAndCheckEntity codeDownloadAndCheckEntity){
		DownloadCheckEntity downloadCheckEntity = new DownloadCheckEntity();
		downloadCheckEntity.setCodeType(Integer.parseInt(codeDownloadAndCheckEntity.getRepositoryType()));
		downloadCheckEntity.setTriggerType(Integer.parseInt(codeDownloadAndCheckEntity.getTriggerType()));
		downloadCheckEntity.setPollScm(codeDownloadAndCheckEntity.getTriggerTime());
		downloadCheckEntity.setIsCheck(Integer.parseInt(codeDownloadAndCheckEntity.getIsCodeCheck()));
		downloadCheckEntity.setCheckPath(codeDownloadAndCheckEntity.getScanDir());
		CodeAccountEntity codeAccount = new CodeAccountEntity();
		codeAccount.setCodeUser(codeDownloadAndCheckEntity.getName());
		codeAccount.setCodePswd(codeDownloadAndCheckEntity.getPassword());
		downloadCheckEntity.setCodeAccount(codeAccount);
		
		//代码库列表
		List<CodePathEntity> codeList = new ArrayList<CodePathEntity>();
		if(codeDownloadAndCheckEntity.getRepositoryInfo() != null){
			for(int i=0;i<codeDownloadAndCheckEntity.getRepositoryInfo().size();i++){
				RepositoryInfo repositoryInfo = codeDownloadAndCheckEntity.getRepositoryInfo().get(i);
				if(repositoryInfo != null){
					CodePathEntity codePathEntity = new CodePathEntity();
					codePathEntity.setSorting(i);//添加排序字段
					codePathEntity.setPath(repositoryInfo.getUrl());
					codePathEntity.setLocalDir(repositoryInfo.getStorePath().trim());
					codePathEntity.setBranchName(repositoryInfo.getBranchName());
					codePathEntity.setVersionNum(repositoryInfo.getVersionNumber());
//					//账号信息
					codePathEntity.setCodeAccountId(Long.parseLong(repositoryInfo.getId()));
//					CodeAccountEntity codeAccountEntity = new CodeAccountEntity();
//					codeAccountEntity.setId(Long.parseLong(repositoryInfo.getId()));
//					codeAccountEntity.setCodeUser(repositoryInfo.getName().trim());
//					codeAccountEntity.setCodePswd(repositoryInfo.getPassword());
//					codePathEntity.setCodeAccount(codeAccountEntity);
					
					codeList.add(codePathEntity);
				}
			}
		}
		downloadCheckEntity.setCodes(codeList);
		return downloadCheckEntity;
	}

	@Override
	public void modifyCodeCheckState(String flowId, String isCodeCheck)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		try{
			String url = RestUtils.restUrlTransform("modifyIsCodeCheck", flowId, isCodeCheck);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = codeDownloadAndCheckDao.update(url, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改代码审查状态错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.MODIFY_IS_CODE_CHECK_ERROR,ex.getMessage());
			}
		}
		
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
			ResponseInfo responseInfo = codeDownloadAndCheckDao.get(url, BranchesList.class);
			
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

	@Override
	public List<RepositoryInfo> verifyMultRepositoryCertificate(
			CodeDownloadAndCheckEntity codeDownloadAndCheckEntity,
			String loginName) throws PaasWebException {
		List<RepositoryInfo> repositoryList = new ArrayList<RepositoryInfo>();
		String retCode = "";
		String msg = "";
		try {
			CodePathAccountEntity codePathCheckEntity = translateToCodePathAccountEntity(codeDownloadAndCheckEntity, loginName);
			String url = RestUtils.restUrlTransform("verifyMultRepositoryUrl");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = codeDownloadAndCheckDao.create(url, codePathCheckEntity, CodeStatList.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			CodeStatList codeStatList = (CodeStatList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(codeStatList != null && codeStatList.getCodeStat()!= null){
					for(CodeAccountStatEntity codeAccountStatEntity : codeStatList.getCodeStat()){
						RepositoryInfo repositoryInfo = new RepositoryInfo();
						repositoryInfo.setUrl(codeAccountStatEntity.getPath());
						repositoryInfo.setId(codeAccountStatEntity.getId());
						repositoryList.add(repositoryInfo);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("验证多个代码库用户名密码错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.VERIFY_REPOSITORY_ERROR,ex.getMessage());
			}
		}
		return repositoryList;
	}
	
	
	private CodePathAccountEntity translateToCodePathAccountEntity(CodeDownloadAndCheckEntity codeDownloadAndCheckEntity,
			String loginName){
		CodePathAccountEntity codePathAccountEntity = new CodePathAccountEntity();
		
		List<CodePathEntity> codePathEntityList = new ArrayList<CodePathEntity>();
		List<RepositoryInfo> repositoryInfoList = codeDownloadAndCheckEntity.getRepositoryInfo();
		for(RepositoryInfo repositoryInfo : repositoryInfoList){
			CodePathEntity codePathEntity = new CodePathEntity();
			codePathEntity.setPath(repositoryInfo.getUrl());
			codePathEntity.setType(Integer.parseInt(repositoryInfo.getRepositoryType()));
			codePathEntityList.add(codePathEntity);
		}
		codePathAccountEntity.setCodePath(codePathEntityList);
		
		CodeAccountEntity codeAccountEntity = new CodeAccountEntity();
		codeAccountEntity.setCodeUser(codeDownloadAndCheckEntity.getName());
		codeAccountEntity.setCodePswd(codeDownloadAndCheckEntity.getPassword());
		codePathAccountEntity.setCodeAccount(codeAccountEntity);
		
		return codePathAccountEntity;
	}
}
