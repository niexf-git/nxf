package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

public class FlowEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ID
	private String uuid;
	
	private String name;
	
	private String aliasName;
	
	private String appName;
	
	private long appId;
	
	private String parentId;
	
	private int roleType;
	
	private int deployType;
	
	private String imageName;
	
	private Date createTime;
	
	private String createBy;
	
	private int status;
	
	private String desc;
	
	private FlowRecordEntity flowRecord;

	/**
	 * 流水记录
	 * */
	private List<FlowRecordEntity> flowRecordList;

	/**
	 * 步骤
	 * */
	private List<StepEntity> stepList;
	
	/**
	 * 镜像
	 * */
	private PrivateImageEntity privateImage;
	
	private AppEntity app;
	
	/**
	 * 步骤记录
	 */
	private List<StepRecordEntity> stepRecords;
	
	public int getDeployType() {
		return deployType;
	}

	public void setDeployType(int deployType) {
		this.deployType = deployType;
	}

	public AppEntity getApp() {
		return app;
	}

	public void setApp(AppEntity app) {
		this.app = app;
	}
	
	public List<FlowRecordEntity> getFlowRecordList() {
		return flowRecordList;
	}

	public void setFlowRecordList(List<FlowRecordEntity> flowRecordList) {
		this.flowRecordList = flowRecordList;
	}

	public FlowRecordEntity getFlowRecord(){
		return flowRecord;
	}
	
	public PrivateImageEntity getPrivateImage() {
		return privateImage;
	}

	public void setPrivateImage(PrivateImageEntity privateImage) {
		this.privateImage = privateImage;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<StepEntity> getStepList() {
		return stepList;
	}

	public void setStepList(List<StepEntity> stepList) {
		this.stepList = stepList;
	}

	public void setFlowRecord(FlowRecordEntity flowRecord) {
		this.flowRecord = flowRecord;
	}

	public List<StepRecordEntity> getStepRecords() {
		return stepRecords;
	}

	public void setStepRecords(List<StepRecordEntity> stepRecords) {
		this.stepRecords = stepRecords;
	}

	@Override
	public String toString() {
		return "FlowEntity [uuid=" + uuid + ", name=" + name + ", aliasName="
				+ aliasName + ", appName=" + appName + ", appId=" + appId
				+ ", parentId=" + parentId + ", roleType=" + roleType
				+ ", deployType=" + deployType + ", imageName=" + imageName
				+ ", createTime=" + createTime + ", createBy=" + createBy
				+ ", status=" + status + ", desc=" + desc + ", flowRecord="
				+ flowRecord + ", flowRecordList=" + flowRecordList
				+ ", stepList=" + stepList + ", privateImage=" + privateImage
				+ ", app=" + app + ", stepRecords=" + stepRecords + "]";
	}

}
