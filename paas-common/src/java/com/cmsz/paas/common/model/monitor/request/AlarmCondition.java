/**
 * Copyright (c) 2015 cmsz, Inc. All Rights Reserved
 * File Cluster.java
 */
package com.cmsz.paas.common.model.monitor.request;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * AlarmCondition
 *
 * @author 
 *
 * @date 2016-12-21
 */
public class AlarmCondition {
	
	private String cpu;
	
	private String memory;
	
	private String filesystem;

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getFilesystem() {
		return filesystem;
	}

	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}

	@Override
	public String toString() {
		return "AlarmCondition [cpu=" + cpu + ", memory=" + memory
				+ ", filesystem=" + filesystem + "]";
	}
	
	
	
	
}
