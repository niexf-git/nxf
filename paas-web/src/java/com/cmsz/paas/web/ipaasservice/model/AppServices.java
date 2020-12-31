/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File Ipaas.java
 */
package com.cmsz.paas.web.ipaasservice.model;

/**
 * IpaaS服务关联的应用服务
 * 
 * @author fubl
 */
public class AppServices {

	private String id;

	private String name;

	private String bindStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBindStr() {
		return bindStr;
	}

	public void setBindStr(String bindStr) {
		this.bindStr = bindStr;
	}

	@Override
	public String toString() {
		return "Ipaas [id=" + id + ", name=" + name + ", bindStr=" + bindStr
				+ "]";
	}
}
