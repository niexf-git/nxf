package com.cmsz.paas.web.cicd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.AutoTestReportEntity;
import com.cmsz.paas.common.model.controller.entity.BulidReportEntity;
import com.cmsz.paas.common.model.controller.entity.BulidStatementEntity;
import com.cmsz.paas.common.model.controller.entity.CodeCheckEntity;
import com.cmsz.paas.common.model.controller.entity.CodeCheckStatementEntity;
import com.cmsz.paas.common.model.controller.entity.InteTestReportEntity;
import com.cmsz.paas.common.model.controller.entity.PerformanceTestStatementEntity;
import com.cmsz.paas.common.model.controller.entity.ReportChart;
import com.cmsz.paas.common.model.controller.entity.TestStatementEntity;
import com.cmsz.paas.common.model.controller.entity.UnitTestStatementEntity;
import com.cmsz.paas.common.model.controller.response.ReportChartList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.ReportDao;
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
import com.cmsz.paas.web.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
/**
 * 报表service实现
 * 
 * @author ccl
 * 
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {
	private static final Logger logger = LoggerFactory
			.getLogger(ReportServiceImpl.class);
	@Autowired
	private ReportDao reportDao;
	@Override
	public Page<AutoTestReportInfo> queryAutoTestReportList(String flowId,long page,long rows)
			throws PaasWebException {
		Page<AutoTestReportInfo> reportPage=new Page<AutoTestReportInfo>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryAutoTestReportListUrl", flowId,page,rows);
		logger.debug("开始调用查询自动化测试报表列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询自动化测试报表列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<AutoTestReportEntity> autoTestReportPage = (com.cmsz.paas.common.page.Page<AutoTestReportEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<AutoTestReportEntity> autoTestReportList=autoTestReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(autoTestReportList);
				String jsonStr = array.toString();
				autoTestReportList = fromJsonList(jsonStr, AutoTestReportEntity.class);
				List<AutoTestReportInfo> list = new ArrayList<AutoTestReportInfo>();
				if (autoTestReportList != null) {
					for (AutoTestReportEntity autoTestReportEntity : autoTestReportList) {
						list.add(genAutoTestReportInfo(autoTestReportEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(autoTestReportPage.getTotalCount());
				logger.info("调用查询自动化测试报表列表Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询自动化测试报表列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_AUTOTESTREPORTLIST_ERROR
						+ "]查询自动化测试报表列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_AUTOTESTREPORTLIST_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 自动化测试报表实体转换
	 * 
	 * @param AutoTestReport
	 * 
	 * @return AutoTestReportInfo
	 */
	private AutoTestReportInfo genAutoTestReportInfo(AutoTestReportEntity autoTestReportEntity){
		AutoTestReportInfo AutoTestReportInfo=new AutoTestReportInfo();
		AutoTestReportInfo.setExecutionRecord(autoTestReportEntity.getTime());
		AutoTestReportInfo.setFailNumber(autoTestReportEntity.getFailNumber());
		AutoTestReportInfo.setFailRate(autoTestReportEntity.getFailReat());
		AutoTestReportInfo.setSuccessNumber(autoTestReportEntity.getSuccessNumber());
		AutoTestReportInfo.setSuccessRate(autoTestReportEntity.getSuccessReat());
		AutoTestReportInfo.setTotals(autoTestReportEntity.getTotals());
		AutoTestReportInfo.setPerson(autoTestReportEntity.getCoder());
		return AutoTestReportInfo;
		
	}
	
	@Override
	public Page<InteTestRoportFormEntity> queryInteTestRoport(String flowId,
			String page, String rows)
			throws PaasWebException {
		// 接收控制中心返回转换数据
		List<InteTestRoportFormEntity> flowList = new ArrayList<InteTestRoportFormEntity>();
		Page<InteTestRoportFormEntity> webPage = new Page<InteTestRoportFormEntity>();
		//rest接口返回码
		String retCode = "";
		//rest接口返回信息
		String msg = "";
		//拼接接口地址
		String url = RestUtils.
				restUrlTransform("queryInteTestRoportUrl", flowId,page,rows);
		logger.debug("开始调用查询集成测试报表的Rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用查询集成测试报表的Rest接口返回码：" + retCode + "，返回信息：" + msg);
			com.cmsz.paas.common.page.Page<InteTestReportEntity> controlPage = (com.cmsz.paas.common.page.Page<InteTestReportEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
			List<InteTestReportEntity> list = controlPage.getResult();
			//list转json
			JSONArray array = new JSONArray(list);
			String jsonStr = array.toString();
			List<InteTestReportEntity> fList = fromJsonList(jsonStr, InteTestReportEntity.class);
			
				if (fList != null && fList.size() > 0) {
					for (InteTestReportEntity entity : fList) {
						flowList.add(translateToInteTestRoportFormEntity(entity));
					}
				}
				webPage.setResult(flowList);
				webPage.setTotalCount(controlPage.getTotalCount());
				logger.info("调用集成测试报表查询Rest接口成功！");
				return webPage;
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if(ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询集成测试报表出错", ex);
				throw new PaasWebException(retCode, msg);
			}else {
				logger.error("[" + Constants.QUERY_INTE_TEST_REPORT_ERROR + "]查询代码详情出错", ex);
				throw new PaasWebException(Constants.QUERY_INTE_TEST_REPORT_ERROR, ex.getMessage());
			}
		}
	}
	
	@Override
	public com.cmsz.paas.web.base.dao.page.Page<BuildRecordReportEntity> queryBuildRoportForm(String flowId,String page, String rows) throws PaasWebException {
		
		// 接收控制中心返回转换数据
		List<BuildRecordReportEntity> buildList = new ArrayList<BuildRecordReportEntity>();
		com.cmsz.paas.web.base.dao.page.Page<BuildRecordReportEntity> webPage = new com.cmsz.paas.web.base.dao.page.Page<BuildRecordReportEntity>();	
		String msg = "";//  rest接口返回信息
		String retCode = "";//rest接口返回码
		String url = RestUtils.restUrlTransform("queryBuildReportFormUrl", flowId,rows,page);
		logger.debug("开始调用查询代构建记录报表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用构建记录报表查询的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			@SuppressWarnings("unchecked")
			Page<com.cmsz.paas.common.model.controller.entity.BulidReportEntity> controlPage = (Page<com.cmsz.paas.common.model.controller.entity.BulidReportEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
			List<com.cmsz.paas.common.model.controller.entity.BulidReportEntity> list = controlPage.getResult();
			// list转json
			org.json.JSONArray array = new org.json.JSONArray(list);
			String jsonStr = array.toString();
			List<com.cmsz.paas.common.model.controller.entity.BulidReportEntity> fList = fromJsonList(jsonStr,com.cmsz.paas.common.model.controller.entity.BulidReportEntity.class);
			
				if (fList != null&&fList.size()>0) {
					for (com.cmsz.paas.common.model.controller.entity.BulidReportEntity entity : fList) {
						buildList.add(genBuildReportFormEntity(entity));
					}
				}
				webPage.setResult(buildList);
				webPage.setTotalCount(controlPage.getTotalCount());
				logger.info("调用构建记录报表查询rest接口返回成功！");
				return webPage;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询构建记录报表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_BUILD_REPORT_ERROR + "]查询代码详情出错", ex);
				throw new PaasWebException(Constants.QUERY_BUILD_REPORT_ERROR, ex.getMessage());
			}
		}
	}
	private <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {  
        ArrayList<T> mList = new ArrayList<T>();  
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();  
//        Gson mGson = new Gson();
        Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        for(final JsonElement elem : array){  
            mList.add(mGson.fromJson(elem, cls));  
        }  
        return mList;  
    } 
	
	/*
	 * 把控制中心的bean转换成前台InteTestRoportFormEntity对象
	 */
	private InteTestRoportFormEntity translateToInteTestRoportFormEntity(InteTestReportEntity inteEntity){
		InteTestRoportFormEntity eFormEntity = new InteTestRoportFormEntity();
		if (inteEntity != null) {
			eFormEntity.setExecutionRecord(inteEntity.getTime());   //执行记录
			eFormEntity.setTotals(inteEntity.getTotals());   //用例总数
			eFormEntity.setSuccessNumber(inteEntity.getSuccessNumber());  //成功数
			eFormEntity.setSuccessRate(inteEntity.getSuccessReat());  //成功率
			eFormEntity.setFailNumber(inteEntity.getFailNumber());   //失败数
			eFormEntity.setFailRate(inteEntity.getFailReat());  //失败率
			eFormEntity.setPerson(inteEntity.getCoder());
		}
		return eFormEntity;
		
	}
	
	/**
	 * 
	 * Description:构建记录报表实体转换
	 * 
	 * @author huangyq
	 * @param
	 * @return BuildReportFormEntity
	 * @date 2017年8月31日
	 */
	private BuildRecordReportEntity genBuildReportFormEntity(BulidReportEntity bulidReportEntity) {
		BuildRecordReportEntity entity = new BuildRecordReportEntity();
		entity.setDownloadTime(bulidReportEntity.getDownloadTime());// 下载审查时间
		entity.setDownloadMessage(changeResultFormat(bulidReportEntity.getDownloadResult()));// 下载审查结果
		entity.setBuildTime(bulidReportEntity.getStringbuildTime());// 编译构建时间
		entity.setBuildMessage(changeResultFormat(bulidReportEntity.getBuildResult()));// 编译构建结果
		entity.setDeployTime(bulidReportEntity.getScanTime());// 部署扫描时间
		entity.setDeployMessage(changeResultFormat(bulidReportEntity.getScanResult()));// 部署扫描结果
		entity.setAutoTime(bulidReportEntity.getAutomaticTime());// 自动测试时间
		entity.setAutoMessage(changeResultFormat(bulidReportEntity.getAutomaticResult()));// 自动测试结果
		entity.setIntegrateTime(bulidReportEntity.getIntegrateTime());// 集成测试时间
		entity.setIntegrateMessage(changeResultFormat(bulidReportEntity.getIntegrateResult()));// 集成测试结果
		entity.setReleaseTime(bulidReportEntity.getReleaseTime());// 发布测试时间
		entity.setReleaseMessage(changeResultFormat(bulidReportEntity.getReleaseResult()));// 发布测试结果
		entity.setExecutionRecord(bulidReportEntity.getTime());//执行记录
		entity.setPerformanceTime(bulidReportEntity.getPerformanceTime());
		entity.setPerformanceMessage(changeResultFormat((int)bulidReportEntity.getPerformanceMessage()));
		
		return entity;
	}
	
	/**
	 * 
	 * Description:
	 * @author huangyq
	 * @param
	 * @return String
	 * @date 2017年9月9日
	 */
	private String changeResultFormat(Integer status){
		String result = "";
		switch (status) {
		case 0:
			result = "未执行";
			break;
		case 1:
			result = "成功";
			break;
		case 2:
			result = "失败";
			break;
		case 3:
			result = "运行中";
			break;
		case 4:
			result = "等待中";
			break;
		case 5:
			result = "删除";
			break;
		case -1:
			result = "未勾选";
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public Page<CodeQualityEntity> queryCodeQualityList(String flowId, long pageNum, long pageIndex)
			throws PaasWebException {
		List<CodeQualityEntity> list = new ArrayList<>();
		Page<CodeQualityEntity> webPage = new Page<CodeQualityEntity>();
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryCodeCheck", flowId, pageNum, pageIndex);
		logger.debug("开始调用查询代码质量报表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询代码质量报表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
//			CodeCheckList codeCheckList = (CodeCheckList) responseInfo.getData();
			Page<CodeCheckEntity> controlPage = (Page<CodeCheckEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
			List<CodeCheckEntity> codeCheckList = controlPage.getResult();
			org.json.JSONArray array = new org.json.JSONArray(codeCheckList);
			String jsonStr = array.toString();
			List<CodeCheckEntity> fList = fromJsonList(jsonStr,CodeCheckEntity.class);
			
				if (fList != null) {
					for (CodeCheckEntity entity : fList) {
						list.add(genCodeQulityEntity(entity));
					}
				}
				webPage.setResult(list);
				webPage.setTotalCount(controlPage.getTotalCount());
				logger.info("调用代码质量报表查询Rest接口成功！");
				return webPage;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			if (e instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询代码质量报表出错", e);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_CODE_QUALITY_REPORT_ERROR + "]查询代码质量出错", e);
				throw new PaasWebException(Constants.QUERY_CODE_QUALITY_REPORT_ERROR, e.getMessage());
			}
		}
	}
	
	public CodeQualityEntity genCodeQulityEntity(CodeCheckEntity entity){
		CodeQualityEntity codeQualityEntity = new CodeQualityEntity();
		
		codeQualityEntity.setCause(entity.getBlockerNum()+"");//阻断 对应 致使
		codeQualityEntity.setSerious(entity.getCriticalNum()+"");//严重 对应 严重
		codeQualityEntity.setCommonly(entity.getMajorNum()+"");//一般 对应 重要
		codeQualityEntity.setSlight(entity.getMinorNum()+"");//轻微  对应 轻微
		codeQualityEntity.setLoopComplexity(entity.getComplexity()+"");//圈复杂度
		codeQualityEntity.setRepeat(entity.getMultiplicity()+"");//重复度
		codeQualityEntity.setSecurityVulnerabilities(entity.getBugNum()+"");//安全漏洞
		codeQualityEntity.setExecutionRecord(entity.getCreateTime());//构建记录
		codeQualityEntity.setPerson(entity.getCoder());
		return codeQualityEntity;
	}
	
	@Override
	public Page<AutoTestReport> queryAutoTestReport(String appId,String operType,long page,long rows)
			throws PaasWebException {
		Page<AutoTestReport> reportPage=new Page<AutoTestReport>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryAutoTestReportUrl",appId,operType,page,rows);
		logger.debug("开始调用查询自动化测试报表列表(外)的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询自动化测试报表列表(外)的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<TestStatementEntity> autoTestReportPage = (com.cmsz.paas.common.page.Page<TestStatementEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<TestStatementEntity> autoTestReportList=autoTestReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(autoTestReportList);
				String jsonStr = array.toString();
				autoTestReportList = fromJsonList(jsonStr, TestStatementEntity.class);
				List<AutoTestReport> list = new ArrayList<AutoTestReport>();
				if (autoTestReportList != null) {
					for (TestStatementEntity testStatementEntity : autoTestReportList) {
						list.add(genAutoTestReport(testStatementEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(autoTestReportPage.getTotalCount());
				logger.info("调用查询自动化测试报表列表(外)Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询自动化测试报表列表(外)出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_AUTO_TEST_REPORT_ERROR
						+ "]查询自动化测试报表列表(外)出错", ex);
				throw new PaasWebException(
						Constants.QUERY_AUTO_TEST_REPORT_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 自动化测试报表(外)实体转换
	 * 
	 * @param AutoTestReport
	 * 
	 * @return AutoTestReport
	 */
	private AutoTestReport genAutoTestReport(TestStatementEntity testStatementEntity){
		AutoTestReport AutoTestReport=new AutoTestReport();
		AutoTestReport.setBuildNum(testStatementEntity.getBuildNum());
		AutoTestReport.setFlowId(testStatementEntity.getFlowId());
		AutoTestReport.setFlowName(testStatementEntity.getFlowName());
		AutoTestReport.setProblemNum(testStatementEntity.getErrorCount());
		AutoTestReport.setUseCaseNum(testStatementEntity.getTotals().toString());
		
		return AutoTestReport;
		
	}
	
	@Override
	public Page<IntegrateTestReport> queryInteTestRoportList(String appId,String operType,long page,long rows)
			throws PaasWebException {
		Page<IntegrateTestReport> reportPage=new Page<IntegrateTestReport>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryIntegrateTestReport",appId,operType,page,rows);
		logger.debug("开始调用查询集成测试报表列表(外)的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询集成测试报表列表(外)的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<TestStatementEntity> inteTestReportPage = (com.cmsz.paas.common.page.Page<TestStatementEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<TestStatementEntity> inteTestReportList=inteTestReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(inteTestReportList);
				String jsonStr = array.toString();
				inteTestReportList = fromJsonList(jsonStr, TestStatementEntity.class);
				List<IntegrateTestReport> list = new ArrayList<IntegrateTestReport>();
				if (inteTestReportList != null) {
					for (TestStatementEntity testStatementEntity : inteTestReportList) {
						list.add(genIntegrateTestReport(testStatementEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(inteTestReportPage.getTotalCount());
				logger.info("调用查询集成测试报表列表(外)Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询集成测试报表列表(外)出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_INTE_TEST_REPORT_LIST_ERROR
						+ "]查询集成测试报表列表(外)出错", ex);
				throw new PaasWebException(
						Constants.QUERY_INTE_TEST_REPORT_LIST_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 集成测试报表(外)实体转换
	 * 
	 * @param IntegrateTestReport
	 * 
	 * @return IntegrateTestReport
	 */
	private IntegrateTestReport genIntegrateTestReport(TestStatementEntity testStatementEntity){
		IntegrateTestReport integrateTestReport=new IntegrateTestReport();
		integrateTestReport.setBuildNum(testStatementEntity.getBuildNum());
		integrateTestReport.setFlowId(testStatementEntity.getFlowId());
		integrateTestReport.setFlowName(testStatementEntity.getFlowName());
		integrateTestReport.setProblemNum(testStatementEntity.getErrorCount());
		integrateTestReport.setUseCaseNum(testStatementEntity.getTotals().toString());
		
		return integrateTestReport;
		
	}
	
	@Override
	public Page<UnitTestReport> queryUnitTestReport(String appId,String operType,long page,long rows)
			throws PaasWebException {
		Page<UnitTestReport> reportPage=new Page<UnitTestReport>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryUnitTestReportUrl",appId,operType,page,rows);
		logger.debug("开始调用查询单元测试报表列表(外)的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询单元测试报表列表(外)的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<UnitTestStatementEntity> unitTestReportPage = (com.cmsz.paas.common.page.Page<UnitTestStatementEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<UnitTestStatementEntity> unitTestReportList=unitTestReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(unitTestReportList);
				String jsonStr = array.toString();
				unitTestReportList = fromJsonList(jsonStr, UnitTestStatementEntity.class);
				List<UnitTestReport> list = new ArrayList<UnitTestReport>();
				if (unitTestReportList != null) {
					for (UnitTestStatementEntity unitTestStatementEntity : unitTestReportList) {
						list.add(genUnitTestReport(unitTestStatementEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(unitTestReportPage.getTotalCount());
				logger.info("调用查询单元测试报表列表(外)Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询单元测试报表列表(外)出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_UNIT_TEST_REPORT_ERROR
						+ "]查询单元测试报表列表(外)出错", ex);
				throw new PaasWebException(
						Constants.QUERY_UNIT_TEST_REPORT_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 单元测试报表(外)实体转换
	 * 
	 * @param UnitTestReport
	 * 
	 * @return UnitTestReport
	 */
	private UnitTestReport genUnitTestReport(UnitTestStatementEntity unitTestStatementEntity){
		UnitTestReport unitTestReport=new UnitTestReport();
		unitTestReport.setBuildNum(unitTestStatementEntity.getBuildNum());
		unitTestReport.setFlowId(unitTestStatementEntity.getFlowId());
		unitTestReport.setFlowName(unitTestStatementEntity.getFlowName());
		unitTestReport.setFailNum(unitTestStatementEntity.getFailNumber());
		unitTestReport.setUseCaseNum(unitTestStatementEntity.getTotals().toString());
		
		return unitTestReport;
		
	}
	
	@Override
	public LineChartEntity queryAutoTestLineChart(String flowId)
			throws PaasWebException {
		LineChartEntity lineChartEntity=new LineChartEntity();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryAutoTestLineChartUrl",flowId);
		logger.debug("开始调用查询自动化测试问题折线图的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, ReportChartList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询自动化测试问题折线图的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			ReportChartList reportChartList = (ReportChartList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<ReportChart> reportCharts=reportChartList.getReportChart();
				if (reportCharts != null) {					
					lineChartEntity=genReportReport(reportCharts);
				}
				logger.info("调用查询自动化测试问题折线图Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询自动化测试问题折线图出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_AUTO_LINE_CHART_ERROR
						+ "]查询自动化测试问题折线图出错", ex);
				throw new PaasWebException(
						Constants.QUERY_AUTO_LINE_CHART_ERROR,
						ex.getMessage());
			}
		}
		return lineChartEntity;
	}
	
	@Override
	public LineChartEntity queryIntegrateTestLineChart(String flowId)
			throws PaasWebException {
		LineChartEntity lineChartEntity=new LineChartEntity();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryIntegrateTestLineChartUrl",flowId);
		logger.debug("开始调用查询集成测试问题折线图的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, ReportChartList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询集成测试问题折线图的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			ReportChartList reportChartList = (ReportChartList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<ReportChart> reportCharts=reportChartList.getReportChart();
				if (reportCharts != null) {					
					lineChartEntity=genReportReport(reportCharts);
				}
				logger.info("调用查询集成测试问题折线图Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询集成测试问题折线图出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_INTE_LINE_CHART_ERROR
						+ "]查询集成测试问题折线图出错", ex);
				throw new PaasWebException(
						Constants.QUERY_INTE_LINE_CHART_ERROR,
						ex.getMessage());
			}
		}
		return lineChartEntity;
	}
	
	@Override
	public LineChartEntity queryUnitTestLineChart(String flowId)
			throws PaasWebException {
		LineChartEntity lineChartEntity=new LineChartEntity();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryUnitTestLineChartUrl",flowId);
		logger.debug("开始调用查询单元测试问题折线图的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, ReportChartList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询单元测试问题折线图的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			ReportChartList reportChartList = (ReportChartList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<ReportChart> reportCharts=reportChartList.getReportChart();
				if (reportCharts != null) {					
					lineChartEntity=genReportReport(reportCharts);
				}
				logger.info("调用查询单元测试问题折线图Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询单元测试问题折线图出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_UNIT_LINE_CHART_ERROR
						+ "]查询单元测试问题折线图出错", ex);
				throw new PaasWebException(
						Constants.QUERY_UNIT_LINE_CHART_ERROR,
						ex.getMessage());
			}
		}
		return lineChartEntity;
	}
	
	/**
	 * 折线图数据实体转换
	 * 
	 * @param LineChartEntity
	 * 
	 * @return LineChartEntity
	 */
	private LineChartEntity genReportReport(List<ReportChart> reportChartList){
		LineChartEntity lineChartEntity=new LineChartEntity();
		List<Integer> xList=new ArrayList<Integer>();
		List<Integer> yList=new ArrayList<Integer>();
        for (int i = 0; i < reportChartList.size(); i++) {
        	xList.add(i+1);
        	yList.add(reportChartList.get(i).getError());
        	lineChartEntity.setFlowName(reportChartList.get(i).getFlowName());        	
		}
        lineChartEntity.setxList(xList);
        lineChartEntity.setyList(yList);
		return lineChartEntity;
		
	}
	
	@Override
	public Page<UnitTestReportInfo> queryUnitTestReportList(String flowId, String page, String rows)
			throws PaasWebException {
//		List<UnitTestReportInfo> unitTestReportList = new ArrayList<>();
		Page<UnitTestReportInfo> reportPage=new Page<UnitTestReportInfo>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryUnitTestReportListUrl", flowId, page, rows);
		logger.debug("开始调用查询单元测试报表列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询单元测试报表列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<UnitTestStatementEntity> unitTestReportPage = (com.cmsz.paas.common.page.Page<UnitTestStatementEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<UnitTestStatementEntity> unitTestReportList=unitTestReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(unitTestReportList);
				String jsonStr = array.toString();
				unitTestReportList = fromJsonList(jsonStr, UnitTestStatementEntity.class);
				List<UnitTestReportInfo> list = new ArrayList<UnitTestReportInfo>();
				if (unitTestReportList != null) {
					for (UnitTestStatementEntity unitTestStatementEntity : unitTestReportList) {
						list.add(genUnitTestReportInfo(unitTestStatementEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(unitTestReportPage.getTotalCount());
				logger.info("调用查询单元测试报表列表Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询单元测试报表列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_UNIT_TEST_REPORT_LIST_ERROR
						+ "]查询单元测试报表列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_UNIT_TEST_REPORT_LIST_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 单元测试报表实体转换
	 * 
	 * @param UnitTestStatementEntity
	 * 
	 * @return UnitTestReportInfo
	 */
	private UnitTestReportInfo genUnitTestReportInfo(UnitTestStatementEntity unitTestStatementEntity){
		UnitTestReportInfo unitTestReportInfo=new UnitTestReportInfo();//
		unitTestReportInfo.setExecuteRecord(unitTestStatementEntity.getTime());//执行记录
		unitTestReportInfo.setUseCaseNums(unitTestStatementEntity.getTotals());//用例数
		unitTestReportInfo.setSuccessNums(unitTestStatementEntity.getSuccessNumber());//成功次数
		unitTestReportInfo.setFailNums(unitTestStatementEntity.getFailNumber());//失败次数
		unitTestReportInfo.setErrorNums(unitTestStatementEntity.getErrorCount());//错误次数
		unitTestReportInfo.setInstructCoverage(unitTestStatementEntity.getCommandCoverage());//指令覆盖率
		unitTestReportInfo.setBranchCoverage(unitTestStatementEntity.getBranchConverage());//分支覆盖率
		unitTestReportInfo.setComplexityCoverage(unitTestStatementEntity.getComplexity());//圈复杂度覆盖率
		String lineCoverage = unitTestStatementEntity.getLineCoverage();
		if(lineCoverage == null ||  lineCoverage == "" ){
			unitTestReportInfo.setLineCoverage("0");//行覆盖率
		}else {
			unitTestReportInfo.setLineCoverage(lineCoverage);//行覆盖率
		}
		unitTestReportInfo.setMethodCoverage(unitTestStatementEntity.getMethodCoverage());//方法覆盖率
		unitTestReportInfo.setClassCoverage(unitTestStatementEntity.getClassCoverage());//类覆盖率

		return unitTestReportInfo;
	}
	
	@Override
	public Page<PerformanceTestReportInfo> queryPerformanceTestReportList(String flowId, String page, String rows)
			throws PaasWebException {
		Page<PerformanceTestReportInfo> reportPage=new Page<PerformanceTestReportInfo>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryPerformanceTestReportListUrl", flowId, page, rows);
		logger.debug("开始调用查询性能测试报表列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询性能测试报表列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<PerformanceTestStatementEntity> performanceTestReportPage = (com.cmsz.paas.common.page.Page<PerformanceTestStatementEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<PerformanceTestStatementEntity> performanceTestReportList=performanceTestReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(performanceTestReportList);
				String jsonStr = array.toString();
				performanceTestReportList = fromJsonList(jsonStr, PerformanceTestStatementEntity.class);
				List<PerformanceTestReportInfo> list = new ArrayList<PerformanceTestReportInfo>();
				if (performanceTestReportList != null) {
					for (PerformanceTestStatementEntity performanceTestStatementEntity : performanceTestReportList) {
						list.add(genPerformanceTestReportInfo(performanceTestStatementEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(performanceTestReportPage.getTotalCount());
				logger.info("调用查询性能测试报表列表Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询性能测试报表列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_PERFORMANCE_TEST_REPORT_LIST_ERROR
						+ "]查询单元测试报表列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_PERFORMANCE_TEST_REPORT_LIST_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 性能测试报表实体转换
	 * 
	 * @param PerformanceTestStatementEntity
	 * 
	 * @return PerformanceTestReportInfo
	 */
	private PerformanceTestReportInfo genPerformanceTestReportInfo(PerformanceTestStatementEntity performanceTestStatementEntity){
		PerformanceTestReportInfo performanceTestReportInfo = new PerformanceTestReportInfo();
		performanceTestReportInfo.setExecuteRecord(performanceTestStatementEntity.getTime());//执行记录
		performanceTestReportInfo.setTotalNums(performanceTestStatementEntity.getTotals());//总次数
		performanceTestReportInfo.setFailNums(performanceTestStatementEntity.getFailNumber());//失败次数
		performanceTestReportInfo.setShortestResponseTime(performanceTestStatementEntity.getShortestResponse());//最短响应时间
		performanceTestReportInfo.setAverageResponseTime(performanceTestStatementEntity.getAverageResponse());//平均响应时间
		performanceTestReportInfo.setLongestResponseTime(performanceTestStatementEntity.getLongestResponse());//最长响应时间
		
		return performanceTestReportInfo;
	}
	
	//构建记录（外）报表
	@Override
	public Page<BuildRecordAbroadReport> queryBuildRecordReport(String appId,String operType, String page, String rows)
			throws PaasWebException {
		List<BuildRecordAbroadReport> buildList = new ArrayList<BuildRecordAbroadReport>();
		Page<BuildRecordAbroadReport> webPage = new Page<BuildRecordAbroadReport>();	
		String msg = "";//  rest接口返回信息
		String retCode = "";//rest接口返回码
		String url = RestUtils.restUrlTransform("queryBuildReportAbroadFormUrl", appId,operType,page,rows);
		logger.debug("开始调用查询构建记录(外)报表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			@SuppressWarnings("unchecked")
			Page<BulidStatementEntity> controlPage = (Page<BulidStatementEntity>) responseInfo.getData();
			logger.info("调用构建记录(外)报表查询的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
			List<BulidStatementEntity> list = controlPage.getResult();
			// list转json
			org.json.JSONArray array = new org.json.JSONArray(list);
			String jsonStr = array.toString();
			List<BulidStatementEntity> fList = fromJsonList(jsonStr,BulidStatementEntity.class);
			
				if (fList != null&&fList.size()>0) {
					for (BulidStatementEntity entity : fList) {
						buildList.add(genBuildReportAbroadFormEntity(entity));
					}
				}
				webPage.setResult(buildList);
				webPage.setTotalCount(controlPage.getTotalCount());
				logger.info("调用构建记录(外)报表查询rest接口返回成功！");
				return webPage;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询构建记录(外)报表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_BUILD_REPORT_ABROAD_ERROR + "]查询构建记录(外)报表出错", ex);
				throw new PaasWebException(Constants.QUERY_BUILD_REPORT_ABROAD_ERROR, ex.getMessage());
			}
		}
	}

	private BuildRecordAbroadReport genBuildReportAbroadFormEntity(BulidStatementEntity entity) {
		BuildRecordAbroadReport bra = new BuildRecordAbroadReport();
		bra.setFlowName(entity.getFlowName());
		bra.setAutoTest(entity.getAutotest());
		bra.setBuildNum(entity.getBuildNum()+"");
		bra.setCompileBuild(entity.getBuild());
		bra.setDeployment(entity.getDepscan());
		bra.setDownloadCheck(entity.getDownloadCheck());
		bra.setIntegrateTest(entity.getIntetest());
		bra.setPerformanceTest(entity.getPerformance());
		bra.setReleaseTest(entity.getRelease());
		return bra;
	}
	
	/**
	 * 查询代码质量报表（外）
	 * @param page
	 * @param rows
	 * 
	 * @return Page<CodeQualityReportInfo>
	 */
	@Override
	public Page<CodeQualityReportInfo> queryCodeQualityReport(String appId, String roleType, long page, long rows) throws PaasWebException {
		Page<CodeQualityReportInfo> reportPage=new Page<CodeQualityReportInfo>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryCodeQualityReportUrl",appId,roleType,page,rows);
		logger.debug("开始调用查询代码质量报表列表(外)的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询代码质量报表列表(外)的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<CodeCheckStatementEntity> codeQualityReportPage = (com.cmsz.paas.common.page.Page<CodeCheckStatementEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<CodeCheckStatementEntity> codeQualityReportList=codeQualityReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(codeQualityReportList);
				String jsonStr = array.toString();
				codeQualityReportList = fromJsonList(jsonStr, CodeCheckStatementEntity.class);
				List<CodeQualityReportInfo> list = new ArrayList<CodeQualityReportInfo>();
				if (codeQualityReportList != null) {
					for (CodeCheckStatementEntity codeCheckStatementEntity : codeQualityReportList) {
						list.add(genCodeQualityReportInfo(codeCheckStatementEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(codeQualityReportPage.getTotalCount());
				logger.info("调用查询代码质量报表列表(外)Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询代码质量报表列表(外)出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_CODE_QUALITY_REPORT_LIST_ERROR
						+ "]查询自动化测试报表列表(外)出错", ex);
				throw new PaasWebException(
						Constants.QUERY_CODE_QUALITY_REPORT_LIST_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 代码质量报表实体转换（外）
	 * 
	 * @param CodeCheckStatementEntity
	 * 
	 * @return CodeQualityReportInfo
	 */
	private CodeQualityReportInfo genCodeQualityReportInfo(CodeCheckStatementEntity codeCheckStatementEntity){
		CodeQualityReportInfo codeQualityReportInfo = new CodeQualityReportInfo();
		codeQualityReportInfo.setFlowId(codeCheckStatementEntity.getFlowId());
		codeQualityReportInfo.setFlowName(codeCheckStatementEntity.getFlowName());//流水名称
		codeQualityReportInfo.setBuildNum(codeCheckStatementEntity.getBuildNum());//构建次数
		codeQualityReportInfo.setProblemNum(codeCheckStatementEntity.getErrorCount());
		
		return codeQualityReportInfo;
	}

	/**
	 * 代码质量（构建次数）折线图
	 */
	@Override
	public LineChartEntity queryCodeQualityLineChart(String flowId) throws PaasWebException {
		LineChartEntity lineChartEntity=new LineChartEntity();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryCodeQualityGraphUrl",flowId);
		logger.debug("开始调用查询代码质量（构建次数）折线图的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, ReportChartList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询代码质量（构建次数）折线图的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			ReportChartList reportChartList = (ReportChartList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<ReportChart> reportCharts=reportChartList.getReportChart();
				if (reportCharts != null) {					
					lineChartEntity=genReportReport(reportCharts);
				}
				logger.info("调用查询代码质量（构建次数）折线图Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询代码质量（构建次数）折线图出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_CODE_QUALITY_LINE_CHART_ERROR
						+ "]查询代码质量（构建次数）折线图出错", ex);
				throw new PaasWebException(
						Constants.QUERY_CODE_QUALITY_LINE_CHART_ERROR,
						ex.getMessage());
			}
		}
		return lineChartEntity;
	}
	
	/**
	 * 性能测试报表(外)
	 * @author lin.my
	 * @param appId
	 * @param operType
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public Page<PerformanceTestReport> queryPerformanceTestReport(String appId, String operType, long page, long rows)
			throws PaasWebException {
		Page<PerformanceTestReport> reportPage = new Page<PerformanceTestReport>();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryPerformanceTestReportUrl", appId, operType, page, rows);
		logger.debug("开始调用查询性能测试报表(外)的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, com.cmsz.paas.common.page.Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询性能测试报表(外)的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			com.cmsz.paas.common.page.Page<PerformanceTestStatementEntity> performanceTestReportPage = (com.cmsz.paas.common.page.Page<PerformanceTestStatementEntity>) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<PerformanceTestStatementEntity> performanceTestReportList = performanceTestReportPage.getResult();
				// list转json
				org.json.JSONArray array = new org.json.JSONArray(performanceTestReportList);
				String jsonStr = array.toString();
				performanceTestReportList = fromJsonList(jsonStr, PerformanceTestStatementEntity.class);
				List<PerformanceTestReport> list = new ArrayList<PerformanceTestReport>();
				if (performanceTestReportList != null) {
					for (PerformanceTestStatementEntity ctrlEntity : performanceTestReportList) {
						list.add(genPerformanceTestReport(ctrlEntity));
					}
				}
				reportPage.setResult(list);
				reportPage.setTotalCount(performanceTestReportPage.getTotalCount());
				logger.info("调用查询性能测试报表(外)Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询性能测试报表(外)出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_PERFORMANCE_TEST_REPORT_ERROR
						+ "]查询性能测试报表(外)出错", ex);
				throw new PaasWebException(
						Constants.QUERY_PERFORMANCE_TEST_REPORT_ERROR,
						ex.getMessage());
			}
		}
		return reportPage;
	}
	
	/**
	 * 性能测试报表（控制中心转前台）
	 * @author lin.my
	 * @param ctrlEntity
	 * @return
	 */
	private PerformanceTestReport genPerformanceTestReport(PerformanceTestStatementEntity ctrlEntity){
		PerformanceTestReport performanceTestReport = new PerformanceTestReport();
		
		performanceTestReport.setFlowId(ctrlEntity.getFlowId());
		performanceTestReport.setFlowName(ctrlEntity.getFlowName()); // 流水名称
		performanceTestReport.setBuildNum(ctrlEntity.getBuildNum()); // 构建次数
//		performanceTestReport.setTotalsNum(ctrlEntity.getTotals() + "");
		performanceTestReport.setExecutionNum(ctrlEntity.getTotals() + "");
		performanceTestReport.setFailNum(ctrlEntity.getFailNumber());
		
		return performanceTestReport;
	}
	
	/**
	 * 性能测试折线图(外)
	 * @author lin.my
	 * @param flowId
	 * @return
	 * @throws PaasWebException
	 */
	@Override
	public LineChartEntity queryPerformanceTestLineChart(String flowId) throws PaasWebException {
		LineChartEntity lineChartEntity=new LineChartEntity();
		String msg = "";
		String retCode = "";
		String url = RestUtils
				.restUrlTransform("queryPerformanceTestLineChartUrl", flowId);
		logger.debug("开始调用性能测试折线图(外)的rest接口：" + url);
		try {
			ResponseInfo responseInfo = reportDao.get(url, ReportChartList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用性能测试折线图(外)的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			ReportChartList reportChartList = (ReportChartList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				List<ReportChart> reportCharts=reportChartList.getReportChart();
				if (reportCharts != null) {					
					lineChartEntity=genReportReport(reportCharts);
				}
				logger.info("调用性能测试折线图(外)Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]性能测试折线图(外)出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_PERFORMANCE_TEST_LINE_CHART_ERROR
						+ "]性能测试折线图(外)出错", ex);
				throw new PaasWebException(
						Constants.QUERY_PERFORMANCE_TEST_LINE_CHART_ERROR,
						ex.getMessage());
			}
		}
		return lineChartEntity;
	}
	
}
