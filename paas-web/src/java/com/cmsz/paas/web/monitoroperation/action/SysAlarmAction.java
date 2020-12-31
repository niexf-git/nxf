package com.cmsz.paas.web.monitoroperation.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.log.entity.SysLogEntity;
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.cmsz.paas.web.monitoroperation.model.DataCenter;
import com.cmsz.paas.web.monitoroperation.model.Host;
import com.cmsz.paas.web.monitoroperation.model.SysAlarm;
import com.cmsz.paas.web.monitoroperation.model.SysAlarmConditionInfo;
import com.cmsz.paas.web.monitoroperation.model.SysAlarmInfo;
import com.cmsz.paas.web.monitoroperation.service.ClusterService;
import com.cmsz.paas.web.monitoroperation.service.DataCenterService;
import com.cmsz.paas.web.monitoroperation.service.HostService;
import com.cmsz.paas.web.monitoroperation.service.SysAlarmService;

/**
 * 监控运维模块Action.
 * 
 * @author ccl
 */
public class SysAlarmAction extends AbstractAction {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(SysAlarmAction.class);
	@Autowired
	private SysAlarmService sysAlarmService;
	/** 开始时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 集群ID */
	private String clusterId;
	/** 数据中心ID */
	private String dataCenterId;
	/** 节点ID */
	private String nodeId;
	/** 告警条件model */
	private SysAlarmConditionInfo alarmCondition;

	/** 错误信息. */
	private String errorMsg;

	/** 错误编码. */
	private String errorCode;
	/** 内存百分比 */
	private String memory;

	/** cpu百分比 */
	private String cpu;

	/** 系统文件百分比 */
	private String filesystem;
	@Autowired
	private DataCenterService dataCenterService;
	@Autowired
	private ClusterService clusterService;
	@Autowired
	private HostService hostService;

	/**
	 * 查询告警详情
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void queryAlarmList() throws Exception {
		if (null == startTime || null == endTime
				|| startTime.equals("") || endTime.equals("")) {
			Date date = new Date();
			endTime = DateUtil.tranformDate(date.toString());
			Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(
					date, -3);
			startTime = DateUtil.tranformDate(preDate.toString());
		}
		Page<SysAlarmInfo> page = this.getJqGridPage("time");
		logger.info("开始执行查询告警详情，起始时间：" + startTime + "，结束时间：" + endTime
				+ "数据中心ID：" + dataCenterId + "集群ID：" + clusterId + "节点ID："
				+ nodeId);
		try {
			PageContext buildPageContext = Struts2.buildPageContext();
			buildPageContext.addParam("endTime", endTime);
			buildPageContext.addParam("startTime", startTime);
			buildPageContext.addParam("clusterId", clusterId);
			buildPageContext.addParam("dataCenterId", dataCenterId);
			buildPageContext.addParam("nodeId", nodeId);		
			com.cmsz.paas.common.page.Page<SysAlarmInfo> ararmList = sysAlarmService.queryAlarmList(buildPageContext);
			page.setResult(ararmList.getResult());
			page.setTotalCount(ararmList.getTotalCount());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询告警详情成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询告警详情出错", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询告警条件
	 * 
	 * @throws Exception
	 */
	public String queryAlarmCondition() {
		logger.info("开始执行查询告警条件");
		try {
			alarmCondition = sysAlarmService.queryAlarmCondition();
			logger.info("执行查询告警条件成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询告警条件出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}

		return SUCCESS;
	}

	/**
	 * 修改告警条件
	 * 
	 * @throws Exception
	 */
	public void modifyAlarmCondition() {
		logger.info("开始执行修改告警条件");
		try {
			SysAlarmConditionInfo alarmConditionInfo = new SysAlarmConditionInfo();
			alarmConditionInfo.setCpu(cpu);
			alarmConditionInfo.setFilesystem(filesystem);
			alarmConditionInfo.setMemory(memory);
			sysAlarmService.modifyAlarmCondition(alarmConditionInfo);
			logger.info("执行修改告警条件成功！");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]修改告警条件出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}

	/**
	 * 告警初始化页面数据中心下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryDataCenterList() {
		logger.info("告警初始化页面数据中心下拉框数据开始");
		List<DataCenter> list = dataCenterService.queryDataCenterList();
		List<SelectType> selectList = new ArrayList<SelectType>();
		SelectType selectTypeTemp = new SelectType();
		selectTypeTemp.setValue("");
		selectTypeTemp.setText("请选择");
		selectList.add(selectTypeTemp);
		for (int i = 0; i < list.size(); i++) {
			SelectType selectType = new SelectType();
			selectType.setValue(list.get(i).getId());
			selectType.setText(list.get(i).getName());
			selectList.add(selectType);
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
				.fromObject(selectList);
		logger.info("告警初始化页面数据中心下拉框数据完成：" + jsonArray.toString());
		sendSuccessReslult(jsonArray.toString());
	}

	/**
	 * 告警初始化页面集群下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryClusterList() {
		logger.info("告警初始化页面集群下拉框数据开始");
		if (dataCenterId.equals("")) {
			sendSuccessReslult("");
		} else {
			List<Cluster> list = clusterService.queryClusterList(dataCenterId);
			List<SelectType> selectList = new ArrayList<SelectType>();
			for (int i = 0; i < list.size(); i++) {
				SelectType selectType = new SelectType();
				selectType.setValue(list.get(i).getId());
				selectType.setText(list.get(i).getName());
				selectList.add(selectType);
			}
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
					.fromObject(selectList);
			logger.info("告警初始化页面集群下拉框数据完成：" + jsonArray.toString());
			sendSuccessReslult(jsonArray.toString());
		}
	}

	/**
	 * 告警初始化页面节点下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryNodeList() {
		logger.info("告警初始化页面节点下拉框数据开始");
		if (clusterId.equals("")) {
			sendSuccessReslult("");
		} else {
			List<Host> list = hostService.queryHostList(clusterId);
			List<SelectType> selectList = new ArrayList<SelectType>();
			for (int i = 0; i < list.size(); i++) {
				SelectType selectType = new SelectType();
				selectType.setValue(list.get(i).getId());
				selectType.setText(list.get(i).getHostIP());
				selectList.add(selectType);
			}
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
					.fromObject(selectList);
			logger.info("告警初始化页面节点下拉框数据完成：" + jsonArray.toString());
			sendSuccessReslult(jsonArray.toString());
		}
	}

	/**
	 * 初始化页面查询时间控件的区间.
	 */
	@UnLogging
	public void initQueryDateTime() {
		Date date = new Date();
		String maxDate = DateUtil.tranformDate(date.toString());
		Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(date, -3);
		String minDate = DateUtil.tranformDate(preDate.toString());
		String str = "{\"maxDate\":\"" + maxDate + "\",\"minDate\":\""
				+ minDate + "\"}";
		sendSuccessReslult(str);
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public SysAlarmConditionInfo getAlarmCondition() {
		return alarmCondition;
	}

	public void setAlarmCondition(SysAlarmConditionInfo alarmCondition) {
		this.alarmCondition = alarmCondition;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getFilesystem() {
		return filesystem;
	}

	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}

}
