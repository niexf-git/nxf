package com.cmsz.paas.common.model.hygieia;

public class CommitOptions {

	private String id;
	
	private ScmEntity scm;
	
	private String url;
	
	private String branch;

	public ScmEntity getScm() {
		return scm;
	}

	public void setScm(ScmEntity scm) {
		this.scm = scm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CommitOptions [id=" + id + ", scm=" + scm + ", url=" + url + ", branch=" + branch + "]";
	}
}
