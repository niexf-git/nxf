package com.cmsz.paas.common.model.controller.entity;

public class SonarCheckEntity {
	private String sonarkeyName;// sonar唯一标识,
	private String projectName;// 应用名+流水名,
	private int codenumber;// 扫描代码行数,
	private int blockerNum;// 阻断问题数量,
	private int criticalNum;// 严重问题数量,
	private int majorNum;// 重要问题数量,
	private int minorNum;// 轻微问题数量,
	private int infoNum;// 提示问题数量,
	private int complexity;// 复杂度,
	private int multiplicity;// 重复度,
	private String versionId;// 版本ID,
	private String createTime;// 创建时间
	private String leakNum;// 安全漏洞数

	public String getLeakNum() {
		return leakNum;
	}

	public void setLeakNum(String leakNum) {
		this.leakNum = leakNum;
	}

	public String getSonarkeyName() {
		return sonarkeyName;
	}

	public void setSonarkeyName(String sonarkeyName) {
		this.sonarkeyName = sonarkeyName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getCodenumber() {
		return codenumber;
	}

	public void setCodenumber(int codenumber) {
		this.codenumber = codenumber;
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

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

	public int getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(int multiplicity) {
		this.multiplicity = multiplicity;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SonarCheckResponce [sonarkeyName=" + sonarkeyName
				+ ", projectName=" + projectName + ", codenumber=" + codenumber
				+ ", blockerNum=" + blockerNum + ", criticalNum=" + criticalNum
				+ ", majorNum=" + majorNum + ", minorNum=" + minorNum
				+ ", infoNum=" + infoNum + ", complexity=" + complexity
				+ ", multiplicity=" + multiplicity + ", versionId=" + versionId
				+ ", createTime=" + createTime + "]";
	}

}
