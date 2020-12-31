package com.cmsz.paas.web.monitoroperation.model;

/**
 * 
 * @author lin.my
 * @version 创建时间：2017年1月12日 下午4:16:45
 */
public class ImageSyncQueryConditions {

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

}
