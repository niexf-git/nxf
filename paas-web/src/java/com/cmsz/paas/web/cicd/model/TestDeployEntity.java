package com.cmsz.paas.web.cicd.model;

/**
 * 测试部署实体
 * 
 * @author ccl
 * @date 2017-8-25
 */
public class TestDeployEntity {
	
	/** 服务id */
	private String serviceID;
	/** 发布的服务名 */
	private String appSvcName;
	/** 部署方式 */
	private String depType;
	
	/** 灰度实例数量 */
	private String grayNum;
	/** 卡片公共属性实体 */
	private StepDetailInfo stepDetailInfo;
	
	/** 应用服务，运行版本 */
	private String runVersion;
	
	/** 应用服务，运行版本Id */
	private String runVersionId;
	
	/** 应用服务状态 运行/停止 */
	private String state;
	
	/** 实例数 */
	private String instanceNum;

	
	
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

	public String getGrayNum() {
		return grayNum;
	}

	public void setGrayNum(String grayNum) {
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

	public String getAppSvcName() {
		return appSvcName;
	}

	public void setAppSvcName(String appSvcName) {
		this.appSvcName = appSvcName;
	}

	public String getDepType() {
		return depType;
	}

	public void setDepType(String depType) {
		this.depType = depType;
	}



}
