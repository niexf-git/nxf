package com.cmsz.paas.web.cicd.model;

/**
 * 代码库信息
 * 
 * @author liaohw
 * @date 2017-8-25
 */
public class RepositoryInfo {

	/** 唯一标识 */
	private String id;

	/** 地址 */
	private String url;

	/** 用户名 */
	private String name;

	/** 密码 */
	private String password;

	/** 代码存放目录 */
	private String storePath;

	/** 分支名称 */
	private String branchName;

	/** 代码仓库类型 1-svn，2-git */
	private String repositoryType;

	/** 版本号 */
	private String versionNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getRepositoryType() {
		return repositoryType;
	}

	public void setRepositoryType(String repositoryType) {
		this.repositoryType = repositoryType;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Override
	public String toString() {
		return "RepositoryInfo [id=" + id + ", url=" + url + ", name=" + name
				+ ", password=" + password + ", storePath=" + storePath
				+ ", branchName=" + branchName + ", repositoryType="
				+ repositoryType + ", versionNumber=" + versionNumber + "]";
	}

}
