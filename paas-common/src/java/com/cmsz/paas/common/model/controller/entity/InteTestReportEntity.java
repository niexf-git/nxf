package com.cmsz.paas.common.model.controller.entity;

public class InteTestReportEntity {
	private String time;
	   
	private int totals;
    
	private int successNumber;
	
    private String successReat;
    
    private int failNumber;
    
    private String failReat;
    
    private String report;//报告
    
    private String coder;//代码负责人

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public int getSuccessNumber() {
		return successNumber;
	}

	public void setSuccessNumber(int successNumber) {
		this.successNumber = successNumber;
	}

	public String getSuccessReat() {
		return successReat;
	}

	public void setSuccessReat(String successReat) {
		this.successReat = successReat;
	}

	public int getFailNumber() {
		return failNumber;
	}

	public void setFailNumber(int failNumber) {
		this.failNumber = failNumber;
	}

	public String getFailReat() {
		return failReat;
	}

	public void setFailReat(String failReat) {
		this.failReat = failReat;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getCoder() {
		return coder;
	}

	public void setCoder(String coder) {
		this.coder = coder;
	}
	
}
