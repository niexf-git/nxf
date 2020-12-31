package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.CheckKpiEntity;

public class CheckKpiList {
	private List< CheckKpiEntity> checkKpiList;

	public List<CheckKpiEntity> getCheckKpiList() {
		return checkKpiList;
	}

	public void setCheckKpiList(List<CheckKpiEntity> checkKpiList) {
		this.checkKpiList = checkKpiList;
	}

	@Override
	public String toString() {
		return "CheckKpiEntity [checkKpiList=" + checkKpiList + "]";
	}
}
