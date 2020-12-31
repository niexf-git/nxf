/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File CodePathEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class CodePathEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long codeAccountId;

	private String buildId;

	private String path;

	private String localDir;
	
	private String branchName;
	
	private String versionNum;
	
	private int sorting;
	/**
	 * 代码类型
	 */
	private int type;

	/**
	 * 代码对应的账号
	 */
	private CodeAccountEntity codeAccount;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodePathEntity other = (CodePathEntity) obj;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (buildId == null) {
			if (other.buildId != null)
				return false;
		} else if (!buildId.equals(other.buildId))
			return false;
		if (codeAccount == null) {
			if (other.codeAccount != null)
				return false;
		} else if (!codeAccount.equals(other.codeAccount))
			return false;
		if (codeAccountId != other.codeAccountId)
			return false;
		if (id != other.id)
			return false;
		if (localDir == null) {
			if (other.localDir != null)
				return false;
		} else if (!localDir.equals(other.localDir))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (type != other.type)
			return false;
		if (versionNum == null) {
			if (other.versionNum != null)
				return false;
		} else if (!versionNum.equals(other.versionNum))
			return false;
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodeAccountId() {
		return codeAccountId;
	}

	public void setCodeAccountId(long codeAccountId) {
		this.codeAccountId = codeAccountId;
	}


	public String getBuildId()
    {
        return buildId;
    }

    public void setBuildId(String buildId)
    {
        this.buildId = buildId;
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLocalDir() {
		return localDir;
	}

	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public CodeAccountEntity getCodeAccount() {
		return codeAccount;
	}

	public void setCodeAccount(CodeAccountEntity codeAccount) {
		this.codeAccount = codeAccount;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public int getSorting() {
		return sorting;
	}

	public void setSorting(int sorting) {
		this.sorting = sorting;
	}

	@Override
	public String toString() {
		return "CodePathEntity [id=" + id + ", codeAccountId=" + codeAccountId + ", buildId=" + buildId + ", path="
				+ path + ", localDir=" + localDir + ", branchName=" + branchName + ", versionNum=" + versionNum
				+ ", sorting=" + sorting + ", type=" + type + ", codeAccount=" + codeAccount + "]";
	} 

}
