/**
 * Copyright (c) 2015 cmsz, Inc. All Rights Reserved
 * File MinionList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.Node;

/**
 * MinionList
 *
 * @author hehm
 *
 * @date 2015-4-8
 */
public class NodeList {

	private List<Node> nodeList;

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}
}
