package com.cmsz.paas.common.model.hygieia;

public class Options {

	private String  scm;
	
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

	@Override
	public String toString() {
		return "Options [scm=" + scm + ", url=" + url + "]";
	}
}
