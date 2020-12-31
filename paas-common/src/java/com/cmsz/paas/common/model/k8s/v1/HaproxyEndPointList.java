/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File HaproxyEndPointList.java
 */
package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

/**
 * @author hehm
 *
 * 2016-4-21
 */
public class HaproxyEndPointList {

	private Map<String, String> points;

	private int haproxyPort;

	private int containerPort;

	private int protocolType;

	public Map<String, String> getPoints() {
		return points;
	}

	public void setPoints(Map<String, String> points) {
		this.points = points;
	}

	public int getHaproxyPort() {
		return haproxyPort;
	}

	public void setHaproxyPort(int haproxyPort) {
		this.haproxyPort = haproxyPort;
	}

	public void setContainerPort(int containerPort) {
		this.containerPort = containerPort;
	}

	public int getContainerPort() {
		return containerPort;
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public int getProtocolType() {
		return protocolType;
	}

	@Override
	public String toString() {
		return "HaproxyEndPointList [points=" + points + ", haproxyPort="
				+ haproxyPort + ", containerPort=" + containerPort
				+ ", protocolType=" + protocolType + "]";
	}

}
