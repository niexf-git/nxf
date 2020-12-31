/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File IpaasServiceList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.IpaasServiceEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class IpaasServiceList {

	private List<IpaasServiceEntity> ipaasList;

	public void setIpaasList(List<IpaasServiceEntity> ipaasList) {
		this.ipaasList = ipaasList;
	}

	public List<IpaasServiceEntity> getIpaasList() {
		return ipaasList;
	}
}
