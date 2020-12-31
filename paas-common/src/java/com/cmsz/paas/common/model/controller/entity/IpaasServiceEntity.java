/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File IpaasServiceEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-24
 */
public class IpaasServiceEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private String id;

	private String name;

	private long appId;

	private String appName;

	private long publicImageId;

	private long runningVersion;

	private long clusterId;

	private int configEffect;

	private int status;

	private int operateType;

	private int serviceType;

	private double cpu;

	private int mem;

	private double peakCpu;

	private int peakMem;

	private int nodeNum;
	
	/**
	 * activemq user
	 */
	private String user;
	
	/**
	 * activemq pwd
	 */
	private String pwd;

	/**
	 * 运行的实例
	 */
	private int runningNode;

	/**
	 * 配置文件内容
	 */
	private String config;

	private Date createTime;

	private String createBy;

	private String description;
	
	private String path;

	/**
	 * 外部服务接口列表
	 */
	private List<K8sServiceEntity> k8sServiceList;

	/**
	 * 使用的公共镜像
	 */
	private PublicImageEntity publicImage;

	/**
	 * 关联的应用服务
	 */
	private List<IpaasToAppServiceEntity> appServices;
	
	/**
	 * 关联的数据中心
	 */
	private DataCenterEntity dataCenterEntity;

	/**
	 * 发布的环境变量
	 */
	private List<PublishEnvEntity> publishEnvsList;

	/**
	 * 运行实例
	 */
	private List<Instance> insts;

	/**
	 * 所属集群
	 */
	private ClusterEntity cluster;

	@Override
	public String toString() {
		return "IpaasServiceEntity [id=" + id + ", name=" + name + ", appId="
				+ appId + ", appName=" + appName + ", publicImageId="
				+ publicImageId + ", runningVersion=" + runningVersion
				+ ", clusterId=" + clusterId + ", configEffect=" + configEffect
				+ ", status=" + status + ", operateType=" + operateType
				+ ", serviceType=" + serviceType + ", cpu=" + cpu + ", mem="
				+ mem + ", peakCpu=" + peakCpu + ", peakMem=" + peakMem
				+ ", nodeNum=" + nodeNum + ", user=" + user + ", pwd=" + pwd
				+ ", runningNode=" + runningNode + ", config=" + config
				+ ", createTime=" + createTime + ", createBy=" + createBy
				+ ", description=" + description + ", path=" + path
				+ ", k8sServiceList=" + k8sServiceList + ", publicImage="
				+ publicImage + ", appServices=" + appServices
				+ ", dataCenterEntity=" + dataCenterEntity
				+ ", publishEnvsList=" + publishEnvsList + ", insts=" + insts
				+ ", cluster=" + cluster + "]";
	}


	public DataCenterEntity getDataCenterEntity() {
		return dataCenterEntity;
	}


	public void setDataCenterEntity(DataCenterEntity dataCenterEntity) {
		this.dataCenterEntity = dataCenterEntity;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public long getPublicImageId() {
		return publicImageId;
	}

	public void setPublicImageId(long publicImageId) {
		this.publicImageId = publicImageId;
	}

	public long getRunningVersion() {
		return runningVersion;
	}

	public void setRunningVersion(long runningVersion) {
		this.runningVersion = runningVersion;
	}

	public long getClusterId() {
		return clusterId;
	}

	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}

	public int getConfigEffect() {
		return configEffect;
	}

	public void setConfigEffect(int configEffect) {
		this.configEffect = configEffect;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
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

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public int getRunningNode() {
		return runningNode;
	}

	public void setRunningNode(int runningNode) {
		this.runningNode = runningNode;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
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

	public List<K8sServiceEntity> getK8sServiceList() {
		return k8sServiceList;
	}

	public void setK8sServiceList(List<K8sServiceEntity> k8sServiceList) {
		this.k8sServiceList = k8sServiceList;
	}

	public PublicImageEntity getPublicImage() {
		return publicImage;
	}

	public void setPublicImage(PublicImageEntity publicImage) {
		this.publicImage = publicImage;
	}

	public List<IpaasToAppServiceEntity> getAppServices() {
		return appServices;
	}

	public void setAppServices(List<IpaasToAppServiceEntity> appServices) {
		this.appServices = appServices;
	}

	public List<PublishEnvEntity> getPublishEnvsList() {
		return publishEnvsList;
	}

	public void setPublishEnvsList(List<PublishEnvEntity> publishEnvsList) {
		this.publishEnvsList = publishEnvsList;
	}

	public List<Instance> getInsts() {
		return insts;
	}

	public void setInsts(List<Instance> insts) {
		this.insts = insts;
	}

	public ClusterEntity getCluster() {
		return cluster;
	}

	public void setCluster(ClusterEntity cluster) {
		this.cluster = cluster;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
