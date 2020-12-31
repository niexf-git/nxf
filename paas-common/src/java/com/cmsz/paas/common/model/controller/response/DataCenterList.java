/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File DataCenterList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.DataCenterEntity;

/**
 * @author hehm
 * 2016-3-23
 */
public class DataCenterList {

	private List<DataCenterEntity> dataCenterList;

	public void setDataCenterList(List<DataCenterEntity> dataCenterList) {
		this.dataCenterList = dataCenterList;
	}

	public List<DataCenterEntity> getDataCenterList() {
		return dataCenterList;
	}
}
