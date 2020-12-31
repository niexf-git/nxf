/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File IpaasPublishEnvList.java
 */
package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.PublishEnvEntity;

/**
 * @author hehm
 * 2016-3-31
 */
public class PublishEnvList {

	private List<PublishEnvEntity> envs;

	public void setEnvs(List<PublishEnvEntity> envs) {
		this.envs = envs;
	}

	public List<PublishEnvEntity> getEnvs() {
		return envs;
	}
}
