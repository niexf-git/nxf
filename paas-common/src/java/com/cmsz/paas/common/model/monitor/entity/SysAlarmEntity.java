/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File SysAlarmEntity.java
 */
package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;


public class SysAlarmEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	
	private int type;
	
	private String name;
	
	private String nodeIp;
	
	private String description;
	
	private Date time;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "SysAlarmEntity [id=" + id + ", type=" + type + ", name=" + name
				+ ", nodeIp=" + nodeIp + ", description=" + description
				+ ", time=" + time + "]";
	}

	
	
	
}
