/**
 * Copyright (c) 2015 cmsz, Inc. All Rights Reserved
 * File Cluster.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * Cluster
 *
 * @author hehm
 *
 * @date 2015-4-8
 */
public class ClusterEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String name;

	private String label;
	
	private int type;

	private String description;

	private long dataCenterId;
	
	private Date insertTime;
	
	//数据中心name
	private String dataCenterName;

	@Override
	public String toString() {
		return "ClusterEntity "
				+ "[id=" + id 
				+ ", name=" + name 
				+ ", label=" + label 
				+ ", type=" + type
				+ ", description=" + description 
				+ ", dataCenterId=" + dataCenterId
				+ ", insertTime=" + insertTime
				+ ", dataCenterName=" + dataCenterName+ "]";
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(long dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getDataCenterName() {
		return dataCenterName;
	}

	public void setDataCenterName(String dataCenterName) {
		this.dataCenterName = dataCenterName;
	}
}
