package com.cmsz.paas.web.cicd.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.service.QueryReportService;

/**
 * 查看报告Action
 * @author jiayz
 * 
 */
public class QueryReportAction extends AbstractAction{

	private static final Logger logger = LoggerFactory
			.getLogger(QueryReportAction.class);
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private QueryReportService queryReportService;
	
	private String flowRecordId;
	
	public String getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(String flowRecordId) {
		this.flowRecordId = flowRecordId;
	}

	/**
	 * 查询安全扫描报告. 查询条件：flowRecordId
	 */
	public void querySecurityScanReport() {
		logger.info("开始执行查询安全扫描报告详情");
		try {
			Object html = queryReportService.getReportHtml(flowRecordId,"1");
			this.sendSuccessReslult(html);
			logger.info("查询安全扫描报告成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询安全扫描报告出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询自动测试报告. 查询条件：flowRecordId
	 */
	public void queryAutoTestReport() {
		logger.info("开始执行查询自动测试报告详情");
		try {
			Object html = queryReportService.getReportHtml(flowRecordId,"2");
			this.sendSuccessReslult(html);
			logger.info("查询自动测试报告成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询自动测试报告出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询集成测试报告. 查询条件：flowRecordId
	 */
	public void queryIntegrateTestReport() {
		logger.info("开始执行查询集成测试报告详情");
		try {
			Object html = queryReportService.getReportHtml(flowRecordId,"3");
			this.sendSuccessReslult(html);
			logger.info("查询集成测试报告成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询集成测试报告出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询单元测试报告. 查询条件：flowRecordId
	 */
	public void queryUnitTestReport() {
		logger.info("开始执行查询单元测试报告详情");
		try {
			Object html = queryReportService.getReportHtml(flowRecordId,"4");
			this.sendSuccessReslult(html);
			logger.info("查询单元测试报告成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询单元测试报告出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	/**
	 * 查询性能测试报告. 查询条件：flowRecordId
	 */
	public void queryPerformanceTestReport() {
		logger.info("开始执行查询性能测试报告详情");
		try {
			Object html = queryReportService.getReportHtml(flowRecordId,"5");
			this.sendSuccessReslult(html);
			logger.info("查询性能测试报告成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询性能测试报告出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
}
