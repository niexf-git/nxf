/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppServiceEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class AppServiceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ID
	private String id;
	
	private String name;
	
	private long appId;
	
	private String flowId;
	
	/**
	 * 应用名
	 */
	private String appName;
	
	private int status;
	
	private int grayVersionStatus;
	
	/**
	 * 部署类型
	 */
	private int deployMode;
	
	/**
	 * 是否创建灰度 (false:否,true：是)
	 */
	private boolean isGrayVer;
	
	private int operateType;
	
	private long clusterId;
	
	private double cpu;

	private int mem;

	private double peakCpu;

	private int peakMem;

	private int totalInstanceNum;
	
	private int grayInstanceNum;
	
	/**
	 * 当前正在运行的实例数
	 */
	private int runningInstanceNum;
	
	private Date createTime;

	private String createBy;

	private String description;
	
	private int configEffect;
	
	/**
	 * 是否存在灰度版本
	 */
	private int existGray;
	
	/**
	 * 服务部署的集群
	 */
	private ClusterEntity cluster;
	
	/**
	 * 服务对应的所有实例
	 */
	private List<Instance> instances;
	
	/**
	 * 外部服务
	 */
	private K8sServiceEntity k8sService;

	/**
	 * 弹性伸缩
	 */
	private ScalerEntity scaler;

	/**
	 * 关联的基础服务
	 */
	private List<IpaasToAppServiceEntity> ipaases;

	/**
	 * 存在的版本 
	 */
	private List<AppServiceVersionEntity> versions;
	
	/**
	 * 关联的数据中心
	 */
	private DataCenterEntity dataCenterEntity;
	
	//记录ip
	private String ip;
		
	//记录模式
	private int model;
	
	@Override
	public String toString() {
		return "AppServiceEntity [id=" + id + ", name=" + name + ", appId=" + appId + ", flowId=" + flowId
				+ ", appName=" + appName + ", status=" + status + ", grayVersionStatus=" + grayVersionStatus
				+ ", deployMode=" + deployMode + ", isGrayVer=" + isGrayVer + ", operateType=" + operateType
				+ ", clusterId=" + clusterId + ", cpu=" + cpu + ", mem=" + mem + ", peakCpu=" + peakCpu + ", peakMem="
				+ peakMem + ", totalInstanceNum=" + totalInstanceNum + ", grayInstanceNum=" + grayInstanceNum
				+ ", runningInstanceNum=" + runningInstanceNum + ", createTime=" + createTime + ", createBy=" + createBy
				+ ", description=" + description + ", configEffect=" + configEffect + ", existGray=" + existGray
				+ ", cluster=" + cluster + ", instances=" + instances + ", k8sService=" + k8sService + ", scaler="
				+ scaler + ", ipaases=" + ipaases + ", versions=" + versions + ", dataCenterEntity=" + dataCenterEntity
				+ ", ip=" + ip + ", model=" + model + "]";
	}

	public boolean getIsGrayVer() {
		return isGrayVer;
	}

	public void setIsGrayVer(boolean isGrayVer) {
		this.isGrayVer = isGrayVer;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public int getDeployMode() {
		return deployMode;
	}

	public void setDeployMode(int deployMode) {
		this.deployMode = deployMode;
	}

	public DataCenterEntity getDataCenterEntity() {
		return dataCenterEntity;
	}



	public void setDataCenterEntity(DataCenterEntity dataCenterEntity) {
		this.dataCenterEntity = dataCenterEntity;
	}



	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}
	
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

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getGrayVersionStatus() {
		return grayVersionStatus;
	}

	public void setGrayVersionStatus(int grayVersionStatus) {
		this.grayVersionStatus = grayVersionStatus;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public long getClusterId() {
		return clusterId;
	}

	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	public double getPeakCpu() {
		return peakCpu;
	}

	public void setPeakCpu(double peakCpu) {
		this.peakCpu = peakCpu;
	}

	public int getPeakMem() {
		return peakMem;
	}

	public void setPeakMem(int peakMem) {
		this.peakMem = peakMem;
	}

	public int getTotalInstanceNum() {
		return totalInstanceNum;
	}

	public void setTotalInstanceNum(int totalInstanceNum) {
		this.totalInstanceNum = totalInstanceNum;
	}

	public int getGrayInstanceNum() {
		return grayInstanceNum;
	}

	public void setGrayInstanceNum(int grayInstanceNum) {
		this.grayInstanceNum = grayInstanceNum;
	}

	public int getRunningInstanceNum() {
		return runningInstanceNum;
	}

	public void setRunningInstanceNum(int runningInstanceNum) {
		this.runningInstanceNum = runningInstanceNum;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getConfigEffect() {
		return configEffect;
	}

	public void setConfigEffect(int configEffect) {
		this.configEffect = configEffect;
	}

	public ClusterEntity getCluster() {
		return cluster;
	}

	public void setCluster(ClusterEntity cluster) {
		this.cluster = cluster;
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public K8sServiceEntity getK8sService() {
		return k8sService;
	}

	public void setK8sService(K8sServiceEntity k8sService) {
		this.k8sService = k8sService;
	}

	public ScalerEntity getScaler() {
		return scaler;
	}

	public void setScaler(ScalerEntity scaler) {
		this.scaler = scaler;
	}

	public List<IpaasToAppServiceEntity> getIpaases() {
		return ipaases;
	}

	public void setIpaases(List<IpaasToAppServiceEntity> ipaases) {
		this.ipaases = ipaases;
	}

	public List<AppServiceVersionEntity> getVersions() {
		return versions;
	}

	public void setVersions(List<AppServiceVersionEntity> versions) {
		this.versions = versions;
	}

	public int getExistGray() {
		return existGray;
	}

	public void setExistGray(int existGray) {
		this.existGray = existGray;
	}
	
}
