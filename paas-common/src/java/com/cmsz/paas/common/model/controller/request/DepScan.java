package com.cmsz.paas.common.model.controller.request;

import java.io.Serializable;

/**
 * 
 * @author guyj
 * @time 2017-8-24
 * 
 */
public class DepScan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appSvcName; // 服务名
	private String appSvcId; // 服务id
	private String stepId; // 步骤Id
	private String stepName;// 步骤名
	private int instanceNum;// 灰度实例数
	private int totalInstanceNum;//总实例数
	private int type; // 部署类型(1.普通发布,2.灰度发布)
	private String scanUrl; // 扫描服务地址
	private int isCheck; // 是否安全检查
	private int isChoise; // 是否选中
	private int status; // 状态，空-未执行，0-成功。1-未成功
	private double time; // 时间,秒
	private String describe;//描述
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getTotalInstanceNum() {
		return totalInstanceNum;
	}

	public void setTotalInstanceNum(int totalInstanceNum) {
		this.totalInstanceNum = totalInstanceNum;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public int getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(int instanceNum) {
		this.instanceNum = instanceNum;
	}

	public String getAppSvcName() {
		return appSvcName;
	}

	public void setAppSvcName(String appSvcName) {
		this.appSvcName = appSvcName;
	}

	public String getAppSvcId() {
		return appSvcId;
	}

	public void setAppSvcId(String appSvcId) {
		this.appSvcId = appSvcId;
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

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public int getIsChoise() {
		return isChoise;
	}

	public void setIsChoise(int isChoise) {
		this.isChoise = isChoise;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "DepScan [appSvcName=" + appSvcName + ", appSvcId=" + appSvcId
				+ ", stepId=" + stepId + ", stepName=" + stepName
				+ ", instanceNum=" + instanceNum + ", totalInstanceNum="
				+ totalInstanceNum + ", type=" + type + ", scanUrl=" + scanUrl
				+ ", isCheck=" + isCheck + ", isChoise=" + isChoise
				+ ", status=" + status + ", time=" + time + ", describe="
				+ describe + "]";
	}
}
