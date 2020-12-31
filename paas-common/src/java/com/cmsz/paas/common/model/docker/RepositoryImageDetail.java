/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File RepositoryImages.java
 */
package com.cmsz.paas.common.model.docker;

import java.util.List;

/**
 * @author hehm
 * 2016-4-9
 */
public class RepositoryImageDetail {

	private String type;

	private List<String> tags;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTags() {
		return tags;
	}
}
