package com.cmsz.paas.web.cicd.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.cicd.model.CodeDownloadAndCheckEntity;
import com.cmsz.paas.web.cicd.model.RepositoryInfo;
import com.cmsz.paas.web.cicd.service.CodeDownloadAndCheckService;

/**
 * 代码下载&审查 Action.
 * 
 * @author liaohw
 * @date 2017-8-25
 */
public class CodeDownloadAndCheckAction extends AbstractAction {

	private static final long serialVersionUID = -4455362536601293021L;

	private static final Logger logger = LoggerFactory
			.getLogger(CodeDownloadAndCheckAction.class);

	@Autowired
	private CodeDownloadAndCheckService codeDownloadAndCheckService;

	/** 流水id */
	private String flowId;

	/** 代码库信息 */
	private RepositoryInfo repositoryInfo;
	
	/** 接收页面输入的数据 */
	private CodeDownloadAndCheckEntity codeDownloadAndCheckEntity;
	
	/** 是否代码审查 */
	private String isCodeCheck;

	/**
	 * 查询代码下载&审查的配置信息
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryCodeDownloadAndCheck() {
		logger.info("开始执行查询代码下载&审查的配置信息，流水id：" + flowId);
		try {
			CodeDownloadAndCheckEntity codeDownloadAndCheckEntity = codeDownloadAndCheckService
					.queryCodeDownloadAndCheck(flowId);
			sendSuccessReslult(codeDownloadAndCheckEntity);
			logger.info("查询代码下载&审查的配置信息成功！");
		} catch (PaasWebException ex) {
			logger.error("查询代码下载&审查的配置信息错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询分支名称（用于页面下拉框展示）
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryBranches() throws Exception {
		logger.info("开始执行查询分支名称...");
		try {
			List<SelectType> branches = codeDownloadAndCheckService.queryBranches(repositoryInfo);
			sendSuccessReslult(branches);
			logger.info("查询分支名称完成");
		} catch (PaasWebException ex) {
			logger.error("查询分支名称错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询代码库信息，用于根据输入的url自动填充用户名密码
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryRepository() throws Exception {
		logger.info("开始执行查询代码库用户名密码信息，url地址：" + repositoryInfo.getUrl()
				+ "，构建类型：" + repositoryInfo.getRepositoryType());
		try {
			String loginName = (String) getSessionMap().get("loginName");
			RepositoryInfo repositoryInfo = codeDownloadAndCheckService
					.queryRepository(this.repositoryInfo, loginName);

			sendSuccessReslult(repositoryInfo);
			logger.info("查询代码库用户名密码完成");
		} catch (PaasWebException ex) {
			logger.error("查询代码库用户名密码错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 对输入的地址验证输入的代码库用户名和密码
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void verifyRepositoryCertificate() throws Exception {
		logger.info("开始执行验证代码库用户名密码...");
		try {
			String loginName = (String) getSessionMap().get("loginName");
			RepositoryInfo repository = codeDownloadAndCheckService.verifyRepositoryCertificate(
					repositoryInfo, loginName);
			// 返回svn库信息的id
			sendSuccessReslult(repository);
			logger.info("验证代码库用户名密码完成");
		} catch (PaasWebException ex) {
			logger.error("验证代码库用户名密码错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 对输入的多个代码库地址验证用户名和密码
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void verifyMultRepositoryCertificate() throws Exception {
		logger.info("开始执行验证多个代码库用户名密码...");
		try {
			String loginName = (String) getSessionMap().get("loginName");
			List<RepositoryInfo> repositoryList = codeDownloadAndCheckService.verifyMultRepositoryCertificate(
					codeDownloadAndCheckEntity, loginName);
			// 返回svn库信息的id
			sendSuccessReslult(repositoryList);
			logger.info("验证多个代码库用户名密码完成");
		} catch (PaasWebException ex) {
			logger.error("验证多个代码库用户名密码错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 修改代码下载&审查的配置信息
	 */
	public void modifyCodeDownloadAndCheck() throws Exception {
		logger.info("开始执行修改代码下载&审查的配置信息...");
		logger.info("页面提交的参数：" + codeDownloadAndCheckEntity.toString()+"  流水id："+flowId);// 调试完删除
		try {
			codeDownloadAndCheckService.modifyCodeDownloadAndCheck(flowId,codeDownloadAndCheckEntity);
			sendSuccessReslult();
			logger.info("修改代码下载&审查的配置信息成功！");
		} catch (PaasWebException e) {
			logger.error("修改代码下载&审查的配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/**
	 * 修改是否代码审查状态
	 * @throws Exception
	 */
	public void modifyCodeCheckState() throws Exception{
		logger.info("开始执行修改代码审查的状态，流水id："+flowId+" 是否代码审查："+isCodeCheck);
		try {
			codeDownloadAndCheckService.modifyCodeCheckState(flowId, isCodeCheck);
			sendSuccessReslult();
			logger.info("修改代码审查的状态成功！");
		} catch (PaasWebException e) {
			logger.error("修改代码审查的状态错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public RepositoryInfo getRepositoryInfo() {
		return repositoryInfo;
	}

	public void setRepositoryInfo(RepositoryInfo repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}

	public CodeDownloadAndCheckEntity getCodeDownloadAndCheckEntity() {
		return codeDownloadAndCheckEntity;
	}

	public void setCodeDownloadAndCheckEntity(
			CodeDownloadAndCheckEntity codeDownloadAndCheckEntity) {
		this.codeDownloadAndCheckEntity = codeDownloadAndCheckEntity;
	}

	public String getIsCodeCheck() {
		return isCodeCheck;
	}

	public void setIsCodeCheck(String isCodeCheck) {
		this.isCodeCheck = isCodeCheck;
	}

}
