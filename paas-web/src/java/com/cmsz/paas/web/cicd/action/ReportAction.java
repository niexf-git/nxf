package com.cmsz.paas.web.cicd.action;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.AutoTestReport;
import com.cmsz.paas.web.cicd.model.AutoTestReportInfo;
import com.cmsz.paas.web.cicd.model.BuildRecordAbroadReport;
import com.cmsz.paas.web.cicd.model.BuildRecordReportEntity;
import com.cmsz.paas.web.cicd.model.CodeQualityEntity;
import com.cmsz.paas.web.cicd.model.CodeQualityReportInfo;
import com.cmsz.paas.web.cicd.model.InteTestRoportFormEntity;
import com.cmsz.paas.web.cicd.model.IntegrateTestReport;
import com.cmsz.paas.web.cicd.model.LineChartEntity;
import com.cmsz.paas.web.cicd.model.PerformanceTestReport;
import com.cmsz.paas.web.cicd.model.PerformanceTestReportInfo;
import com.cmsz.paas.web.cicd.model.UnitTestReport;
import com.cmsz.paas.web.cicd.model.UnitTestReportInfo;
import com.cmsz.paas.web.cicd.service.ReportService;

/**
 * 报表Action
 * 
 * @author ccl
 * 
 */
public class ReportAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ReportAction.class);

	@Autowired
	private ReportService reportService;

	private String flowId;// 流水ID

	/**
	 * 查询自动化测试报表
	 * 
	 * @param 流水ID
	 * 
	 * @return List<AutoTestReportInfo>
	 */
	@UnLogging
	public void queryAutoTestReportList() {
		logger.info("开始执行查询自动化测试报表");
		try {
			Page<AutoTestReportInfo> autoTestReportInfoList = reportService
					.queryAutoTestReportList(flowId, Struts2.buildPageContext()
							.getBound().getOffset(), Struts2.buildPageContext()
							.getBound().getLimit());
			logger.info("查询自动化测试报表成功！");
			this.sendSuccessReslult(autoTestReportInfoList);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询自动化测试报表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询集成测试报表
	 * 
	 * @param 流水ID
	 * 
	 * @return List<InteTestRoportFormEntity>
	 */
	@UnLogging
	public void queryIntegrateTestReports() {
		logger.info("开始执行查询集成测试报表");
		try {
			// 获取前端分页参数
			PageContext pContext = Struts2.buildPageContext("");
			// 当前页数
			String page = pContext.getBound().getOffset() + "";
			// 每页条数
			String rows = pContext.getBound().getLimit() + "";
			Page<InteTestRoportFormEntity> flowPage = reportService
					.queryInteTestRoport(flowId, page, rows);
			this.sendSuccessReslult(flowPage);
			logger.info("查询集成测试报表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询集成测试报表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 
	 * Description:构建记录报表查询
	 * 
	 * @author huangyq
	 * @param
	 * @return void
	 * @throws IOException
	 * @date 2017年8月31日
	 */
	@UnLogging
	public void queryBuildRecordList() {
		logger.info("开始执行查询构建记录报表");
		try {
			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");

			String rows = pc.getBound().getLimit() + ""; // 每页条数
			String page = pc.getBound().getOffset() + ""; // 当前页数

			com.cmsz.paas.web.base.dao.page.Page<BuildRecordReportEntity> buildPage = reportService
					.queryBuildRoportForm(flowId, page, rows);
			sendSuccessReslult(buildPage);
			logger.info("查询构建记录报表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询构建记录报表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询代码质量报表
	 * 
	 * @param 流水ID
	 * 
	 * @return List<CodeQualityEntity>
	 */
	@UnLogging
	public void queryCodeQualityList() {
		logger.info("开始执行查询代码质量报表");
		try {

			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			long page = pc.getBound().getOffset(); // 当前页数
			long rows = pc.getBound().getLimit(); // 每页条数

			Page<CodeQualityEntity> codeQualityList = reportService
					.queryCodeQualityList(flowId, page, rows);
			sendSuccessReslult(codeQualityList);
			logger.info("查询代码质量报表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询代码质量报表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询自动化测试报表（外）
	 * 
	 * @param
	 * 
	 * @return List<AutoTestReport>
	 */
	@UnLogging
	public void queryAutoTestReport() {

		// 获取应用Id、操作类型（1-开发、2-测试）、角色类型（0-超级管理员，1-普通用户）
		String appId = (String) getSessionMap().get("appPerSelectedId");
		String operType = (String) getSessionMap().get("selectedOpertype");

		// roleType存入session的时候是int类型，类型转化的时候会出错，所以不能用：
		// String roleType = (String)getSessionMap().get("roleType");
		String roleType = getSessionMap().get("roleType") + "";

		logger.info("开始执行查询自动化测试报表（外），应用Id：" + appId + "，操作类型：" + operType
				+ "，角色类型：" + operType);

		try {

			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			long page = pc.getBound().getOffset(); // 当前页数
			long rows = pc.getBound().getLimit(); // 每页条数

			// 普通用户还没授予应用的时候，不需要去请求控制中心，直接返回
			if ("1".equals(roleType) && "".equals(appId) && "".equals(operType)) {
				Page<AutoTestReport> autoTestReport = new Page<AutoTestReport>();
				autoTestReport.setResult(Collections
						.<AutoTestReport> emptyList());
				autoTestReport.setTotalCount(0);
				sendSuccessReslult(autoTestReport);
			} else {
				Page<AutoTestReport> autoTestReport = reportService
						.queryAutoTestReport(appId, operType, page, rows);
				sendSuccessReslult(autoTestReport);
				logger.info("查询自动化测试报表（外）成功！");
			}
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询自动化测试报表（外）出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 集成测试报表（外）
	 * 
	 * @param
	 * 
	 * @return List<IntegrateTestReport>
	 */
	@UnLogging
	public void queryIntegrateTestReport() {
		// 获取应用Id、操作类型（1-开发、2-测试）、角色类型（0-超级管理员，1-普通用户）
		String appId = (String) getSessionMap().get("appPerSelectedId");
		String operType = (String) getSessionMap().get("selectedOpertype");

		// roleType存入session的时候是int类型，类型转化的时候会出错，所以不能用：
		// String roleType = (String)getSessionMap().get("roleType");
		String roleType = getSessionMap().get("roleType") + "";

		logger.info("开始执行查询集成测试报表（外），应用Id：" + appId + "，操作类型：" + operType
				+ "，角色类型：" + operType);

		try {

			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			long page = pc.getBound().getOffset(); // 当前页数
			long rows = pc.getBound().getLimit(); // 每页条数

			// 普通用户还没授予应用的时候，不需要去请求控制中心，直接返回
			if ("1".equals(roleType) && "".equals(appId) && "".equals(operType)) {
				Page<IntegrateTestReport> integrateTestReport = new Page<IntegrateTestReport>();
				integrateTestReport.setResult(Collections
						.<IntegrateTestReport> emptyList());
				integrateTestReport.setTotalCount(0);
				sendSuccessReslult(integrateTestReport);
			} else {
				Page<IntegrateTestReport> integrateTestReport = reportService
						.queryInteTestRoportList(appId, operType, page, rows);
				sendSuccessReslult(integrateTestReport);
				logger.info("查询集成测试报表（外）成功！");
			}
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询集成测试报表（外）出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 单元测试报表（外）
	 * 
	 * @param
	 * 
	 * @return List<UnitTestReport>
	 */
	@UnLogging
	public void queryUnitTestReport() {

		// 获取应用Id、操作类型（1-开发、2-测试）、角色类型（0-超级管理员，1-普通用户）
		String appId = (String) getSessionMap().get("appPerSelectedId");
		String operType = (String) getSessionMap().get("selectedOpertype");

		// roleType存入session的时候是int类型，类型转化的时候会出错，所以不能用：
		// String roleType = (String)getSessionMap().get("roleType");
		String roleType = getSessionMap().get("roleType") + "";

		logger.info("开始执行查询单元测试报表（外），应用Id：" + appId + "，操作类型：" + operType
				+ "，角色类型：" + operType);

		try {

			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			long page = pc.getBound().getOffset(); // 当前页数
			long rows = pc.getBound().getLimit(); // 每页条数

			// 普通用户还没授予应用的时候，不需要去请求控制中心，直接返回
			if ("1".equals(roleType) && "".equals(appId) && "".equals(operType)) {
				Page<UnitTestReport> unitTestReport = new Page<UnitTestReport>();
				unitTestReport.setResult(Collections
						.<UnitTestReport> emptyList());
				unitTestReport.setTotalCount(0);
				sendSuccessReslult(unitTestReport);
			} else {
				Page<UnitTestReport> unitTestReport = reportService
						.queryUnitTestReport(appId, operType, page, rows);
				sendSuccessReslult(unitTestReport);
				logger.info("查询单元测试报表（外）成功！");
			}
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询单元测试报表（外）出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 自动化测试问题折线图
	 * 
	 * @param
	 * 
	 * @return LineChartEntity
	 */
	@UnLogging
	public void queryAutoTestLineChart() {
		logger.info("开始执行查询自动化测试问题折线图");
		try {
			LineChartEntity lineChartEntity = reportService
					.queryAutoTestLineChart(flowId);
			sendSuccessReslult(lineChartEntity);
			logger.info("查询自动化测试问题折线图成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询自动化测试问题折线图出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 集成测试问题折线图
	 * 
	 * @param
	 * 
	 * @return LineChartEntity
	 */
	@UnLogging
	public void queryIntegrateTestLineChart() {
		logger.info("开始执行查询集成测试问题折线图");
		try {
			LineChartEntity lineChartEntity = reportService
					.queryIntegrateTestLineChart(flowId);
			sendSuccessReslult(lineChartEntity);
			logger.info("查询集成测试问题折线图成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询集成测试问题折线图出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 单元测试问题折线图
	 * 
	 * @param
	 * 
	 * @return LineChartEntity
	 */
	@UnLogging
	public void queryUnitTestLineChart() {
		logger.info("开始执行查询单元测试问题折线图");
		try {
			LineChartEntity lineChartEntity = reportService
					.queryUnitTestLineChart(flowId);
			sendSuccessReslult(lineChartEntity);
			logger.info("查询单元测试问题折线图成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询单元测试问题折线图出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 单元测试报表查询(内)
	 * 
	 * @author ls
	 * @param
	 * @return void
	 * @date 2017年11月23日
	 */
	@UnLogging
	public void queryUnitTestReportList() {
		logger.info("开始执行查询单元测试报表");
		try {
			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			String rows = pc.getBound().getLimit() + ""; // 每页条数
			String page = pc.getBound().getOffset() + ""; // 当前页数

			Page<UnitTestReportInfo> buildPage = reportService.queryUnitTestReportList(flowId, page, rows);
			sendSuccessReslult(buildPage);
			logger.info("查询单元测试报表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询单元测试报表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 性能测试报表查询(内)
	 * 
	 * @author ls
	 * @param
	 * @return void
	 * @date 2017年11月23日
	 */
	@UnLogging
	public void queryPerformanceTestReportList() {
		logger.info("开始执行查询性能测试报表");
		try {
			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");

			String rows = pc.getBound().getLimit() + ""; // 每页条数
			String page = pc.getBound().getOffset() + ""; // 当前页数

			Page<PerformanceTestReportInfo> buildPage = reportService.queryPerformanceTestReportList(flowId, page, rows);
			buildPage.getResult();
			sendSuccessReslult(buildPage);
			logger.info("查询性能测试报表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询性能测试报表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 构建记录（外）报表查询
	 * 
	 * @author jiayz
	 * @param
	 * @return void
	 * @date 2017年11月27日
	 */
	@UnLogging
	public void queryBuildRecordReport() {
		logger.info("开始执行查询构建记录（外）报表");
		try {
			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			String rows = pc.getBound().getLimit() + ""; // 每页条数
			String page = pc.getBound().getOffset() + ""; // 当前页数
			String appId = (String) getSessionMap().get("appPerSelectedId");
			String operType = (String) getSessionMap().get("selectedOpertype");
			Page<BuildRecordAbroadReport> buildPage = reportService
					.queryBuildRecordReport(appId,operType, page, rows);			
			buildPage.getResult();
			sendSuccessReslult(buildPage);
			logger.info("查询构建记录（外）报表成功");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询构建记录（外）报表出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询代码质量报表（外）
	 * 
	 * @param 
	 * 
	 * @return Page<CodeQualityReportInfo>
	 */
	@UnLogging
	public void queryCodeQualityReport(){
		// 获取应用Id、操作类型（1-开发、2-测试）、角色类型（0-超级管理员，1-普通用户）
		String appId = (String) getSessionMap().get("appPerSelectedId");
		String operType = (String) getSessionMap().get("selectedOpertype");

		// roleType存入session的时候是int类型，类型转化的时候会出错，所以不能用：
		// String roleType = (String)getSessionMap().get("roleType");
		String roleType = getSessionMap().get("roleType") + "";

		logger.info("开始执行查询代码质量报表（外），应用Id：" + appId + "，操作类型：" + operType
				+ "，角色类型：" + roleType);
		try {		
			
			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			long page = pc.getBound().getOffset(); //当前页数
			long rows = pc.getBound().getLimit(); //每页条数
						
			Page<CodeQualityReportInfo> codeQualityReportInfo = reportService.queryCodeQualityReport(appId, operType, page, rows);
			sendSuccessReslult(codeQualityReportInfo);
			logger.info("查询代码质量报表（外）成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询代码质量报表（外）出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 代码质量（构建次数）折线图
	 * 
	 * @param 
	 * 
	 * @return LineChartEntity
	 */
	@UnLogging
	public void queryCodeQualityLineChart(){
		logger.info("开始执行查询代码质量（构建次数）折线图");
		try {								
			LineChartEntity lineChartEntity = reportService.queryCodeQualityLineChart(flowId);
//			LineChartEntity lineChartEntity = reportService.queryCodeQualityLineChart("7eba21a0cb3d11e799ab00505694211b");
			sendSuccessReslult(lineChartEntity);
			logger.info("查询代码质量（构建次数）折线图成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询代码质量（构建次数）折线图出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 性能测试报表(外)
	 * @author lin.my
	 */
	@UnLogging
	public void queryPerformanceTestReport() {

		// 获取应用Id、操作类型（1-开发、2-测试）、角色类型（0-超级管理员，1-普通用户）
		String appId = (String) getSessionMap().get("appPerSelectedId");
		String operType = (String) getSessionMap().get("selectedOpertype");

		// roleType存入session的时候是int类型，类型转化的时候会出错，所以不能用：
		// String roleType = (String)getSessionMap().get("roleType");
		String roleType = getSessionMap().get("roleType") + "";

		logger.info("开始执行查询性能测试报表（外），应用Id：" + appId + "，操作类型：" + operType
				+ "，角色类型：" + roleType);

		try {

			// 获取前端页面分页参数
			PageContext pc = Struts2.buildPageContext("");
			long page = pc.getBound().getOffset(); // 当前页数
			long rows = pc.getBound().getLimit(); // 每页条数

			// 普通用户还没授予应用的时候，不需要去请求控制中心，直接返回
			if ("1".equals(roleType) && "".equals(appId) && "".equals(operType)) {
				Page<PerformanceTestReport> performanceTestReport = new Page<PerformanceTestReport>();
				performanceTestReport.setResult(Collections
						.<PerformanceTestReport> emptyList());
				performanceTestReport.setTotalCount(0);
				sendSuccessReslult(performanceTestReport);
			} else {
				Page<PerformanceTestReport> performanceTestReport = reportService
						.queryPerformanceTestReport(appId, operType, page, rows);
				sendSuccessReslult(performanceTestReport);
				logger.info("查询单元测试报表（外）成功！");
			}
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询单元测试报表（外）出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 性能测试折线图(外)
	 */
	@UnLogging
	public void queryPerformanceTestLineChart(){
		logger.info("开始执行性能测试折线图(外)");
		try {								
			LineChartEntity lineChartEntity = reportService.queryPerformanceTestLineChart(flowId);
			sendSuccessReslult(lineChartEntity);
			logger.info("执行性能测试折线图(外)成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]执行性能测试折线图(外)出错", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

}
