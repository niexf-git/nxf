/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File RepositoryImageList.java
 */
package com.cmsz.paas.common.model.docker;

import java.util.List;

/**
 * @author hehm
 * 2016-4-9
 */
public class RepositoryImageList {

	private List<String> repositories;

	public void setRepositories(List<String> repositories) {
		this.repositories = repositories;
	}

	public List<String> getRepositories() {
		return repositories;
	}
}
