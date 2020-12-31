/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppImageVersionList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.PrivateImageVersionEntity;

/**
 * @author hehm
 * 2016-3-27
 */
public class PrivateImageVersionList {
	
	/**状态*/
	private String status;
	/**镜像版本*/
	private String version;
	/**时间*/
	private String time;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private List<PrivateImageVersionEntity> privateImageVersionList;

	public void setPrivateImageVersionList(List<PrivateImageVersionEntity> privateImageVersionList) {
		this.privateImageVersionList = privateImageVersionList;
	}

	public List<PrivateImageVersionEntity> getPrivateImageVersionList() {
		return privateImageVersionList;
	}
}
