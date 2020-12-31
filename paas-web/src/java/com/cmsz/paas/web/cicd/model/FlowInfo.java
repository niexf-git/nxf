package com.cmsz.paas.web.cicd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiayz
 * @time 2017-8-29
 */
public class FlowInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String appId;// 应用ID
	private String flowId;// id
	private String flowName; // 流水名
	private String roleType;
	private String operType; // 操作类型
	private String appName; // 应用名
	private String updateTime;// 修改时间
	private String buildStatus;// 构建状态
	private String createBy; // 创建人
	private String flowDescription; // 流水描述
	private String token; // 模糊查询
	private long buildCount;// 构建次数
	private String imageName;// 镜像名称
	private String createTime;// 创建时间
	private String preBuildTime;// 上次构建时间
	private int imageId;// 镜像ID
	private double runTime;// 上次构建总时长
	// 引用步骤实体
	private List<StepInfo> stepList;

	private String flowRecordId;// 流水步骤id

	private int deployType;// 部署类型

	public double getRunTime() {
		return runTime;
	}

	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

	public int getDeployType() {
		return deployType;
	}

	public void setDeployType(int deployType) {
		this.deployType = deployType;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public long getBuildCount() {
		return buildCount;
	}

	public void setBuildCount(long buildCount) {
		this.buildCount = buildCount;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPreBuildTime() {
		return preBuildTime;
	}

	public void setPreBuildTime(String preBuildTime) {
		this.preBuildTime = preBuildTime;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getFlowDescription() {
		return flowDescription;
	}

	public void setFlowDescription(String flowDescription) {
		this.flowDescription = flowDescription;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<StepInfo> getStepList() {
		return stepList;
	}

	public void setStepList(List<StepInfo> stepList) {
		this.stepList = stepList;
	}

	public String getFlowRecordId() {
		return flowRecordId;
	}

	public void setFlowRecordId(String flowRecordId) {
		this.flowRecordId = flowRecordId;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	@Override
	public String toString() {
		return "FlowInfo [appId=" + appId + ", flowId=" + flowId
				+ ", flowName=" + flowName + ", roleType=" + roleType
				+ ", operType=" + operType + ", appName=" + appName
				+ ", updateTime=" + updateTime + ", buildStatus=" + buildStatus
				+ ", createBy=" + createBy + ", flowDescription="
				+ flowDescription + ", token=" + token + ", buildCount="
				+ buildCount + ", imageName=" + imageName + ", createTime="
				+ createTime + ", preBuildTime=" + preBuildTime + ", imageId="
				+ imageId + ", runTime=" + runTime + ", stepList=" + stepList
				+ ", flowRecordId=" + flowRecordId + ", deployType="
				+ deployType + "]";
	}

}
