/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.orm.id.ID;

public class AppEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	private String flowId;
	private String name;
	
	private String namespace;
	
	private String description;
	
	private Date createTime;

	private String createBy;

	private String harborUser;

	private String harborPwd;

	private long harborProjectId;
	
	/**
	 * 关联的容灾配置
	 */
	private List<DisasterToleranceConfEntity> disasterToleranceConfs;
	
	/**
	 * 关联的集群
	 */
	private List<ClusterToAppEntity> clusters;
	
	/**
	 * 关联的harbor策略
	 */
	private List<HarborPolicyEntity> policys;
	
	/**
	 * 关联的迁移记录
	 */
	private List<MigrationRecordEntity> migrationRecords;

	@Override
	public String toString() {
		return "AppEntity [id=" + id + ", flowId=" + flowId + ", name=" + name
				+ ", namespace=" + namespace + ", description=" + description
				+ ", createTime=" + createTime + ", createBy=" + createBy
				+ ", harborUser=" + harborUser + ", harborPwd=" + harborPwd
				+ ", harborProjectId=" + harborProjectId
				+ ", disasterToleranceConfs=" + disasterToleranceConfs
				+ ", clusters=" + clusters + ", policys=" + policys
				+ ", migrationRecords=" + migrationRecords + "]";
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getHarborUser() {
		return harborUser;
	}

	public void setHarborUser(String harborUser) {
		this.harborUser = harborUser;
	}

	public String getHarborPwd() {
		return harborPwd;
	}

	public void setHarborPwd(String harborPwd) {
		this.harborPwd = harborPwd;
	}

	public long getHarborProjectId() {
		return harborProjectId;
	}

	public void setHarborProjectId(long harborProjectId) {
		this.harborProjectId = harborProjectId;
	}

	public List<ClusterToAppEntity> getClusters() {
		return clusters;
	}

	public void setClusters(List<ClusterToAppEntity> clusters) {
		this.clusters = clusters;
	}

	public List<HarborPolicyEntity> getPolicys() {
		return policys;
	}

	public void setPolicys(List<HarborPolicyEntity> policys) {
		this.policys = policys;
	}
	

	public List<DisasterToleranceConfEntity> getDisasterToleranceConfs() {
		return disasterToleranceConfs;
	}

	public void setDisasterToleranceConfs(
			List<DisasterToleranceConfEntity> disasterToleranceConfs) {
		this.disasterToleranceConfs = disasterToleranceConfs;
	}

	public List<MigrationRecordEntity> getMigrationRecords() {
		return migrationRecords;
	}

	public void setMigrationRecords(List<MigrationRecordEntity> migrationRecords) {
		this.migrationRecords = migrationRecords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((harborPwd == null) ? 0 : harborPwd.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AppEntity) {   
			AppEntity app = (AppEntity) obj;   
            return this.name.equals(app.getName());   
        }   
        return super.equals(obj);  
	}
	
	
}
