package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.BuildKpiEntity;

public class BuildKpiList {
	private List< BuildKpiEntity> buildKpiList;

	public List<BuildKpiEntity> getBuildKpiList() {
		return buildKpiList;
	}

	public void setBuildKpiList(List<BuildKpiEntity> buildKpiList) {
		this.buildKpiList = buildKpiList;
	}

	@Override
	public String toString() {
		return "BuildKpiEntity [buildKpiList=" + buildKpiList + "]";
	}
}
