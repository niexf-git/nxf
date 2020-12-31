/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File SvnAccountEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class CodeAccountEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long codeRegistryId;

	private String sysUser;

	private String codeUser;

	private String codePswd;

	private String credentials;

	private Date createTime;

	/**
	 * 对应的代码仓库
	 */
	private CodeRegistryEntity codeRegistry;

	@Override
	public String toString() {
		return "CodeAccountEntity "
				+ "[id=" + id 
				+ ", codeRegistryId=" + codeRegistryId 
				+ ", codeUser=" + sysUser 
				+ ", codeUser=" + codeUser 
				+ ", codePswd=" + codePswd 
				+ ", credentials=" + credentials 
				+ ", createTime=" + createTime 
				+ ", svnRegistry=" + codeRegistry + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodeRegistryId() {
		return codeRegistryId;
	}

	public void setCodeRegistryId(long codeRegistryId) {
		this.codeRegistryId = codeRegistryId;
	}

	public String getSysUser() {
		return sysUser;
	}

	public void setSysUser(String sysUser) {
		this.sysUser = sysUser;
	}

	public String getCodeUser() {
		return codeUser;
	}

	public void setCodeUser(String codeUser) {
		this.codeUser = codeUser;
	}

	public String getCodePswd() {
		return codePswd;
	}

	public void setCodePswd(String codePswd) {
		this.codePswd = codePswd;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CodeRegistryEntity getCodeRegistry() {
		return codeRegistry;
	}

	public void setCodeRegistry(CodeRegistryEntity codeRegistry) {
		this.codeRegistry = codeRegistry;
	}
}
