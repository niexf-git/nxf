/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File IdValue.java
 */
package com.cmsz.paas.common.model.controller.response;

/**
 * @author hehm
 * 2016-3-27
 */
public class IdValue {

	private String id;
	
	private String errorInfo;
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
