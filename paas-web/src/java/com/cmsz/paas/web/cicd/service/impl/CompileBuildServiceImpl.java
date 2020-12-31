package com.cmsz.paas.web.cicd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.CodePathEntity;
import com.cmsz.paas.common.model.controller.entity.QueryBuild;
import com.cmsz.paas.common.model.controller.response.QueryBuildDetail;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.CompileBuildDao;
import com.cmsz.paas.web.cicd.model.CompileBuildEntity;
import com.cmsz.paas.web.cicd.model.RepositoryInfo;
import com.cmsz.paas.web.cicd.service.CompileBuildService;
import com.cmsz.paas.web.constants.Constants;

@Service("compileBuildService")
public class CompileBuildServiceImpl implements CompileBuildService {

	private static final Logger logger = LoggerFactory
			.getLogger(CompileBuildServiceImpl.class);

	@Autowired
	private CompileBuildDao compileBuildDao;

	@Override
	public CompileBuildEntity queryCompileBuild(String flowId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryCompileBuildUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = compileBuildDao.get(url, QueryBuildDetail.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			QueryBuildDetail queryBuildDetail = (QueryBuildDetail) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				 if (queryBuildDetail != null && queryBuildDetail.getQueryBuild() != null) {
					 return translateToCompileBuildEntity(queryBuildDetail.getQueryBuild());
				 }else{
					 return null;
				 }
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询编译&构建配置信息错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_COMPILE_BUILD_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/**
	 * 把控制中心的bean转换成前台使用的bean
	 * @param compileBuildInfo
	 * @return
	 */
	private CompileBuildEntity translateToCompileBuildEntity(QueryBuild compileBuildInfo){
		CompileBuildEntity compileBuildEntity = new CompileBuildEntity();
		compileBuildEntity.setDockerFilePath(compileBuildInfo.getDockerFile());
		compileBuildEntity.setExecuteCommand(compileBuildInfo.getCmd());
		compileBuildEntity.setIsUnitTest(compileBuildInfo.getIsUnitTest()+"");
		compileBuildEntity.setUnitTestReportDir(compileBuildInfo.getUniTeRepDir());
		compileBuildEntity.setUnitTestReport(compileBuildInfo.getUniTeRepFile());
		compileBuildEntity.setJacocoReportDir(compileBuildInfo.getCovFileDir());
		compileBuildEntity.setJacocoReport(compileBuildInfo.getCovFile());	
		
		//代码库信息
		List<RepositoryInfo> repositoryInfoList = new ArrayList<RepositoryInfo>();
		if(compileBuildInfo.getCodePath() != null){
			for(CodePathEntity codePathEntity : compileBuildInfo.getCodePath()){
				RepositoryInfo repositoryInfo = new RepositoryInfo();
				repositoryInfo.setUrl(codePathEntity.getPath());
				repositoryInfo.setStorePath(codePathEntity.getLocalDir());
				repositoryInfo.setBranchName(codePathEntity.getBranchName());
//				if(codePathEntity.getCodeAccount() != null){
//					repositoryInfo.setId(codePathEntity.getCodeAccount().getId()+"");
//					repositoryInfo.setName(codePathEntity.getCodeAccount().getCodeUser());
//					repositoryInfo.setPassword(codePathEntity.getCodeAccount().getCodePswd());
//				}
				repositoryInfoList.add(repositoryInfo);
			}
		}
		compileBuildEntity.setRepositoryInfo(repositoryInfoList);
		
		return compileBuildEntity;
	}

	@Override
	public void modifyCompileBuild(String flowId,CompileBuildEntity compileBuildEntity) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try{
			QueryBuild compileBuildInfo = translateToControllerQueryBuild(compileBuildEntity);
			String url = RestUtils.restUrlTransform("modifyCompileBuildurl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = compileBuildDao.update(url, compileBuildInfo, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改编译&构建配置信息错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.MODIFY_COMPILE_BUILD_ERROR,ex.getMessage());
			}
		}
	}
	
	/**
	 * 把前台bean转换成控制中心的bean
	 * @param compileBuildEntity
	 * @return
	 */
	private QueryBuild translateToControllerQueryBuild(CompileBuildEntity compileBuildEntity){
		QueryBuild compileBuildInfo = new QueryBuild();
		compileBuildInfo.setDockerFile(compileBuildEntity.getDockerFilePath());
		compileBuildInfo.setCmd(compileBuildEntity.getExecuteCommand());
		compileBuildInfo.setIsUnitTest(Integer.parseInt(compileBuildEntity.getIsUnitTest()));
		compileBuildInfo.setUniTeRepDir(compileBuildEntity.getUnitTestReportDir());
		compileBuildInfo.setUniTeRepFile(compileBuildEntity.getUnitTestReport());
		compileBuildInfo.setCovFileDir(compileBuildEntity.getJacocoReportDir());
		compileBuildInfo.setCovFile(compileBuildEntity.getJacocoReport());
		
		return compileBuildInfo;
	}

}
