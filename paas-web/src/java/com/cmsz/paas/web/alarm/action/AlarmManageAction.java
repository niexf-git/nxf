package com.cmsz.paas.web.alarm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.alarm.entity.AlarmDetail;
import com.cmsz.paas.web.alarm.service.AlarmService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.user.service.UserManagerService;

/**
 * 系统告警管理类
 * 
 * @author li.lv 2015-4-14
 */
public class AlarmManageAction extends AbstractAction {

	private static final long serialVersionUID = 9215236536146006130L;

	private static final Logger logger = LoggerFactory
			.getLogger(AlarmManageAction.class);

	@Autowired
	private AlarmService alarmService;

	/** 用户管理service对象 . */
	@Autowired
	private UserManagerService userManagerService;

	/** 应用名称 */
	private String appName;

	/** 告警时间起始 */
	private String insertTimeStart;
	/** 告警时间结束 */
	private String insertTimeEnd;
	/** 实例名称 */
	private String keyWord;

	/** 操作类型 */
	private String operType;

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	/** 告警类型映射集合 */
	private Map<String, String> alarmTypeMap = new HashMap<String, String>();
	/** 资源类型 映射集合 */
	private Map<String, String> eventItemMap = new HashMap<String, String>();
	/** 告警级别映射集合 */
	private Map<String, String> alarmLevelMap = new HashMap<String, String>();
	/** 告警操作类型映射集合 */
	private Map<String, String> alarmOperTypeMap = new HashMap<String, String>();

	/**
	 * Query alarm. 系统告警查询
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void queryAlarm() throws Exception {
		try {
			// 初始化查询时间范围
			if (null == insertTimeStart || null == insertTimeEnd
					|| insertTimeStart.equals("") || insertTimeEnd.equals("")) {
				Date date = new Date();
				insertTimeEnd = DateUtil.tranformDate(date.toString());
				Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(
						date, -3);
				insertTimeStart = DateUtil.tranformDate(preDate.toString());
			}
			logger.info("查询系统告警信息，起始时间：" + insertTimeStart + "，结束时间："
					+ insertTimeEnd + "应用名称：" + appName+ "实例名称：" + keyWord+ "操作类型：" + operType);
			// 获取用户数据权限
			String appNames = "";
			String[] appNamess = getSessionMap().get("appPermissionName")
					.toString().split(",");
			for (int i = 0; i < appNamess.length; i++) {
				if (i == appNamess.length - 1) {
					appNames += "'" + appNamess[i] + "'";
				} else {
					appNames += "'" + appNamess[i] + "',";
				}
			}
			appNames = "(" + appNames + ")";

			Page<AlarmDetail> pageData = this.getJqGridPage("insertTime");
			// 写入sql查询条件
			PageContext buildPageContext = Struts2.buildPageContext();
			
			appName = getSessionMap().get("appPerSelectedName").toString();
			appName= appName.split(",").length>1 ? null:appName.split(",")[0];
			operType = getSessionMap().get("selectedOpertype").toString();
			operType = operType.split(",").length>1 ? null:operType.split(",")[0];
			buildPageContext.addParam("appName", appName);
			if("_".equals(keyWord)){
				keyWord = "\\" + keyWord;
			}
			buildPageContext.addParam("keyWord", keyWord);
			buildPageContext.addParam("operType", operType);
			buildPageContext.addParam("insertTimeStart", insertTimeStart);
			buildPageContext.addParam("insertTimeEnd", insertTimeEnd);

			buildPageContext.addParam("appNames", appNames);

			com.cmsz.paas.common.page.Page<AlarmDetail> findPage = new com.cmsz.paas.common.page.Page<AlarmDetail>();
			findPage = alarmService.findPage(buildPageContext);
			pageData.setResult(findPage.getResult());
			pageData.setTotalCount(findPage.getTotalCount());
			JSONObject jsonPage = this.getJsonPage(pageData);
			JSONArray jsonList = new JSONArray();
			// 数据映射转换成中文
			getAlarmLevelMap();
			getAlarmTypeMap();
			getEventItemMap();
			getAlarmOperTypeMap();
			if (pageData.getResult() != null) {
				for (AlarmDetail alarmDetail : pageData.getResult()) {
					if (null != alarmDetail.getAlarmLevel()) {
						alarmDetail.setAlarmLevel(alarmLevelMap.get(alarmDetail
								.getAlarmLevel()));
					}
					if (null != alarmDetail.getAlarmType()) {
						alarmDetail.setAlarmType(alarmTypeMap.get(alarmDetail
								.getAlarmType()));
					}
					if (null != alarmDetail.getEventItem()) {
						alarmDetail.setEventItem(eventItemMap.get(alarmDetail
								.getEventItem()));
					}
					if (null != alarmDetail.getOperType()) {
						alarmDetail.setOperType(alarmOperTypeMap
								.get(alarmDetail.getOperType()));
					}
					JSONObject jo = this.entityToJsonObject(alarmDetail);
					jsonList.put(jo);
				}
			}
			jsonPage.put("result", jsonList);
			this.renderText(jsonPage.toString());
			logger.info("分页查询系统告警信息成功，查询结果：" + jsonList);
		} catch (PaasWebException ex) {
			logger.error("查询系统告警信息异常", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
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

	/**
	 * 根据时间和资源类型查询系统告警信息
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryAlarmByTime() throws Exception {
		try {
			List<AlarmDetail> list = null;
			// 获取用户数据权限
//			@SuppressWarnings("unchecked")
//			List<ClusterInfo> clusterInfoList = (List<ClusterInfo>) getSessionMap()
//					.get("dataPermission");
			// sql查询条件-数据权限信息
//			String dataPermission = "";
			// 判断数据权限是否为空
//			if (!clusterInfoList.isEmpty()) {
//				dataPermission = "(";
//				for (int i = 0; i < clusterInfoList.size(); i++) {
//					if (i == clusterInfoList.size() - 1) {
//						dataPermission += "'"
//								+ clusterInfoList.get(i).getLabel() + "'" + ")";
//					} else {
//						dataPermission += "'"
//								+ clusterInfoList.get(i).getLabel() + "'" + ",";
//					}
//				}
//				// 根据用户的数据权限-可操作集群-筛选数据
//				list = alarmService.findByTime(dataPermission);
//			} else {
//				// 没有数据权限不需查询数据库
//				list = new ArrayList<AlarmDetail>();
//			}
			String selectAppName = (String) getSessionMap().get("appPerSelectedName");
			list = alarmService.findByTime(selectAppName);
			// 数据映射转换成中文
			getAlarmLevelMap();
			getAlarmTypeMap();
			getEventItemMap();
			getAlarmOperTypeMap();
			JSONArray jsonList = new JSONArray();
			for (AlarmDetail alarmDetail : list) {
				if (null != alarmDetail.getAlarmLevel()) {
					alarmDetail.setAlarmLevel(alarmLevelMap.get(alarmDetail
							.getAlarmLevel()));
				}
				if (null != alarmDetail.getAlarmType()) {
					alarmDetail.setAlarmType(alarmTypeMap.get(alarmDetail
							.getAlarmType()));
				}
				if (null != alarmDetail.getEventItem()) {
					alarmDetail.setEventItem(eventItemMap.get(alarmDetail
							.getEventItem()));
				}
				if (null != alarmDetail.getOperType()) {
					alarmDetail.setOperType(alarmOperTypeMap.get(alarmDetail
							.getOperType()));
				}
				JSONObject jo = this.entityToJsonObject(alarmDetail);
				jsonList.put(jo);
			}
			sendSuccessReslult(jsonList.toString());
		} catch (PaasWebException ex) {
			logger.error("查询系统告警信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}

	}

	/**
	 * 系统告警初始化页面应用名称下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryAppList() {
		logger.info("系统告警初始化页面应用名称下拉框数据开始");
		String[] appNames = getSessionMap().get("appPermissionName").toString()
				.split(",");
		List<SelectType> selectList = new ArrayList<SelectType>();
		SelectType selectTypeTemp = new SelectType();
		selectTypeTemp.setValue("");
		selectTypeTemp.setText("全部");
		selectList.add(selectTypeTemp);
		for (int i = 0; i < appNames.length; i++) {
			SelectType selectType = new SelectType();
			selectType.setValue(appNames[i]);
			selectType.setText(appNames[i]);
			selectList.add(selectType);
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
				.fromObject(selectList);
		logger.info("系统告警初始化页面应用名称下拉框数据完成：" + jsonArray.toString());
		sendSuccessReslult(jsonArray.toString());
	}

	/**
	 * 系统告警初始化页面操作类型下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryOperTypeList() {
		logger.info("系统告警初始化页面应用名称下拉框数据开始");
		String[] operTypes = getSessionMap().get("opertype").toString()
				.split(",");
		List<SelectType> selectList = new ArrayList<SelectType>();
		SelectType selectTypeTemp = new SelectType();
		selectTypeTemp.setValue("");
		selectTypeTemp.setText("全部");
		selectList.add(selectTypeTemp);
		getAlarmOperTypeMap();
		for (int i = 0; i < operTypes.length; i++) {
			SelectType selectType = new SelectType();
			selectType.setValue(operTypes[i]);
			selectType.setText(alarmOperTypeMap.get(operTypes[i]));
			selectList.add(selectType);
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
				.fromObject(selectList);
		logger.info("系统告警初始化页面操作类型下拉框数据完成：" + jsonArray.toString());
		sendSuccessReslult(jsonArray.toString());
	}

	public Map<String, String> getAlarmTypeMap() {
		alarmTypeMap.put("0", "告警恢复");
		alarmTypeMap.put("1", "告警");
		return alarmTypeMap;
	}

	public void setAlarmTypeMap(Map<String, String> alarmTypeMap) {
		this.alarmTypeMap = alarmTypeMap;
	}

	public Map<String, String> getEventItemMap() {
		eventItemMap.put("1", "CPU");
		eventItemMap.put("2", "内存");
		eventItemMap.put("3", "停止");
		eventItemMap.put("4", "重启");
		return eventItemMap;
	}

	public Map<String, String> getAlarmOperTypeMap() {
		alarmOperTypeMap.put("1", "开发");
		alarmOperTypeMap.put("2", "测试");
		alarmOperTypeMap.put("3", "运维");
		return alarmOperTypeMap;
	}

	public void setEventItemMap(Map<String, String> eventItemMap) {
		this.eventItemMap = eventItemMap;
	}

	public Map<String, String> getAlarmLevelMap() {
		alarmLevelMap.put("1", "一般");
		alarmLevelMap.put("2", "严重");
		return alarmLevelMap;
	}

	public void setAlarmLevelMap(Map<String, String> alarmLevelMap) {
		this.alarmLevelMap = alarmLevelMap;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getInsertTimeStart() {
		return insertTimeStart;
	}

	public void setInsertTimeStart(String insertTimeStart) {
		this.insertTimeStart = insertTimeStart;
	}

	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}
	
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

}
