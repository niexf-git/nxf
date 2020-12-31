package com.cmsz.paas.web.cicd.model;

/**
 * 代码详情实体
 * 
 * @author ccl
 * @date 2017-8-31
 */
public class CodeDetailsEntity {
	/** sonarUUID */
	private String sonarUUID;
	/** 扫描代码行数 */
	private int codenumber;
	/** 致命问题数量 */
	private int blockerNum;
	/** 严重问题数量 */
	private int criticalNum;
	/** 一般问题数量 */
	private int majorNum;
	/** 轻微问题数量  */
	private int minorNum;
	/** 提示问题数量  */
	private int infoNum;
	/** 复杂度 */
	private int complexity;
	/** 重复度 */
	private int multiplicity;
	/** 安全漏洞数 */
	private int bugNum;
	/** 执行时间 */
	private String createTime;
	
	
	public String getSonarUUID() {
		return sonarUUID;
	}
	public void setSonarUUID(String sonarUUID) {
		this.sonarUUID = sonarUUID;
	}
	public int getBugNum() {
		return bugNum;
	}
	public void setBugNum(int bugNum) {
		this.bugNum = bugNum;
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

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	

}
