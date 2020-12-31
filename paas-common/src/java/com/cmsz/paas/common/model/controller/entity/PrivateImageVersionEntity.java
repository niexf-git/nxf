/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppImageVersion.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class PrivateImageVersionEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long privateImageId;

	private String buildRecordId;

	private String tag;

	private String version;

	private int status;

	private String description;

	private String createBy;

	private Date createTime;
	
	private String logDir;
	
	private String configDir;
	
	private String imgName;
	
	private int loadStatus;
	
	/**
	 * 镜像URL
	 */
	private String url;

	@Override
	public String toString() {
		return "PrivateImageVersionEntity "
				+ "[id=" + id 
				+ ", privateImageId=" + privateImageId 
				+ ", buildRecordId=" + buildRecordId 
				+ ", tag=" + tag 
				+ ", version=" + version 
				+ ", status=" + status 
				+ ", description=" + description
				+ ", createBy=" + createBy 
				+ ", createTime=" + createTime 
				+ ",logDir="+logDir
				+ ",configDir="+configDir
				+ ",url="+url +"]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imgName == null) ? 0 : imgName.hashCode())+
				 prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		PrivateImageVersionEntity other = (PrivateImageVersionEntity) obj;
	    
		if(other.getImgName() == null || other.getTag() == null)
	    	return false;
		if(imgName ==null || tag == null)
			return false;
	
		return ( imgName.equals(other.imgName) && 
				 tag.equals(other.tag));
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPrivateImageId() {
		return privateImageId;
	}

	public void setPrivateImageId(long privateImageId) {
		this.privateImageId = privateImageId;
	}

	public String getBuildRecordId() {
		return buildRecordId;
	}

	public void setBuildRecordId(String buildRecordId) {
		this.buildRecordId = buildRecordId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getConfigDir() {
		return configDir;
	}

	public void setConfigDir(String configDir) {
		this.configDir = configDir;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public int getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(int loadStatus) {
		this.loadStatus = loadStatus;
	}

	
	
}
