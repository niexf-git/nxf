package com.cmsz.paas.web.cicd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.FlowEntity;
import com.cmsz.paas.common.model.controller.entity.StepEntity;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.page.SortInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.DateUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.FlowManagerDao;
import com.cmsz.paas.web.cicd.model.FlowInfo;
import com.cmsz.paas.web.cicd.model.FlowNameEntity;
import com.cmsz.paas.web.cicd.model.StepInfo;
import com.cmsz.paas.web.cicd.service.FlowManagerService;
import com.cmsz.paas.web.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 流水管理service实现
 * @author jiayz
 * 
 */
@Service("flowManagerService")
public class FlowManagerServiceImpl implements FlowManagerService{
	private static final Logger logger = LoggerFactory
			.getLogger(FlowManagerServiceImpl.class);
	
	@Autowired
	FlowManagerDao  dao;
	
	@Override
	public void createFlow(FlowInfo flow) {
		logger.info("收到创建流水请求，flow：" + flow.toString());
		String url = RestUtils.restUrlTransform("createFlowUrl");
		ResponseInfo responseInfo;
		String retCode = "";;
		String msg = "";
		try {
			logger.debug("开始调用创建流水的rest接口：" + url);
			FlowEntity fe = translateToFlow(flow);
			responseInfo = dao.create(url, fe,IdValue.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CREATE_FLOW_ERROR,
						"无法访问创建流水的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用创建流水rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用创建流水的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]创建流水出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.CREATE_FLOW_ERROR
						+ "]创建流水出错", ex);
				throw new PaasWebException(Constants.CREATE_FLOW_ERROR,
						ex.getMessage());
			}
		}
	}

	private FlowEntity translateToFlow(FlowInfo flow) {
		FlowEntity f = new FlowEntity();
		if(flow!=null){
			f.setAppId(Long.parseLong(flow.getAppId()));
			f.setCreateBy(flow.getCreateBy());
			f.setDesc(flow.getFlowDescription());
			f.setName(flow.getFlowName());
			f.setRoleType(Integer.parseInt(flow.getRoleType()));
			List<StepEntity> ctrlList = getCtrlStepEntityList(flow);
			f.setStepList(ctrlList);
		}
		return f;
	}
	
	/**
	 * 复制流水（前台转控制中心）
	 * @param flow
	 * @return
	 */
	private FlowEntity translateToCtrlFlow(FlowInfo flow) {
		FlowEntity f = new FlowEntity();
		if(flow != null){
			f.setAppId(Long.parseLong(flow.getAppId()));
			f.setUuid(flow.getFlowId());
			f.setCreateBy(flow.getCreateBy());
			f.setDesc(flow.getFlowDescription());
			f.setName(flow.getFlowName());
			f.setRoleType(Integer.parseInt(flow.getOperType()));
			List<StepEntity> ctrlList = getCtrlStepEntityList(flow);
			f.setStepList(ctrlList);
		}
		return f;
	}

	private List<StepEntity> getCtrlStepEntityList(FlowInfo flow) {
		List<StepEntity> ctrlList = new ArrayList<StepEntity>();
		List<StepInfo> list = flow.getStepList();
		if(list!=null&&list.size()>0){
			for(StepInfo si:list){
				StepEntity fe = new StepEntity();
				String isChoise = si.getIsChoise();
				String stepName = si.getStepName();
				fe.setIsChoise(Integer.parseInt(isChoise));
				fe.setStepName(stepName);
				ctrlList.add(fe);
			}
		}
		return ctrlList;
	}
	
	/**
	 * 模糊查询(流水列表)
	 * @param appId 应用ID
	 * @param operType 操作类型
	 * @param searchKey 模糊查询
	 * @param page 当前页数
	 * @param rows 每页条数
	 * @author lin.my
	 * @throws PaasWebException
	 */
	@Override
	public com.cmsz.paas.web.base.dao.page.Page<FlowInfo> queryFlowList(String appId, String operType,
			String searchKey, PageContext pc) throws PaasWebException {
		// 接收控制中心返回转换数据
		List<FlowInfo> flowList = new ArrayList<FlowInfo>();
		com.cmsz.paas.web.base.dao.page.Page<FlowInfo> webPage = new com.cmsz.paas.web.base.dao.page.Page<FlowInfo>();
		String retCode = ""; // rest接口返回码
		String msg = ""; // rest接口返回信息
		String page = pc.getBound().getOffset() + ""; //当前页数
		String rows = pc.getBound().getLimit() + ""; //每页条数
		List<SortInfo> sortList = pc.getSorts();
		String sidx = "";//排序字段
		String sord="";//升序还是降序
		if(sortList.size()>0){
			sidx = sortList.get(0).getSortField();
			sord = sortList.get(0).getSortValue();
			if("FLOW_NAME".equals(sidx)){//排序字段改为控制中心的格式
				sidx = "flowName";
			}else if("APP_NAME".equals(sidx)){
				sidx = "appName";
			}else if("UPDATE_TIME".equals(sidx)){
				sidx = "createTime";
			}else if("BUILD_STATUS".equals(sidx)){
				sidx = "flowStatus";
			}
		}
		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform("queryFlowListUrl", appId, operType, page, rows, searchKey,sidx,sord);
		logger.debug("开始调用模糊查询(流水列表)rest接口：" + url);
		try {
			ResponseInfo responseInfo = dao.get(url, Page.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			@SuppressWarnings("unchecked")
			Page<FlowEntity> controlPage = (Page<FlowEntity>) responseInfo.getData();
			logger.debug("调用模糊查询(流水列表)rest接口返回码：" + retCode + ", 返回信息：" + msg);
			List<FlowEntity> list = controlPage.getResult();
			// list转json
			org.json.JSONArray array = new org.json.JSONArray(list);
			String jsonStr = array.toString();
			List<FlowEntity> fList = fromJsonList(jsonStr, FlowEntity.class);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (fList != null && fList.size()>0) {
					for (FlowEntity entity : fList) {
						flowList.add(genFlowEntity(entity));
					}
				}
				webPage.setResult(flowList);
				webPage.setPageSize((int)controlPage.getRange().getLimit());
				webPage.setTotalCount(controlPage.getTotalCount());
				logger.info("调用模糊查询(流水列表)rest接口返回成功！");
				return webPage;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]模糊查询(流水列表)", ex);
				throw new PaasWebException(retCode, msg);
			} else {	
				logger.error("[" + Constants.QUERY_FLOW_ERROR
						+ "]模糊查询(流水列表)出错", ex);
				throw new PaasWebException(Constants.QUERY_FLOW_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/**
	 * 解析的时候加行了双引号
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	private <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
		ArrayList<T> mList = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		//Gson mGson = new Gson();
		Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		for (final JsonElement elem : array) {
			mList.add(mGson.fromJson(elem, cls));
		}
		return mList;
	}

	/**
	 * 将控制中心的实体转换为前台的实体
	 * @param entity
	 * @return
	 */
	private FlowInfo genFlowEntity(
			FlowEntity entity) {
		FlowInfo flowEntity = new FlowInfo();
		if(entity != null){
			flowEntity.setFlowId(entity.getUuid());
			flowEntity.setFlowName(entity.getName());
			flowEntity.setDeployType(entity.getDeployType());
			flowEntity.setOperType(entity.getRoleType() + "");
			flowEntity.setAppName(entity.getApp()!=null?entity.getApp().getName():"");
//			flowEntity.setUpdateTime(DateUtil.formatSqlTimestamp(entity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			if(entity.getFlowRecordList().size() > 0){
				flowEntity.setUpdateTime(DateUtil.formatSqlTimestamp(entity.getFlowRecordList().get(0).getBeginTime(), "yyyy-MM-dd HH:mm:ss"));
			} else {
				flowEntity.setUpdateTime("未执行过构建");
			}
			flowEntity.setBuildStatus(entity.getStatus() + "");
			if(entity.getFlowRecordList() != null && entity.getFlowRecordList().size() > 0){
				for(int i = 0; i < entity.getFlowRecordList().size(); i++){
					flowEntity.setFlowRecordId(entity.getFlowRecordList().get(i).getUuid());
				}
			}
			flowEntity.setCreateTime(DateUtil.formatSqlTimestamp(entity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		return flowEntity;
	}

	@Override
	public void modifyFlow(FlowInfo flow) {
		logger.info("收到修改流水请求，flow：" + flow.toString());
		String url = RestUtils.restUrlTransform("modifyFlowUrl",flow.getFlowId());
		ResponseInfo responseInfo;
		String retCode = "";
		String msg = "";
		try {
			logger.debug("开始调用修改流水的rest接口：" + url);
			FlowEntity fe = translateToFlow(flow);
			responseInfo = dao.update(url, fe,IdValue.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.MODIFY_FLOW_ERROR,
						"无法访问修改流水的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用创建流水rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用修改流水的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]修改流水出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.MODIFY_FLOW_ERROR
						+ "]修改流水出错", ex);
				throw new PaasWebException(Constants.MODIFY_FLOW_ERROR,
						ex.getMessage());
			}
		}
		
	}

	@Override
	public void deleteFlow(String flowId) {
		logger.info("收到删除流水请求，flowId：" + flowId);
		String url = RestUtils.restUrlTransform("deleteFlowUrl",flowId);
		ResponseInfo responseInfo;
		String retCode = "";
		String msg = "";
		try {
			logger.debug("开始调用删除流水的rest接口：" + url);
			responseInfo = dao.delete(url,IdValue.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.DELETE_FLOW_ERROR,
						"无法访问删除流水的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用删除流水rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用删除流水的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]删除流水出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.DELETE_FLOW_ERROR
						+ "]删除流水出错", ex);
				throw new PaasWebException(Constants.DELETE_FLOW_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public FlowInfo queryFlowById(String flowId) {
		logger.info("收到查询单个流水请求，flowId：" + flowId);
		String url = RestUtils.restUrlTransform("queryFlowByIdUrl",flowId);
		ResponseInfo responseInfo;
		String retCode = "";;
		String msg = "";
		try {
			logger.debug("开始调用查询单个流水的rest接口：" + url);
			responseInfo = dao.get(url,FlowEntity.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.QUERY_FLOW_BY_ID,
						"无法访问查询单个流水的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用查询单个流水rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用查询单个流水的rest接口返回成功！");
				FlowEntity fe = (FlowEntity)responseInfo.getData();
				FlowInfo f = translateToCtrlFlow(fe);
				return f;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询单个流水出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_FLOW_BY_ID
						+ "]查询单个流水出错", ex);
				throw new PaasWebException(Constants.QUERY_FLOW_BY_ID,
						ex.getMessage());
			}
		}
	}

	private FlowInfo translateToCtrlFlow(
			FlowEntity fe) {
		FlowInfo f = new FlowInfo();
		if(fe!=null){
			f.setFlowId(fe.getUuid());
			f.setAppId(fe.getAppId() + "");
			f.setOperType(fe.getRoleType() + "");
			f.setFlowName(fe.getName());
			f.setCreateBy(fe.getCreateBy());
			f.setBuildStatus(fe.getStatus()+"");
			f.setDeployType(fe.getDeployType());
			f.setRoleType(fe.getRoleType()+"");
			//f.setBuildCount(fe.getBuildNum());
			//f.setPreBuildTime(preBuildTime);
			/*if(fe.getPrivateImage()!=null){
				f.setImageName(fe.getPrivateImage().getName());
			}*/
			if (fe.getFlowRecordList()!=null&&fe.getFlowRecordList().size()>0) {
				f.setRunTime(fe.getFlowRecordList().get(0).getRunTime());
			}
			if (fe.getFlowRecord()!=null) {
				f.setFlowRecordId(fe.getFlowRecord().getUuid());
				f.setBuildCount(fe.getFlowRecord().getBuildNum());
				f.setPreBuildTime(DateUtil.formatSqlTimestamp(fe.getFlowRecord().getBeginTime(),"yyyy-MM-dd HH:mm:ss"));
			}
			if (fe.getPrivateImage()!=null) {
				f.setImageId(fe.getPrivateImage().getPid());
			}
			f.setImageName(fe.getImageName());
			f.setFlowDescription(fe.getDesc());
			f.setCreateTime(DateUtil.formatSqlTimestamp(fe.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
			List<StepInfo> iList = getStepInfoList(fe);
			f.setStepList(iList);
		}
		return f;
	}

	private List<StepInfo> getStepInfoList(
			FlowEntity fe) {
		List<StepInfo> iList = new ArrayList<StepInfo>();
		List<StepEntity> list = fe.getStepList();
		if(list!=null&&list.size()>0){
			for(StepEntity se : list){
				StepInfo si = new StepInfo();
				int isChoise = se.getIsChoise();
				String stepName = se.getStepName();
				si.setStepName(stepName);
				si.setIsChoise(isChoise+"");
				iList.add(si);
			}
		}
		return iList;
	}
	
	/**
	 * 立即构建
	 * @author lin.my
	 * @throws PaasWebException
	 */
	@Override
	public void promptBuild(String flowId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		ResponseInfo responseInfo = null;
		try {
			String url = RestUtils.restUrlTransform("promptBuildUrl", flowId);
			logger.info("开始调用立即构建Rest接口：" + url);
			
			responseInfo = dao.update(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			
			logger.debug("调用立即构建rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用立即构建rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("立即构建错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.PROMPT_BUILD_ERROR,ex.getMessage());
			}
		}
	}
	
	/**
	 * 停止流水
	 * @author lin.my
	 * @throws PaasWebException
	 */
	@Override
	public void stopFlow(String flowId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		ResponseInfo responseInfo = null;
		try {
			String url = RestUtils.restUrlTransform("stopFlowUrl", flowId);
			logger.info("开始调用停止流水Rest接口：" + url);
			
			responseInfo = dao.update(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			
			logger.debug("调用停止流水rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用停止流水rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("停止流水错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, "停止流水错误！");
			} else {
				throw new PaasWebException(Constants.STOP_FLOW_ERROR,ex.getMessage());
			}
		}
	}
	
	/**
	 * 复制流水
	 * @author lin.my
	 * @throws PaasWebException
	 */
	@Override
	public void copyFlow(FlowInfo flow) {
		logger.info("收到复制流水请求，flow：" + flow.toString());
		String url = RestUtils.restUrlTransform("copyFlowUrl",flow.getFlowId());
		ResponseInfo responseInfo = null;
		String retCode = "";
		String msg = "";
		try {
			logger.debug("开始调用复制流水的rest接口：" + url);
			FlowEntity fe = translateToCtrlFlow(flow);
			responseInfo = dao.update(url, fe, IdValue.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.COPY_FLOW_ERROR,
						"无法访问复制流水的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用复制流水rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用复制流水的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]复制流水出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.COPY_FLOW_ERROR
						+ "]复制流水出错", ex);
				throw new PaasWebException(Constants.COPY_FLOW_ERROR,
						ex.getMessage());
			}
		}
	}
	@Override
	public List<FlowInfo> queryFlowByIds(String flowIds) throws PaasWebException {
		
		List<FlowInfo> list = null;
		logger.info("收到多流水查询请求，flowIds：" + flowIds);
		String url = RestUtils.restUrlTransform("queryFlowListByIdUrl",flowIds);
		ResponseInfo responseInfo = null;
		String retCode = "";
		String msg = "";
		try {
			logger.debug("开始调用复制流水的rest接口：" + url);
			responseInfo = dao.get(url, Page.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.QUERY_FLOW_BY_ID,
						"无法访问多流水查询的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用复制流水rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用复制流水的rest接口返回成功！");
				Page<FlowEntity> entity = (Page<FlowEntity>) responseInfo.getData();
				List<FlowEntity> fList = entity.getResult();
				
				if(null != fList){
					org.json.JSONArray array = new org.json.JSONArray(fList);
					String jsonStr = array.toString();
					List<FlowEntity> flowList = fromJsonList(jsonStr, FlowEntity.class);
					list = new ArrayList<FlowInfo>();
					for (FlowEntity flowEntity : flowList) {
						list.add(genFlowEntity(flowEntity));
					}
				}
				
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]复制流水出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_FLOW_BY_ID
						+ "]复制流水出错", ex);
				throw new PaasWebException(Constants.QUERY_FLOW_BY_ID,
						ex.getMessage());
			}
		}
		return list;
	}

}
