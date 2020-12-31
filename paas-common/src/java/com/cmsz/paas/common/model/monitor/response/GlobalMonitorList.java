package com.cmsz.paas.common.model.monitor.response;

import java.util.ArrayList;
import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.GlobalMonitorEntity;

public class GlobalMonitorList {
	private List<GlobalMonitorEntity> nodeList = new ArrayList<GlobalMonitorEntity>();

	public List<GlobalMonitorEntity> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<GlobalMonitorEntity> nodeList) {
		this.nodeList = nodeList;
	}
}
