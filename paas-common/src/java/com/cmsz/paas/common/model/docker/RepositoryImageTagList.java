/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File RepositoryImageVersionList.java
 */
package com.cmsz.paas.common.model.docker;

import java.util.List;

/**
 * @author hehm
 * 2016-4-9
 */
public class RepositoryImageTagList {

	private String name;

	private List<String> tags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
