/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File AppId.java
 */
package com.cmsz.paas.common.model.controller.request;

/**
 * @author hehm
 * 2016-4-15
 */
public class AppId {

	private long id;

	private int type;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
