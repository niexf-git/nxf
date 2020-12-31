package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class DepScanEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String stepId; //步骤Id
	
	private String stepName;//步骤名
	
	private String appSvcName;//服务名
	
	private String appSvcId;//服务id
	
	private String serviceRunVersion;//服务运行版本
	
	private long serviceRunVersionId;//服务运行版本id
	
	private int serviceState;//服务状态
	
	private int serviceInstanceNum;//服务实例数
	
	private String describe;//描述
	
	private int isCheck;//是否安全检查
	
	private int type;//部署类型
	
	private String scanUrl;//web服务地址
	
	private int status; //状态，空-未执行，0-成功。1-未成功
	
	private int instanceNum;//灰度实例数
	
	private int totalInstanceNum;//总实例数
	
	private double time; //时间,秒
	
	private int isChoise; //是否选中
	
	/**
	 * 是否创建灰度 (false:否,true：是)
	 */
	private boolean isGrayVer;
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public void setGrayVer(boolean isGrayVer) {
		this.isGrayVer = isGrayVer;
	}

	public int getTotalInstanceNum() {
		return totalInstanceNum;
	}

	public void setTotalInstanceNum(int totalInstanceNum) {
		this.totalInstanceNum = totalInstanceNum;
	}

	public boolean getIsGrayVer(){
		return isGrayVer;
	}
	
	public void setIsGrayVer(boolean isGrayVer) {
		this.isGrayVer = isGrayVer;
	}
	public String getAppSvcId() {
		return appSvcId;
	}
	public void setAppSvcId(String appSvcId) {
		this.appSvcId = appSvcId;
	}
	public int getInstanceNum() {
		return instanceNum;
	}
	public void setInstanceNum(int instanceNum) {
		this.instanceNum = instanceNum;
	}
	public int getIsChoise() {
		return isChoise;
	}
	public void setIsChoise(int isChoise) {
		this.isChoise = isChoise;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getAppSvcName() {
		return appSvcName;
	}
	public void setAppSvcName(String appSvcName) {
		this.appSvcName = appSvcName;
	}
	public int getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getScanUrl() {
		return scanUrl;
	}
	public void setScanUrl(String scanUrl) {
		this.scanUrl = scanUrl;
	}

	public String getServiceRunVersion() {
		return serviceRunVersion;
	}

	public void setServiceRunVersion(String serviceRunVersion) {
		this.serviceRunVersion = serviceRunVersion;
	}

	public long getServiceRunVersionId() {
		return serviceRunVersionId;
	}

	public void setServiceRunVersionId(long serviceRunVersionId) {
		this.serviceRunVersionId = serviceRunVersionId;
	}

	public int getServiceState() {
		return serviceState;
	}

	public void setServiceState(int serviceState) {
		this.serviceState = serviceState;
	}

	public int getServiceInstanceNum() {
		return serviceInstanceNum;
	}

	public void setServiceInstanceNum(int serviceInstanceNum) {
		this.serviceInstanceNum = serviceInstanceNum;
	}

	@Override
	public String toString() {
		return "DepScanEntity [stepId=" + stepId + ", stepName=" + stepName
				+ ", appSvcName=" + appSvcName + ", appSvcId=" + appSvcId
				+ ", serviceRunVersion=" + serviceRunVersion
				+ ", serviceRunVersionId=" + serviceRunVersionId
				+ ", serviceState=" + serviceState + ", serviceInstanceNum="
				+ serviceInstanceNum + ", describe=" + describe + ", isCheck="
				+ isCheck + ", type=" + type + ", scanUrl=" + scanUrl
				+ ", status=" + status + ", instanceNum=" + instanceNum
				+ ", totalInstanceNum=" + totalInstanceNum + ", time=" + time
				+ ", isChoise=" + isChoise + ", isGrayVer=" + isGrayVer + "]";
	}
	
}
