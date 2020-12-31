package com.cmsz.paas.web.cicd.model;

/**
 * 发布实体
 * 
 * @author liaohw
 * @date 2017-11-23
 */
public class ReleaseEntity {
	/** 目的地 */
	private String destination;

	/** 版本号 */
	private String versionNumber;

	/** 发布方式 */
	private String type;

	/** 卡片公共属性实体 */
	private StepDetailInfo stepDetailInfo;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public StepDetailInfo getStepDetailInfo() {
		return stepDetailInfo;
	}

	public void setStepDetailInfo(StepDetailInfo stepDetailInfo) {
		this.stepDetailInfo = stepDetailInfo;
	}

	@Override
	public String toString() {
		return "ReleaseEntity2 [destination=" + destination
				+ ", versionNumber=" + versionNumber + ", type=" + type
				+ ", stepDetailInfo=" + stepDetailInfo + "]";
	}

}
