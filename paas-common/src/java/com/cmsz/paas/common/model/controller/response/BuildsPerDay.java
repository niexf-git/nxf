package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: BuildsPerDayEntity.java
 * @Description: 
 *
 *
 * @author zhongmg
 * @date: 2017年11月28日 下午12:27:11
 * @version: v1.0
 */
public class BuildsPerDay implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String successCount;
	private String failuresCount;
	private String buildDate;
	private List<LatestBuild> latestBuildList;
	 
	public String getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}
	public String getFailuresCount() {
		return failuresCount;
	}
	public void setFailuresCount(String failuresCount) {
		this.failuresCount = failuresCount;
	}
	public String getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	public List<LatestBuild> getLatestBuildList() {
		return latestBuildList;
	}
	public void setLatestBuildList(List<LatestBuild> latestBuildList) {
		this.latestBuildList = latestBuildList;
	}
	 
}
