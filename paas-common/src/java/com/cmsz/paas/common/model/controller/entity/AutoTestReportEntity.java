package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class AutoTestReportEntity implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String time;//执行记录
	private String totals;//用例总数
	private int successNumber;//成功数
    private String successReat;//成功率
    private int failNumber;//失败数
    private String failReat;//失败率
    private String report;//报告结果
    private String coder;//代码负责人
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTotals() {
		return totals;
	}
	public void setTotals(String totals) {
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
	
	public String getCoder() {
		return coder;
	}
	public void setCoder(String coder) {
		this.coder = coder;
	}
	@Override
	public String toString() {
		return "AutoTestReportEntity [time=" + time + ", totals=" + totals
				+ ", successNumber=" + successNumber + ", successReat="
				+ successReat + ", failNumber=" + failNumber + ", failReat="
				+ failReat + ", report=" + report + ", coder=" + coder + "]";
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
    
}
