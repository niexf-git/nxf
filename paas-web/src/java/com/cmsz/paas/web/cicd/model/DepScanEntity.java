package com.cmsz.paas.web.cicd.model;


/**
 * 开发部署扫描实体
 * 
 * @author ccl
 * @date 2017-8-25
 */
public class DepScanEntity {
	/** 服务id */
	private String serviceID;
	/** 服务名 */
	private String appSvcName;
	/** 是否安全检查 */
	private String isCheck;
	/** 部署类型(1.普通发布,2.灰度发布) */
	private String type;
	/** web服务地址 */
	private String webUrl;
	/** 灰度实例数量 */
	private int grayNum;
	/** 实例总数 */
	private int totalNum;
	/** 卡片公共属性实体 */
	private StepDetailInfo stepDetailInfo;
    /** 是否可以选择灰度 true:是 false:否*/
	private boolean isGray;
	
	/** 灰度选择项提示信息*/
	private String grayInfo;
	
	/** 应用服务，运行版本 */
	private String runVersion;
	
	/** 应用服务，运行版本Id */
	private String runVersionId;
	
	/** 应用服务状态 运行/停止 */
	private String state;
	
	/** 实例数 */
	private String instanceNum;
	
	
	public int getGrayNum() {
		return grayNum;
	}

	public void setGrayNum(int grayNum) {
		this.grayNum = grayNum;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public StepDetailInfo getStepDetailInfo() {
		return stepDetailInfo;
	}

	public void setStepDetailInfo(StepDetailInfo stepDetailInfo) {
		this.stepDetailInfo = stepDetailInfo;
	}



	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getAppSvcName() {
		return appSvcName;
	}

	public void setAppSvcName(String appSvcName) {
		this.appSvcName = appSvcName;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isGray() {
		return isGray;
	}

	public void setGray(boolean isGray) {
		this.isGray = isGray;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getGrayInfo() {
		return grayInfo;
	}

	public void setGrayInfo(String grayInfo) {
		this.grayInfo = grayInfo;
	}

	public String getRunVersion() {
		return runVersion;
	}

	public void setRunVersion(String runVersion) {
		this.runVersion = runVersion;
	}

	public String getRunVersionId() {
		return runVersionId;
	}

	public void setRunVersionId(String runVersionId) {
		this.runVersionId = runVersionId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(String instanceNum) {
		this.instanceNum = instanceNum;
	}
	
	
}
