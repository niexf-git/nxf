/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File SvnRegistyEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class CodeRegistryEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	
	private int type;

	private String url;

	private Date createTime;

	@Override
	public String toString() {
		return "CodeRegistryEntity "
				+ "[id=" + id 
				+ ", type=" + type
				+ ", url=" + url 
				+ ", createTime=" + createTime + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	} 
}
