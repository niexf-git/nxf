package com.cmsz.paas.web.cicd.model;

import java.util.List;

/***
 * 概览构建信息
 * @author jiangwei
 *
 */
public class MeasureBuildEntity {

	
	/**
	 * 每天构建次数、构建信息
	 */
	private List<BuildsPerDayEntity> buildsPerDayEntities;
	
	/**
	 * 每天平均构建时间
	 */
	private List<AvgBuilds> avgBuilds;
	
	/**
	 * 构建概览信息
	 */
	private TotalBuils totalBuilds;

	public List<BuildsPerDayEntity> getBuildsPerDayEntities() {
		return buildsPerDayEntities;
	}

	public void setBuildsPerDayEntities(
			List<BuildsPerDayEntity> buildsPerDayEntities) {
		this.buildsPerDayEntities = buildsPerDayEntities;
	}

	public List<AvgBuilds> getAvgBuilds() {
		return avgBuilds;
	}

	public void setAvgBuilds(List<AvgBuilds> avgBuilds) {
		this.avgBuilds = avgBuilds;
	}

	public TotalBuils getTotalBuilds() {
		return totalBuilds;
	}

	public void setTotalBuilds(TotalBuils totalBuilds) {
		this.totalBuilds = totalBuilds;
	}

	
	
}
