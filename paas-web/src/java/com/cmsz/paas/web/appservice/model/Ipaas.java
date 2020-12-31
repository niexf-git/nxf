/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File Ipaas.java
 */
package com.cmsz.paas.web.appservice.model;

/**
 * IpaaS服务
 * 
 * @author fubl
 */
public class Ipaas {

	private String id;

	private String name;

	private String bindStr;

	private String type;// 服务类型，zk-1,redis-2

	private String envs;// ipaas发布的环境变量，json串

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnvs() {
		return envs;
	}

	public void setEnvs(String envs) {
		this.envs = envs;
	}

	@Override
	public String toString() {
		return "Ipaas [id=" + id + ", name=" + name + ", bindStr=" + bindStr
				+ ", type=" + type + ", envs=" + envs + "]";
	}
}
