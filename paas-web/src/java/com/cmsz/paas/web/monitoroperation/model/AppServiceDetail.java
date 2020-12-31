package com.cmsz.paas.web.monitoroperation.model;


import java.util.List;

import com.cmsz.paas.web.appserviceinst.model.Instance;



public class AppServiceDetail {
	/** 所属服务id */
	private String id;
	/** 所属应用id */
	private String appId;
	/** 所属应用名称 */
	private String appName;

	/** 服务名称 */
	private String name;
	/** 应用服务状态 */
	private String status;
	/** 操作类型1-开发，2-测试，3-运维 */
	private String operateType;

	/** 部署集群 */
	private String clusterId;
	/** 实例数目 */
	private String instanceNum;
	/** 正在运行的实例数 */
	private String runningInstNum;
	/** 创建时间 */
	private String createTime;
	/** 创建人 */
	private String createBy;
	/** 描述 */
	private String description;
	/**
	 * 实例伸缩类型，固定-1，动态-2
	 */
	private String inst_scale_type;

	/**
	 * cpu目标值
	 */
	private String cpu_target;

	/**
	 * 最小实例数
	 */
	private String inst_min;

	/**
	 * 最大实例数
	 */
	private String inst_max;
	
	private int existGray;
	public int getExistGray() {
		return existGray;
	}

	public void setExistGray(int existGray) {
		this.existGray = existGray;
	}

	public String getInst_scale_type() {
		return inst_scale_type;
	}

	public void setInst_scale_type(String inst_scale_type) {
		this.inst_scale_type = inst_scale_type;
	}

	public String getCpu_target() {
		return cpu_target;
	}

	public void setCpu_target(String cpu_target) {
		this.cpu_target = cpu_target;
	}

	public String getInst_min() {
		return inst_min;
	}

	public void setInst_min(String inst_min) {
		this.inst_min = inst_min;
	}

	public String getInst_max() {
		return inst_max;
	}

	public void setInst_max(String inst_max) {
		this.inst_max = inst_max;
	}

	/** 实例集合 */
	private List<Instance> insts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(String instanceNum) {
		this.instanceNum = instanceNum;
	}

	public String getRunningInstNum() {
		return runningInstNum;
	}

	public void setRunningInstNum(String runningInstNum) {
		this.runningInstNum = runningInstNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Instance> getInsts() {
		return insts;
	}

	public void setInsts(List<Instance> insts) {
		this.insts = insts;
	}

	@Override
	public String toString() {
		return "AppServiceEntity [id=" + id + ", appId=" + appId + ", appName="
				+ appName + ", name=" + name + ", status=" + status
				+ ", operateType=" + operateType + ", imageType="
				+ ", clusterId=" + clusterId + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", description=" + description
				+ "]";
	}

}
