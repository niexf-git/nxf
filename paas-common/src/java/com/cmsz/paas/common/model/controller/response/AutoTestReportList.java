package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.AutoTestReportEntity;

public class AutoTestReportList {

	private List<AutoTestReportEntity> autoTestReport;

	public List<AutoTestReportEntity> getAutoTestReport() {
		return autoTestReport;
	}

	public void setAutoTestReport(List<AutoTestReportEntity> autoTestReport) {
		this.autoTestReport = autoTestReport;
	}

}
