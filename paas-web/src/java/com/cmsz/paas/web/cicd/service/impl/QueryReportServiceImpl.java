package com.cmsz.paas.web.cicd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.SecScaEntity;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.QueryReportDao;
import com.cmsz.paas.web.cicd.service.QueryReportService;
import com.cmsz.paas.web.constants.Constants;
/**
 * 查询报告serviceimpl
 * @author jiayz
 * 
 */
@Service("queryReportService")
public class QueryReportServiceImpl implements QueryReportService{
	private static final Logger logger = LoggerFactory
			.getLogger(QueryReportServiceImpl.class);
	@Autowired
	QueryReportDao dao;
	@Override
	public String getReportHtml(String flowRecordId,String type) {
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryReportUrl",flowRecordId,type);
		logger.debug("开始调用查询报告详情的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, SecScaEntity.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询报告详情的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			SecScaEntity se = (SecScaEntity) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用查询报告详情Rest接口成功！");
				if(se!=null){
					return se.getReport();
				}
				return "";
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询报告详情出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_REPORT_ERROR
						+ "]查询报告详情出错", ex);
				throw new PaasWebException(
						Constants.QUERY_REPORT_ERROR,
						ex.getMessage());
			}
		}
	}

}
