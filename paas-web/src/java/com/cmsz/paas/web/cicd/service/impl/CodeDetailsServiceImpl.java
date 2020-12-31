package com.cmsz.paas.web.cicd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.CodeCheckStatementEntity;
import com.cmsz.paas.common.model.controller.entity.CodeProblemDetailEntity;
import com.cmsz.paas.common.model.controller.entity.CodeProblemPathEntity;
import com.cmsz.paas.common.model.controller.response.CodeCheckList;
import com.cmsz.paas.common.model.controller.response.CodeProblemList;
import com.cmsz.paas.common.model.controller.response.CodeProblemPathList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.CodeDetailsDao;
import com.cmsz.paas.web.cicd.model.CodeDetailsEntity;
import com.cmsz.paas.web.cicd.model.CodeProblemDetailsInfo;
import com.cmsz.paas.web.cicd.model.CodeProblemInfo;
import com.cmsz.paas.web.cicd.service.CodeDetailsService;
import com.cmsz.paas.web.constants.Constants;

/**
 * 代码详情service实现
 * 
 * @author ccl
 * 
 */
@Service("codeDetailsService")
public class CodeDetailsServiceImpl implements CodeDetailsService {
	private static final Logger logger = LoggerFactory
			.getLogger(CodeDetailsServiceImpl.class);
	@Autowired
	private CodeDetailsDao codeDetailsDao;

	@Override
	public List<CodeDetailsEntity> queryCodeDetailsList(String flowId,String flowRecordId)
			throws PaasWebException {
		List<CodeDetailsEntity> list = new ArrayList<CodeDetailsEntity>();
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCodeDetailsListUrl",
				flowId,flowRecordId);
		logger.debug("开始调用查询代码详情列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = codeDetailsDao.get(url,
					CodeCheckList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询代码详情列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			CodeCheckList codelist = (CodeCheckList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (codelist != null) {
					for (CodeCheckStatementEntity codeCheckStatementEntity : codelist.getCodeCheckList()) {
						list.add(genCodeDetailsEntity(codeCheckStatementEntity));
					}
				}
				logger.info("调用查询代码详情列表Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询代码详情列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_CODEDETAILSLIST_ERROR
						+ "]查询代码详情列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_CODEDETAILSLIST_ERROR, ex.getMessage());
			}
		}
		return list;
	}

	/**
	 * 代码详情实体转换
	 * 
	 * @param CodeCheckEntity
	 * 
	 * @return CodeDetailsEntity
	 */
	private CodeDetailsEntity genCodeDetailsEntity(
			CodeCheckStatementEntity codeCheckStatementEntity) {
		CodeDetailsEntity codeDetailsEntity = new CodeDetailsEntity();
		codeDetailsEntity.setSonarUUID(codeCheckStatementEntity.getSonarUuid());
		codeDetailsEntity.setBlockerNum(codeCheckStatementEntity.getBlockerNum());
		codeDetailsEntity.setCodenumber(codeCheckStatementEntity.getCodeNumber());
		codeDetailsEntity.setComplexity(codeCheckStatementEntity.getComplexity());
		codeDetailsEntity.setCreateTime(codeCheckStatementEntity.getCreateTime());
		codeDetailsEntity.setCriticalNum(codeCheckStatementEntity.getCriticalNum());
		codeDetailsEntity.setInfoNum(codeCheckStatementEntity.getInfoNum());
		codeDetailsEntity.setBugNum(codeCheckStatementEntity.getBugNum());
		codeDetailsEntity.setMajorNum(codeCheckStatementEntity.getMajorNum());
		codeDetailsEntity.setMinorNum(codeCheckStatementEntity.getMinorNum());
		codeDetailsEntity.setMultiplicity(codeCheckStatementEntity.getMultiplicity());
		return codeDetailsEntity;

	}

	@Override
	public List<CodeProblemInfo> queryCodeDetails(String flowId, String problem,
			String uuid) throws PaasWebException {
		List<CodeProblemInfo> CodeProblemInfoList=new ArrayList<CodeProblemInfo>();
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCodeDetailsUrl", flowId,
				problem, uuid);
		logger.debug("开始调用查询代码详情的rest接口：" + url);
		try {
			ResponseInfo responseInfo = codeDetailsDao.get(url,
					CodeProblemPathList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询代码详情的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			CodeProblemPathList codeProblemPathList = (CodeProblemPathList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (codeProblemPathList != null) {
					CodeProblemInfo codeProblemInfo = new CodeProblemInfo();
					for (CodeProblemPathEntity codeProblemPathEntity : codeProblemPathList.getCodeProblemPathList()) {
						codeProblemInfo = genCodeProblemInfo(codeProblemPathEntity);
						CodeProblemInfoList.add(codeProblemInfo);
					}
				}
				logger.info("调用查询代码详情Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询代码详情出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_CODEDETAILS_ERROR
						+ "]查询代码详情出错", ex);
				throw new PaasWebException(Constants.QUERY_CODEDETAILS_ERROR,
						ex.getMessage());
			}
		}
		return CodeProblemInfoList;
	}

	/**
	 * 代码详情实体转换
	 * 
	 * @param CodeProblemPathEntity
	 * 
	 * @return CodeProblemInfo
	 */
	private CodeProblemInfo genCodeProblemInfo(
			CodeProblemPathEntity codeProblemPathEntity) {
		CodeProblemInfo codeProblemInfo = new CodeProblemInfo();
		if (codeProblemPathEntity!=null) {
			codeProblemInfo.setCodepath(codeProblemPathEntity.getCodePath());
		}

		return codeProblemInfo;

	}

	@Override
	public CodeProblemDetailsInfo queryProblemDetails(String flowId,
			String problem, String codePath) throws PaasWebException {
		CodeProblemDetailsInfo codeProblemDetailsInfo = new CodeProblemDetailsInfo();
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryProblemDetailsUrl",
				flowId, problem, codePath);
		logger.debug("开始调用查询问题代码详情的rest接口：" + url);
		try {
			ResponseInfo responseInfo = codeDetailsDao.get(url,
					CodeProblemList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询问题代码详情的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			CodeProblemList codeProblemList = (CodeProblemList) responseInfo
					.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (codeProblemList != null) {
					codeProblemDetailsInfo = genCodeProblemDetailsInfo(codeProblemList);
				}
				logger.info("调用查询问题代码详情Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询问题代码详情出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_PROBLEMDETAILS_ERROR
						+ "]查询问题代码详情出错", ex);
				throw new PaasWebException(
						Constants.QUERY_PROBLEMDETAILS_ERROR, ex.getMessage());
			}
		}
		return codeProblemDetailsInfo;
	}

	/**
	 * 问题代码详情实体转换
	 * 
	 * @param CodeProblemList
	 * 
	 * @return CodeProblemDetailsInfo
	 */
	private CodeProblemDetailsInfo genCodeProblemDetailsInfo(
			CodeProblemList codeProblemList) {
		CodeProblemDetailsInfo codeProblemDetailsInfo = new CodeProblemDetailsInfo();
		if (codeProblemList.getCodeProblem() != null) {
			codeProblemDetailsInfo.setCode(codeProblemList.getCodeProblem().getCode());
			List<CodeProblemInfo> list=new ArrayList<CodeProblemInfo>();
			if (codeProblemList.getCodeProblem().getCodeProblemDetail() != null) {
				for (CodeProblemDetailEntity codeProblemDetailEntity : codeProblemList
						.getCodeProblem().getCodeProblemDetail()) {
					CodeProblemInfo codeProblemInfo = new CodeProblemInfo();
					codeProblemInfo.setDescription(codeProblemDetailEntity.getDescription());
					codeProblemInfo.setLine(codeProblemDetailEntity.getLine());
					codeProblemInfo.setMessage(codeProblemDetailEntity.getMessage());
					codeProblemInfo.setProblemType(codeProblemDetailEntity.getTitleName());
					list.add(codeProblemInfo);
				}
				codeProblemDetailsInfo.setCodeProblemList(list);
			}
		}
		return codeProblemDetailsInfo;

	}

}
