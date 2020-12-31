package com.cmsz.paas.web.cicd.model;

public class CodeRepoEntity {

	/**
	 * 代码库类型
	 */
	private String scm;
	
	/**
	 * 代码库地址
	 */
	private String url;

	public String getScm() {
		return scm;
	}

	public void setScm(String scm) {
		this.scm = scm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
