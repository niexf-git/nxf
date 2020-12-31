package com.cmsz.paas.web.cicd.service;


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

/**
 * 报表service
 * @author ccl
 * 
 */
public interface ReportService {
	/**
	 * 查询自动化测试报表.
	 * 
	 * @param 流水ID
	 * @return List<AutoTestReportInfo>
	 */
	public Page<AutoTestReportInfo> queryAutoTestReportList(String flowId,long page,long rows) throws PaasWebException;
	/**
	 * 查询集成测试报表
	 * @author luohan
	 * @param flowId 流水id
	 * @return
	 */
	public Page<InteTestRoportFormEntity> queryInteTestRoport(String flowId, 
			String page, String rows) throws PaasWebException;
	
	/**
	 * 
	 * Description:查询构建报表
	 * @author huangyq
	 * @param
	 * @return List<BuildReportFormEntity>
	 * @date 2017年8月31日
	 */
	public com.cmsz.paas.web.base.dao.page.Page<BuildRecordReportEntity> queryBuildRoportForm(String flowId,String page, String rows) throws PaasWebException;

	/**
	 * 查询代码质量报表.
	 * @param flowId
	 * @param pageNum
	 * @param pageIndex
	 * @return
	 * @throws PaasWebException
	 */
	public Page<CodeQualityEntity> queryCodeQualityList (String flowId,long pageNum,long pageIndex) throws PaasWebException;
	/**
	 * 查询自动化测试报表(外).
	 * 
	 * @param 
	 * @return List<AutoTestReport>
	 */
	public Page<AutoTestReport> queryAutoTestReport(String appId,String operType,long page,long rows) throws PaasWebException;
	
	/**
	 * 查询集成测试报表（外）
	 * @author ccl
	 * @param 
	 * @return
	 */
	public Page<IntegrateTestReport> queryInteTestRoportList(String appId,String operType,long page,long rows) throws PaasWebException;
	
	/**
	 * 查询单元测试报表（外）
	 * @author ccl
	 * @param 
	 * @return
	 */
	public Page<UnitTestReport> queryUnitTestReport(String appId,String operType,long page,long rows) throws PaasWebException;
	
	/**
	 * 查询自动化测试问题折线图
	 * @author ccl
	 * @param 
	 * @return
	 */
	public LineChartEntity queryAutoTestLineChart(String flowId) throws PaasWebException;
	
	/**
	 * 查询集成测试问题折线图
	 * @author ccl
	 * @param 
	 * @return
	 */
	public LineChartEntity queryIntegrateTestLineChart(String flowId) throws PaasWebException;
	
	/**
	 * 查询单元测试问题折线图
	 * @author ccl
	 * @param 
	 * @return
	 */
	public LineChartEntity queryUnitTestLineChart(String flowId) throws PaasWebException;

	/**
	 * 查询单元测试报表.
	 * @param flowId
	 * @param page
	 * @param rows
	 * @return
	 * @throws PaasWebException
	 */
	public Page<UnitTestReportInfo> queryUnitTestReportList(String flowId, String page, String rows) throws PaasWebException;
	
	/**
	 * 查询性能报表.
	 * @param flowId
	 * @param page
	 * @param rows
	 * @return
	 * @throws PaasWebException
	 */
	public Page<PerformanceTestReportInfo> queryPerformanceTestReportList(String flowId, String page, String rows) throws PaasWebException;
	/**
	 * 查询构建记录（外）报表.
	 * @param appId
	 * @param operType
	 * @param page
	 * @param rows
	 * @return
	 * @throws PaasWebException
	 */
	public Page<BuildRecordAbroadReport> queryBuildRecordReport(String appId, String operType, String page,
			String rows)throws PaasWebException;

	/**
	 * 查询代码质量报表（外）.
	 * @param operType 
	 * @param appId 
	 * @param page
	 * @param rows
	 * @return
	 * @throws PaasWebException
	 */
	public Page<CodeQualityReportInfo> queryCodeQualityReport(String appId, String roleType, long page, long rows) throws PaasWebException;
	
	/**
	 * 查询代码质量（构建次数）折线图（外）.
	 * @param page
	 * @param rows
	 * @return
	 * @throws PaasWebException
	 */
	public LineChartEntity queryCodeQualityLineChart(String flowId) throws PaasWebException;
	
	/**
	 * 性能测试报表(外)
	 * @author lin.my
	 * @param appId
	 * @param operType
	 * @param page
	 * @param rows
	 * @return
	 */
	public Page<PerformanceTestReport> queryPerformanceTestReport(String appId,
			String operType, long page, long rows);
	
	/**
	 * 性能测试折线图(外)
	 * @author lin.my
	 * @param flowId
	 * @return
	 * @throws PaasWebException
	 */
	public LineChartEntity queryPerformanceTestLineChart(String flowId) throws PaasWebException;
}
