/**
 * Copyright (c) 2015 cmsz, Inc. All Rights Reserved
 * File Instance.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Instance
 *
 * @author hehm
 *
 * @date 2015-4-8
 */
public class Instance implements Serializable{
    
	private static final long serialVersionUID = 1L;

	private String id;
	
	private int type;

	private String containerId;

	private Date createTime;

	private Date startedAt;

	private String status;

	private String hostIp;

	private int restartCount;

	private String suggestMsg;

	private String websshUrl;
	
	private String namespace;

	@Override
	public String toString() {
		return "Instance [id=" + id + ", type=" + type + ", containerId=" + containerId + ", createTime=" + createTime
				+ ", startedAt=" + startedAt + ", status=" + status + ", hostIp=" + hostIp + ", restartCount="
				+ restartCount + ", suggestMsg=" + suggestMsg + ", websshUrl=" + websshUrl + ", namespace=" + namespace
				+ "]";
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public int getRestartCount() {
		return restartCount;
	}

	public void setRestartCount(int restartCount) {
		this.restartCount = restartCount;
	}

	public String getSuggestMsg() {
		return suggestMsg;
	}

	public void setSuggestMsg(String suggestMsg) {
		this.suggestMsg = suggestMsg;
	}

	public String getWebsshUrl() {
		return websshUrl;
	}

	public void setWebsshUrl(String websshUrl) {
		this.websshUrl = websshUrl;
	}
}
