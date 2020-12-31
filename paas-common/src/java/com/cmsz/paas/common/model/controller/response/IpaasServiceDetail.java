/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File IpaasServiceDetail.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.IpaasServiceEntity;

/**
 * @author hehm
 * 2016-3-24
 */
public class IpaasServiceDetail {

	private IpaasServiceEntity ipaasServiceDetail;

	public void setIpaasServiceDetail(IpaasServiceEntity ipaasServiceDetail) {
		this.ipaasServiceDetail = ipaasServiceDetail;
	}

	public IpaasServiceEntity getIpaasServiceDetail() {
		return ipaasServiceDetail;
	}
}
