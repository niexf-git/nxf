package com.cmsz.paas.common.model.controller.entity;

/**
 * 存放robot framework测试结果的尸体类
 * @author Administrator
 *
 */
public class RobotTestResultEntity {

	//类型
	private String fileKind;
	//报告
	private String report;
	
	public String getFileKind() {
		return fileKind;
	}
	public void setFileKind(String fileKind) {
		this.fileKind = fileKind;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
}
