package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

public class FlowRecordEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ID
	private String uuid;
	private String flowId;
	private String aliasName;
	private long buildNum;
	private Date beginTime;
	private double runTime;
	private String tag;
	private String imageName;
	private int status;
	private String imageVersion;
	private String codeVersion;
	private String triggerBy;
	private String jobStatus;
	private long appId;
	private long imgId;
	private boolean isExecTime;
	
	
	private String successNum;//每天构建成功次数

	private String failedNum;//每天构建失败次数
	
	private String deployNum;//部署次数
	
	 public String getDeployNum() {
		return deployNum;
	}

	public void setDeployNum(String deployNum) {
		this.deployNum = deployNum;
	}

	public String getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(String successNum) {
		this.successNum = successNum;
	}

	public String getFailedNum() {
		return failedNum;
	}

	public void setFailedNum(String failedNum) {
		this.failedNum = failedNum;
	}

	/**
	   * 步骤记录
	   * */
	private List<StepRecordEntity> stepRecords;
	
	private PrivateImageEntity privateImage;
	
	/**
	 * 上一次构建时间
	 */
	private Date LastBeginTime;

	public boolean isExecTime() {
		return isExecTime;
	}

	public void setExecTime(boolean isExecTime) {
		this.isExecTime = isExecTime;
	}

	public PrivateImageEntity getPrivateImage() {
		return privateImage;
	}

	public void setPrivateImage(PrivateImageEntity privateImage) {
		this.privateImage = privateImage;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}
	
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public long getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(long buildNum) {
		this.buildNum = buildNum;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public double getRunTime() {
		return runTime;
	}

	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCodeVersion() {
		return codeVersion;
	}

	public void setCodeVersion(String codeVersion) {
		this.codeVersion = codeVersion;
	}

	public String getTriggerBy() {
		return triggerBy;
	}

	public void setTriggerBy(String triggerBy) {
		this.triggerBy = triggerBy;
	}

	public List<StepRecordEntity> getStepRecords() {
		return stepRecords;
	}

	public void setStepRecords(List<StepRecordEntity> stepRecords) {
		this.stepRecords = stepRecords;
	}

	public Date getLastBeginTime() {
		return LastBeginTime;
	}

	public void setLastBeginTime(Date lastBeginTime) {
		LastBeginTime = lastBeginTime;
	}

	
	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getImgId() {
		return imgId;
	}

	public void setImgId(long imgId) {
		this.imgId = imgId;
	}

	@Override
	public String toString() {
		return "FlowRecordEntity [uuid=" + uuid + ", flowId=" + flowId
				+ ", aliasName=" + aliasName + ", buildNum=" + buildNum
				+ ", beginTime=" + beginTime + ", runTime=" + runTime
				+ ", tag=" + tag + ", imageName=" + imageName + ", status="
				+ status + ", imageVersion=" + imageVersion + ", codeVersion="
				+ codeVersion + ", triggerBy=" + triggerBy + ", jobStatus="
				+ jobStatus + ", appId=" + appId + ", imgId=" + imgId
				+ ", stepRecords=" + stepRecords + ", privateImage="
				+ privateImage + ", LastBeginTime=" + LastBeginTime + "]";
	}
}
