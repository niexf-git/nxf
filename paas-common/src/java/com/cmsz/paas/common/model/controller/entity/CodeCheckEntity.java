package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class CodeCheckEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String stepRecordId;
	private String sonarkeyName;// sonar唯一标识
	private String sonarUuid; // sonar扫描uuid
	private long buildNumber; // 某一次构建
	private int codeNumber;// 扫描代码行数
	private String createTime;// 创建时间
	private int blockerNum;// 阻断问题
	private int criticalNum;// 严重问题数
	private int majorNum;// 重要问题数；
	private int minorNum;// 轻微问题数
	private int infoNum;// 提示问题
	private int multiplicity;// 代码重复行数
	private double codeRepRate;//代码重复率
	private int complexity;// 圈复杂度
	private int bugNum;// 安全漏洞
	private String coder;// 代码负责人
	
	public String getStepRecordId() {
		return stepRecordId;
	}

	public void setStepRecordId(String stepRecordId) {
		this.stepRecordId = stepRecordId;
	}

	public double getCodeRepRate() {
		return codeRepRate;
	}

	public void setCodeRepRate(double codeRepRate) {
		this.codeRepRate = codeRepRate;
	}

	public long getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(long buildNum) {
		this.buildNumber = buildNum;
	}

	public String getSonarUuid() {
		return sonarUuid;
	}

	public void setSonarUuid(String sonarUuid) {
		this.sonarUuid = sonarUuid;
	}

	public String getSonarkeyName() {
		return sonarkeyName;
	}

	public void setSonarkeyName(String sonarkeyName) {
		this.sonarkeyName = sonarkeyName;
	}

	public int getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(int codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getBlockerNum() {
		return blockerNum;
	}

	public void setBlockerNum(int blockerNum) {
		this.blockerNum = blockerNum;
	}

	public int getCriticalNum() {
		return criticalNum;
	}

	public void setCriticalNum(int criticalNum) {
		this.criticalNum = criticalNum;
	}

	public int getMajorNum() {
		return majorNum;
	}

	public void setMajorNum(int majorNum) {
		this.majorNum = majorNum;
	}

	public int getMinorNum() {
		return minorNum;
	}

	public void setMinorNum(int minorNum) {
		this.minorNum = minorNum;
	}

	public int getInfoNum() {
		return infoNum;
	}

	public void setInfoNum(int infoNum) {
		this.infoNum = infoNum;
	}

	public int getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(int multiplicity) {
		this.multiplicity = multiplicity;
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

	public int getBugNum() {
		return bugNum;
	}

	public void setBugNum(int bugNum) {
		this.bugNum = bugNum;
	}

	public String getCoder() {
		return coder;
	}

	public void setCoder(String coder) {
		this.coder = coder;
	}
	

}
