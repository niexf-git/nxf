/**
 * Copyright (c) 2015 cmsz, Inc. All Rights Reserved
 * File InstanceList.java
 */
package com.cmsz.paas.common.model.response;

import java.util.List;

import com.cmsz.paas.common.model.entity.Instance;

/**
 * InstanceList
 *
 * @author hehm
 *
 * @date 2015-4-8
 */
public class InstanceList {
	private List<Instance> instanceList;

	/**
	 * @param instanceList the instanceList to set
	 */
	public void setInstanceList(List<Instance> instanceList) {
		this.instanceList = instanceList;
	}

	/**
	 * @return the instanceList
	 */
	public List<Instance> getInstanceList() {
		return instanceList;
	}
}
