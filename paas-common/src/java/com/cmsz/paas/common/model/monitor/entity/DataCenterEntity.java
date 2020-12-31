/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File DataCenterEntity.java
 */
package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.ClusterEntity;
import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class DataCenterEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String name;
	
	private int isMain;
	
	private String ipaasHaproxyUrl;
	
	private String appHaproxyUrl;
	
	private String harborUrl;
	
	private String registryUrl;
	
	private String harborUser;
	
	private String harborPwd;
	
	private long harborTargetId;
	
	private String description;
	
	private Date insertTime;
	
	private String flumeIp;

	/**
	 * 数据中心下的集群
	 */
	private List<ClusterEntity> clusters;

	@Override
	public String toString() {
		return "DataCenterEntity "
				+ "[id=" + id 
				+ ", name=" + name
				+ ", isMain=" + isMain
				+ ", ipaasHaproxyUrl=" + ipaasHaproxyUrl
				+ ", appHaproxyUrl=" + appHaproxyUrl
				+ ", harborUrl=" + harborUrl
				+ ", registryUrl=" + registryUrl
				+ ", harborUser=" + harborUser
				+ ", harborPwd=" + harborPwd
				+ ", harborTargetId=" + harborTargetId
				+ ", description=" + description
				+ ", clusters=" + clusters 
				+ ", flumeIp=" + flumeIp 
				+ "]";
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

	public int getIsMain() {
		return isMain;
	}

	public void setIsMain(int isMain) {
		this.isMain = isMain;
	}

	public String getIpaasHaproxyUrl() {
		return ipaasHaproxyUrl;
	}

	public void setIpaasHaproxyUrl(String ipaasHaproxyUrl) {
		this.ipaasHaproxyUrl = ipaasHaproxyUrl;
	}

	public String getAppHaproxyUrl() {
		return appHaproxyUrl;
	}

	public void setAppHaproxyUrl(String appHaproxyUrl) {
		this.appHaproxyUrl = appHaproxyUrl;
	}

	public String getHarborUrl() {
		return harborUrl;
	}

	public void setHarborUrl(String harborUrl) {
		this.harborUrl = harborUrl;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
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

	public long getHarborTargetId() {
		return harborTargetId;
	}

	public void setHarborTargetId(long harborTargetId) {
		this.harborTargetId = harborTargetId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ClusterEntity> getClusters() {
		return clusters;
	}

	public void setClusters(List<ClusterEntity> clusters) {
		this.clusters = clusters;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public String getFlumeIp() {
		return flumeIp;
	}

	public void setFlumeIp(String flumeIp) {
		this.flumeIp = flumeIp;
	}
}
