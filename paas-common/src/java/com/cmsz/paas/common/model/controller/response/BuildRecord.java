package com.cmsz.paas.common.model.controller.response;

import java.util.List;

public class BuildRecord {
	
	private List<BuildsPerDay> buildsPerDayList;
	
	private List<Avgbuilds> avgbuildsList;
	
	private Totalbuilds totalbuilds;

	
	public List<BuildsPerDay> getBuildsPerDayList() {
		return buildsPerDayList;
	}

	public void setBuildsPerDayList(List<BuildsPerDay> buildsPerDayList) {
		this.buildsPerDayList = buildsPerDayList;
	}

	public Totalbuilds getTotalbuilds() {
		return totalbuilds;
	}

	public void setTotalbuilds(Totalbuilds totalbuilds) {
		this.totalbuilds = totalbuilds;
	}

	public List<Avgbuilds> getAvgbuildsList() {
		return avgbuildsList;
	}

	public void setAvgbuildsList(List<Avgbuilds> avgbuildsList) {
		this.avgbuildsList = avgbuildsList;
	}
}
