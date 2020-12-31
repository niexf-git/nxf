package com.cmsz.paas.web.ipaasservice.model;

/**
 * 基础服务实体，用于创建和修改
 * 
 * @author liaohw
 * @date 2016-4-21
 */
public class IpaasServiceEntity {
	/** 唯一标识 */
	private String id;
	/** 基础服务名称 */
	private String name;
	/** 基础服务类型 */
	private String serviceType;
	/** 镜像id */
	private String imageId;
	/** 运行版本 */
	private String runningVersionId;
	/** cpu均值 */
	private String avgCpu;
	/** cpu峰值 */
	private String peakCpu;
	/** 内存均值 */
	private String avgMemory;
	/** 内存峰值 */
	private String peakMemory;
	/** 节点规模 */
	private String nodeNumber;
	/** 配置文件内容 */
	private String configFile;
	/** 描述 */
	private String description;
	/** 应用id,从session中取数据 */
	private String appId;
	/** 操作类型,从session中取数据 */
	private String operateType;
	/** 创建人,从session中取数据 */
	private String creator;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getRunningVersionId() {
		return runningVersionId;
	}

	public void setRunningVersionId(String runningVersionId) {
		this.runningVersionId = runningVersionId;
	}

	public String getAvgCpu() {
		return avgCpu;
	}

	public void setAvgCpu(String avgCpu) {
		this.avgCpu = avgCpu;
	}

	public String getPeakCpu() {
		return peakCpu;
	}

	public void setPeakCpu(String peakCpu) {
		this.peakCpu = peakCpu;
	}

	public String getAvgMemory() {
		return avgMemory;
	}

	public void setAvgMemory(String avgMemory) {
		this.avgMemory = avgMemory;
	}

	public String getPeakMemory() {
		return peakMemory;
	}

	public void setPeakMemory(String peakMemory) {
		this.peakMemory = peakMemory;
	}

	public String getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(String nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "IpaasServiceEntity [id=" + id + ", name=" + name
				+ ", serviceType=" + serviceType + ", imageId=" + imageId
				+ ", runningVersionId=" + runningVersionId + ", avgCpu="
				+ avgCpu + ", peakCpu=" + peakCpu + ", avgMemory=" + avgMemory
				+ ", peakMemory=" + peakMemory + ", nodeNumber=" + nodeNumber
				+ ", configFile=" + configFile + ", description=" + description
				+ ", appId=" + appId + ", operateType=" + operateType
				+ ", creator=" + creator + "]";
	}

}
