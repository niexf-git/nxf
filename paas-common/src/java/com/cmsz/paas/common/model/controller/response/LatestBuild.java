package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;

/**
 * @ClassName: LatestBuild.java
 * @Description: 
 *
 *
 * @author zhongmg
 * @date: 2017年11月28日 下午12:27:14
 * @version: v1.0
 */
public class LatestBuild implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String    statu;
	private String	  buildNumber;
	private String    buildName;
	private String    buildTime;
	private String    buildDuration;
	
	
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getBuildNumber() {
		return buildNumber;
	}
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public String getBuildDuration() {
		return buildDuration;
	}
	public void setBuildDuration(String buildDuration) {
		this.buildDuration = buildDuration;
	}

}
