package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;
/**
 * 代码质量报表
 * @author 
 *
 */
public class CodeCheckStatementEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ID
	private String uuid;
	
	private String stepRecordId;
	
	private String sonarkeyName;//sonar唯一标识

	private String sonarUuid;//sonar扫描uuid

	private Integer buildNumber;//某一次构建
	
	private Integer codeNumber;//扫描代码行数

	private String createTime;//创建时间

	private Integer blockerNum;//阻断问题

	private Integer criticalNum;//严重问题数

	private Integer majorNum;//重要问题数

	private Integer minorNum;//轻微问题数

	private Integer infoNum;//提示问题

	private Integer multiplicity;//代码重复行数

	private Double codeRepRate;//代码重复率

	private Integer complexity;//圈复杂度

	private Integer bugNum;//安全漏洞

	private String coder;//代码负责人
	
	private String flowName;//流水名称
	private Integer buildNum;//执行次数
	private String flowId;//流水id
	private Integer errorCount;//问题总数
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStepRecordId() {
		return stepRecordId;
	}

	public void setStepRecordId(String stepRecordId) {
		this.stepRecordId = stepRecordId;
	}

	public String getSonarkeyName() {
		return sonarkeyName;
	}

	public void setSonarkeyName(String sonarkeyName) {
		this.sonarkeyName = sonarkeyName;
	}

	public String getSonarUuid() {
		return sonarUuid;
	}

	public void setSonarUuid(String sonarUuid) {
		this.sonarUuid = sonarUuid;
	}

	public Integer getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(Integer buildNumber) {
		this.buildNumber = buildNumber;
	}

	public Integer getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(Integer codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getBlockerNum() {
		return blockerNum;
	}

	public void setBlockerNum(Integer blockerNum) {
		this.blockerNum = blockerNum;
	}

	public Integer getCriticalNum() {
		return criticalNum;
	}

	public void setCriticalNum(Integer criticalNum) {
		this.criticalNum = criticalNum;
	}

	public Integer getMajorNum() {
		return majorNum;
	}

	public void setMajorNum(Integer majorNum) {
		this.majorNum = majorNum;
	}

	public Integer getMinorNum() {
		return minorNum;
	}

	public void setMinorNum(Integer minorNum) {
		this.minorNum = minorNum;
	}

	public Integer getInfoNum() {
		return infoNum;
	}

	public void setInfoNum(Integer infoNum) {
		this.infoNum = infoNum;
	}

	public Integer getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(Integer multiplicity) {
		this.multiplicity = multiplicity;
	}

	public Double getCodeRepRate() {
		return codeRepRate;
	}

	public void setCodeRepRate(Double codeRepRate) {
		this.codeRepRate = codeRepRate;
	}

	public Integer getComplexity() {
		return complexity;
	}

	public void setComplexity(Integer complexity) {
		this.complexity = complexity;
	}

	public Integer getBugNum() {
		return bugNum;
	}

	public void setBugNum(Integer bugNum) {
		this.bugNum = bugNum;
	}

	public String getCoder() {
		return coder;
	}

	public void setCoder(String coder) {
		this.coder = coder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Integer getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(Integer buildNum) {
		this.buildNum = buildNum;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

}
