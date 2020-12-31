package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.BulidReportEntity;

public class BulidReportList {
	private List< BulidReportEntity> bulidReportList;


	public List<BulidReportEntity> getBulidReportList() {
		return bulidReportList;
	}

	public void setBulidReportList(List<BulidReportEntity> bulidReportList) {
		this.bulidReportList = bulidReportList;
	}
	
	@Override
	public String toString() {
		return "BulidReportList [bulidReportList=" + bulidReportList + "]";
	}
}
