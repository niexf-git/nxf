/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildEntity.java
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
public class BuildEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long appId;
	
	/**
	 * 应用名
	 */
	private String appName;
	
	private String name;

	private int type;

	private String dockerfilePath;

	private String description;

	private String createBy;

	private Date createTime;
	
	/**
	 * 构建生成的私有镜像ID
	 */
	private long privateImageId;
	
	/**
	 * 构建生成的私有镜像名称
	 */
	private String privateImageName;
	
	/**
	 * 总构建次数
	 */
	private int buildNum;
	
	/**
	 * 最近一次构建的开始时间
	 */
	private Date latestBuildStartTime;
	
	/**
	 * 最近一次构建的结束时间
	 */
	private Date latestBuildEndTime;
	
	/**
	 * 最近一次构建的状态
	 */
	private int latestBuildStatus;

	private List<CodePathEntity> codes;

	private List<BuildStepEntity> buildSteps;

	@Override
	public String toString() {
		return "BuildEntity "
				+ "[id=" + id 
				+ ", appId=" + appId 
				+ ", name=" + name
				+ ", appName=" + appName 
				+ ", type="+type
				+ ", dockerfilePath=" + dockerfilePath 
				+ ", description=" + description 
				+ ", createBy=" + createBy 
				+ ", createTime=" + createTime 
				+ ", privateImageId=" + privateImageId 
				+ ", privateImageName=" + privateImageName 
				+ ", buildNum " + buildNum
				+ " , latestBuildStartTime =" +latestBuildStartTime
				+ ", latestBuildEndTime = " +latestBuildEndTime
				+ ", latestBuildStatus=" + latestBuildStatus
				+ ", codes=" + codes 
				+ ", buildSteps=" + buildSteps + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDockerfilePath() {
		return dockerfilePath;
	}

	public void setDockerfilePath(String dockerfilePath) {
		this.dockerfilePath = dockerfilePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getPrivateImageId() {
		return privateImageId;
	}

	public void setPrivateImageId(long privateImageId) {
		this.privateImageId = privateImageId;
	}

	public String getPrivateImageName() {
		return privateImageName;
	}

	public void setPrivateImageName(String privateImageName) {
		this.privateImageName = privateImageName;
	}

	public int getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(int buildNum) {
		this.buildNum = buildNum;
	}

	public Date getLatestBuildStartTime() {
		return latestBuildStartTime;
	}

	public void setLatestBuildStartTime(Date latestBuildStartTime) {
		this.latestBuildStartTime = latestBuildStartTime;
	}

	public Date getLatestBuildEndTime() {
		return latestBuildEndTime;
	}

	public void setLatestBuildEndTime(Date latestBuildEndTime) {
		this.latestBuildEndTime = latestBuildEndTime;
	}

	public int getLatestBuildStatus() {
		return latestBuildStatus;
	}

	public void setLatestBuildStatus(int latestBuildStatus) {
		this.latestBuildStatus = latestBuildStatus;
	}

	public List<CodePathEntity> getCodes() {
		return codes;
	}

	public void setCodes(List<CodePathEntity> codes) {
		this.codes = codes;
	}

	public List<BuildStepEntity> getBuildSteps() {
		return buildSteps;
	}

	public void setBuildSteps(List<BuildStepEntity> buildSteps) {
		this.buildSteps = buildSteps;
	}
}
