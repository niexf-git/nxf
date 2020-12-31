/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File PublicImageVersionEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class PublicImageVersionEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	
	private String imageName;

	private long publicImageId;

	private String tag;

	private String version;

	private int status;

	private String description;
	
	private int loadStatus;
	
	private int deployStatus;
	
	/**
	 * 镜像版本URL
	 */
	private String url;

	@Override
	public String toString() {
		return "PublicImageVersionEntity "
				+ "[id=" + id 
				+ ", publicImageId=" + publicImageId 
				+ ", tag=" + tag 
				+ ", loadStatus=" + loadStatus 
				+ ", version=" + version 
				+ ", status=" + status 
				+ ", description=" + description
				+ ", url="+ url +"]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode()) +
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
		PublicImageVersionEntity other = (PublicImageVersionEntity) obj;
		
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	
	public int getDeployStatus() {
		return deployStatus;
	}


	public void setDeployStatus(int deployStatus) {
		this.deployStatus = deployStatus;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPublicImageId() {
		return publicImageId;
	}

	public void setPublicImageId(long publicImageId) {
		this.publicImageId = publicImageId;
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


	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(int loadStatus) {
		this.loadStatus = loadStatus;
	}
	
}
