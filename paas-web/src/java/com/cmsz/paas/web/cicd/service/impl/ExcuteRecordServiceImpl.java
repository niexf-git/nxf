package com.cmsz.paas.web.cicd.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.FlowRecordEntity;
import com.cmsz.paas.common.model.controller.entity.StepRecordEntity;
import com.cmsz.paas.common.model.controller.response.Logs;
import com.cmsz.paas.common.model.controller.response.StepRecordList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.DateUtil;
import com.cmsz.paas.web.base.util.LogProcessUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.ExcuteRecordDao;
import com.cmsz.paas.web.cicd.model.FlowExcuteRecordEntity;
import com.cmsz.paas.web.cicd.model.FlowStepRecordEntity;
import com.cmsz.paas.web.cicd.model.StepRecodeEntity;
import com.cmsz.paas.web.cicd.service.ExcuteRecordService;
import com.cmsz.paas.web.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 执行记录
 * @author lin.my
 * @version 创建时间：2017年9月5日 下午4:45:02
 */
@Service("excuteRecordService")
public class ExcuteRecordServiceImpl implements ExcuteRecordService{
	private static final Logger logger = LoggerFactory
			.getLogger(ExcuteRecordServiceImpl.class);
	
	@Autowired
	ExcuteRecordDao  dao;
	
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

	/**
	 * 流水执行记录列表展示
	 * @author lin.my
	 */
	@Override
	public Page<FlowExcuteRecordEntity> queryFlowExcuteRecordList(String flowId,
			String page, String rows) throws PaasWebException {
		
		// 接收控制中心返回转换数据
		List<FlowExcuteRecordEntity> flowExcuteRecordList = new ArrayList<FlowExcuteRecordEntity>();
		Page<FlowExcuteRecordEntity> webPage = new Page<FlowExcuteRecordEntity>();
		String retCode = ""; // rest接口返回码
		String msg = ""; // rest接口返回信息
		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform("queryFlowExcuteRecordListUrl", flowId, page, rows);
		logger.debug("开始调用流水执行记录列表rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao.get(url, Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			@SuppressWarnings("unchecked")
			Page<FlowRecordEntity> controlPage = (Page<FlowRecordEntity>) responseInfo.getData();
			logger.debug("调用流水执行记录列表rest接口返回码：" + retCode + ", 返回信息：" + msg);
			List<FlowRecordEntity> list = controlPage.getResult();
			// list转json
			org.json.JSONArray array = new org.json.JSONArray(list);
			String jsonStr = array.toString();
			List<FlowRecordEntity> fList = fromJsonList(jsonStr, FlowRecordEntity.class);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (fList != null&&fList.size()>0) {
					for (FlowRecordEntity entity : fList) {
						flowExcuteRecordList.add(genFlowRecordEntity(entity));
					}
				}
				webPage.setResult(flowExcuteRecordList);
				webPage.setTotalCount(controlPage.getTotalCount());
				logger.info("调用流水执行记录列表rest接口返回成功！");
				return webPage;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]流水执行记录列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_FLOW_EXCUTE_RECORD_LIST_ERROR
						+ "]流水执行记录列表出错", ex);
				throw new PaasWebException(Constants.QUERY_FLOW_EXCUTE_RECORD_LIST_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/**
	 * 将控制中心的实体转换为前台的实体
	 * @param entity
	 * @return
	 */
	private FlowExcuteRecordEntity genFlowRecordEntity(FlowRecordEntity entity) {
		FlowExcuteRecordEntity flowExcuteRecordEntity = new FlowExcuteRecordEntity();
		if(entity != null){
			// 别名(job名称)组成：应用名_流水名_操作类型(webapp_theThird_dev)
			flowExcuteRecordEntity.setAliasName(entity.getAliasName().split("_")[1].toString());
			flowExcuteRecordEntity.setBeginTime(DateUtil.formatSqlTimestamp(entity.getBeginTime(), "yyyy-MM-dd HH:mm:ss"));
			flowExcuteRecordEntity.setBuildNum(entity.getBuildNum() + "");
			flowExcuteRecordEntity.setCodeVersion(entity.getCodeVersion());
			flowExcuteRecordEntity.setFlowId(entity.getFlowId());
			flowExcuteRecordEntity.setImageVersion(entity.getImageVersion());
			flowExcuteRecordEntity.setRunTime(entity.getRunTime());
			flowExcuteRecordEntity.setStatus(entity.getStatus());
			flowExcuteRecordEntity.setTag(entity.getTag());
			flowExcuteRecordEntity.setTriggerBy(entity.getTriggerBy());
			flowExcuteRecordEntity.setFlowRecordId(entity.getUuid());
		}
		return flowExcuteRecordEntity;
	}

	/***
	 *  流水执行记录
	 *  @author lin.my
	 */
	@Override
	public List<FlowStepRecordEntity> queryFlowExcuteRecord(String flowId,
			String flowRecordId) {
		// 接收控制中心返回转换数据
		List<FlowStepRecordEntity> flowExcuteRecord = new ArrayList<FlowStepRecordEntity>();
		
		StepRecordList listJson = new StepRecordList(); // 接收rest接口返回数据
		
		String retCode = ""; // rest接口返回码
		String msg = ""; // rest接口返回信息

		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform("queryFlowExcuteRecordUrl", flowId, flowRecordId);

		logger.debug("开始调用流水执行记录rest接口：" + url);

		try {
			ResponseInfo responseInfo = dao.get(url, StepRecordList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			listJson = (StepRecordList) responseInfo.getData();

			logger.debug("调用流水执行记录rest接口返回码：" + retCode + ", 返回信息：" + msg);

			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!listJson.equals(null)) {
					for (StepRecordEntity entity : listJson.getStepRecordList()) {
						flowExcuteRecord.add(genStepRecordEntity(entity));
					}
				}
				logger.info("调用流水执行记录rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]流水执行记录出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_FLOW_EXCUTE_RECORD_ERROR
						+ "]流水执行记录出错", ex);
				throw new PaasWebException(Constants.QUERY_FLOW_EXCUTE_RECORD_ERROR,
						ex.getMessage());
			}
		}
		return flowExcuteRecord;
	}
	
	/**
	 * 将控制中心的实体转换为前台的实体
	 * @param entity
	 * @return
	 */
	private FlowStepRecordEntity genStepRecordEntity(StepRecordEntity entity) {
		FlowStepRecordEntity flowStepRecordEntity = new FlowStepRecordEntity();
		if(entity != null){
			flowStepRecordEntity.setAliasName(entity.getAliasName());
			flowStepRecordEntity.setStepName(entity.getStepName());
			flowStepRecordEntity.setBeginTime(DateUtil.formatSqlTimestamp(entity.getBeginTime(), "yyyy-MM-dd HH:mm:ss"));
			flowStepRecordEntity.setBuildNum(entity.getBuildNum());
			flowStepRecordEntity.setFlowRecordId(entity.getFlowRecordId());
			flowStepRecordEntity.setReport(entity.getReport());
			flowStepRecordEntity.setRunTime(entity.getRunTime());
			flowStepRecordEntity.setStatus(entity.getStatus());
			flowStepRecordEntity.setUuid(entity.getUuid());
		}
		return flowStepRecordEntity;
	}
	
	
	@Override
	public List<StepRecodeEntity> queryStepRecodeList(String flowId,
			 String stepName) throws PaasWebException {
		List<StepRecodeEntity> stepRecodeList = null;
		//返回消息
		String msg = "";
		//返回编码
		String retCode = "";
		//rest请求拼接
		String url = RestUtils
				.restUrlTransform("queryStepRecodeListUrl", flowId,stepName);
		logger.debug("开始调用查询步骤构建记录列表的rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao
					.get(url, StepRecordList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询步骤构建记录里列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			StepRecordList recordList = (StepRecordList) responseInfo.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (recordList != null && recordList.getStepRecordList().size()>0) {
					stepRecodeList = genStepRecodeEntity(recordList.getStepRecordList());
				}
				logger.info("调用查询步骤构建记录里列表的Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询步骤构建记录里列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_STEPRECODELIST_ERROR
						+ "]查询步骤构建记录里列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_STEPRECODELIST_ERROR,
						ex.getMessage());
			}
		}
		return stepRecodeList;
	}
	
	@Override
	public RestResult queryBuildLogs(String flowId, String stepId,String asName)
			throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		BufferedReader reader = null;
		try {
			String url = RestUtils.restUrlTransform("queryExcuteRecordLogsUrl", flowId, stepId,asName);
			logger.info("开始调用Rest接口：" + url);
			
			String logContent = "";    //日志内容
			String currentLine = null;
			reader = dao.get(url);      
			while((currentLine = reader.readLine()) != null){
				ResponseInfo responseInfo = JsonUtil.json2ResponseInfoBean(currentLine, Logs.class);
				retCode = responseInfo.getRetCode();
				msg = PropertiesUtil.getValue(retCode);
				Logs logs = (Logs)responseInfo.getData();
				logger.debug("查询单步骤记录日志的rest接口返回码：" + retCode + ", 返回信息：" + msg);
				
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
			
			/*String[] logs = new String[10];
			
			logs[0] = "[INFO] org.springframework.web.context.ContextLoader.initWebApplicationContext(ContextLoader.java:187) 2017-09-04 14:46:37  Root WebApplicationContext: initialization started";
			logs[1] = "[INFO] org.springframework.context.support.AbstractApplicationContext.prepareRefresh(AbstractApplicationContext.java:456) 2017-09-04 14:46:37  Refreshing Root WebApplicationContext: startup date [Mon Sep 04 14:46:37 CST 2017]; root of context hierarchy";
			logs[2] = "[INFO] org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:315) 2017-09-04 14:46:38  Loading XML bean definitions from class path resource [applicationContext.xml]";
			logs[3] = "[INFO] org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider.registerDefaultFilters(ClassPathScanningCandidateComponentProvider.java:178) 2017-09-04 14:46:38  JSR-250 'javax.annotation.ManagedBean' found and supported for component scanning";
			logs[4] = "[INFO] org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:315) 2017-09-04 14:46:39  Loading XML bean definitions from class path resource [xfire-servlet.xml]";
			logs[5] = "[INFO] org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:315) 2017-09-04 14:46:39  Loading XML bean definitions from class path resource [org/codehaus/xfire/spring/xfire.xml]";
			logs[6] = "[INFO] org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:315) 2017-09-04 14:46:39  Loading XML bean definitions from class path resource [org/codehaus/xfire/spring/customEditors.xml]";
			logs[7] = "[INFO] org.springframework.beans.factory.support.DefaultListableBeanFactory.registerBeanDefinition(DefaultListableBeanFactory.java:618) 2017-09-04 14:46:39  Overriding bean definition for bean 'userService': replacing [Generic bean: class [com.cmsz.ws.impl.UserServiceImpl]; scope=singleton; abstract=false; lazyInit=false; autowireMode=1; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [E:\\workspace_monitor\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\paas-web4\\WEB-INF\\classes\\com\\cmsz\\ws\\impl\\UserServiceImpl.class]] with [Generic bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [xfire-servlet.xml]]";
			logs[8] = "[INFO] org.springframework.beans.factory.support.DefaultListableBeanFactory.registerBeanDefinition(DefaultListableBeanFactory.java:618) 2017-09-04 14:46:39  Overriding bean definition for bean 'groupService': replacing [Generic bean: class [com.cmsz.ws.impl.GroupServiceImpl]; scope=singleton; abstract=false; lazyInit=false; autowireMode=1; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [E:\\workspace_monitor\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\paas-web4\\WEB-INF\\classes\\com\\cmsz\\ws\\impl\\GroupServiceImpl.class]] with [Generic bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [xfire-servlet.xml]]";
			logs[9] = "[INFO] org.springframework.beans.factory.support.DefaultListableBeanFactory.registerBeanDefinition(DefaultListableBeanFactory.java:618) 2017-09-04 14:46:39  Overriding bean definition for bean 'departmentService': replacing [Generic bean: class [com.cmsz.ws.impl.DepartmentServiceImpl]; scope=singleton; abstract=false; lazyInit=false; autowireMode=1; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [E:\\workspace_monitor\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\paas-web4\\WEB-INF\\classes\\com\\cmsz\\ws\\impl\\DepartmentServiceImpl.class]] with [Generic bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [xfire-servlet.xml]]";
			
			for (String s : logs) {
				logContent += s;
				logContent +="\r\n";
			}
			result.setRetCode("200");
			result.setMsg("SUCCESS");
		    result.setData(LogProcessUtil.processLogContent(logContent));*/
		} catch (Exception ex) {
			logger.error("查询单步骤记录日志错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_STEP_LOGS_ERROR,ex.getMessage());
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
	
	private List<StepRecodeEntity> genStepRecodeEntity(List<StepRecordEntity> list){
		List<StepRecodeEntity> sreList = new ArrayList<StepRecodeEntity>();
		for (StepRecordEntity entity : list) {
			StepRecodeEntity recodeEntity = new StepRecodeEntity();
			recodeEntity.setStepId(entity.getUuid());
			recodeEntity.setStepRecodeId(entity.getFlowRecordId());
			recodeEntity.setStepName(entity.getStepName());
			recodeEntity.setStartTime(sdfDateTime(entity.getBeginTime()));
			recodeEntity.setExecuteTime(entity.getRunTime()+"s");
			recodeEntity.setStatus(entity.getStatus()+"");
			recodeEntity.setAsName(entity.getAliasName());
			sreList.add(recodeEntity);
		}
		
		return sreList;
	}
	
	private String sdfDateTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
