package com.cmsz.paas.web.monitoroperation.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.application.model.ApplicationInfo;
import com.cmsz.paas.web.application.service.ApplicationService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.monitoroperation.model.DataCenter;
import com.cmsz.paas.web.monitoroperation.model.ImageSync;
import com.cmsz.paas.web.monitoroperation.model.ImageSyncQueryConditions;
import com.cmsz.paas.web.monitoroperation.service.DataCenterService;
import com.cmsz.paas.web.monitoroperation.service.ImageSyncService;

/**
 * 
 * @author lin.my
 * @version 创建时间：2017年1月11日 下午2:24:36
 */
@UnLogging
public class ImageSyncAction extends AbstractAction {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ImageSyncAction.class);
	
	/** 应用ID */
	private String appId = "";

	/** 数据中心ID */
	private String dataCenterId = "";

	/** 状态 */
	private String status = "";
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;

	/** 错误信息. */
	private String errorMsg;

	/** 错误编码. */
	private String errorCode;
	
	@Autowired
	private ImageSyncService jobService;
	
	@Autowired
	private ApplicationService applicationRestService;
	
	@Autowired
	private DataCenterService dataCenterService;
	
	/**
	 * 镜像同步
	 * @throws Exception
	 */
	@UnLogging
	@SuppressWarnings("unchecked")
	public void queryImageSyncList() throws Exception {
		if (null == startTime || null == endTime
				|| "".equals(startTime) || "".equals(endTime)) {
			Date date = new Date();
			endTime = DateUtil.tranformDate(date.toString());
			Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(date, -3);
			startTime = DateUtil.tranformDate(preDate.toString());
		}
		Page<ImageSync> page = this.getJqGridPage("time");
		logger.info("开始镜像同步，起始时间：" + startTime + "，结束时间：" + endTime
				+ "数据中心ID：" + dataCenterId + "应用ID：" + appId);
		try {
			ImageSyncQueryConditions condition = new ImageSyncQueryConditions();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			condition.setEndTime((df.parse(endTime).getTime()+"").substring(0, 10));
			condition.setStartTime((df.parse(startTime).getTime()+"").substring(0, 10));
			
			if("".equals(appId) || "".equals(dataCenterId)){
				List<ImageSync> list = Collections.emptyList();
				page.setResult(list);
				page.setTotalCount(list.size());
				this.renderText(JackJson.fromObjectToJson(page));
			} else {
				if(!"".equals(appId)){
					condition.setAppId(appId);
				}
				if (!"".equals(dataCenterId)) {
					condition.setDataCenterId(dataCenterId);
				}
				if(!"".equals(status)){
					condition.setStatus(status);
				}
				
				List<ImageSync> list = jobService.queryImageSyncList(condition);
				page.setResult(list);
				page.setTotalCount(list.size());
				this.renderText(JackJson.fromObjectToJson(page));

				logger.info("镜像同步成功！");
			}
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]镜像同步出错", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 初始化页面-应用下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryAppsList() {
		logger.info("初始化页面应用下拉框数据开始");
		
		List<ApplicationInfo> list = applicationRestService.queryRestAppList("", "");
		
		List<SelectType> selectList = new ArrayList<SelectType>();
		SelectType selectTypeTemp = new SelectType();
		selectTypeTemp.setValue("");
		selectTypeTemp.setText("请选择");
		selectList.add(selectTypeTemp);
		for (int i = 0; i < list.size(); i++) {
			SelectType selectType = new SelectType();
			selectType.setValue(list.get(i).getId()+"");
			selectType.setText(list.get(i).getAppName());
			selectList.add(selectType);
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
				.fromObject(selectList);
		logger.info("初始化页面应用下拉框数据完成：" + jsonArray.toString());
		sendSuccessReslult(jsonArray.toString());
	}
	
	/**
	 * 初始化页面数据中心下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryDataCenterList() {
		logger.info("初始化页面数据中心下拉框数据开始");
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
		logger.info("初始化页面数据中心下拉框数据完成：" + jsonArray.toString());
		sendSuccessReslult(jsonArray.toString());
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
	 * 加载公共镜像
	 */
	public void loadPublicImages() {
		try {
			logger.info("加载公共镜像");
			jobService.loadPublicImages();
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("加载公共镜像异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
