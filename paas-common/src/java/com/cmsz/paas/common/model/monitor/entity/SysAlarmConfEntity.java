/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppImageVersion.java
 */
package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;


public class SysAlarmConfEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private String name;

	private String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SysAlarmConfEntity [id=" + id + ", name=" + name + ", value="
				+ value + "]";
	}

	
	
}
