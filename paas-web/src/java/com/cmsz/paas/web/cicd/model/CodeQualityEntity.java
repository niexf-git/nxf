package com.cmsz.paas.web.cicd.model;
/**
 * 代码质量列表
 * 
 * @author dengpu
 * @date 2017-9-1
 */
public class CodeQualityEntity {
	/**重复*/
	private String repeat;
	/**圈复杂度*/
	private String loopComplexity;
	/**安全漏洞*/
	private String securityVulnerabilities;
	/**致使*/
	private String cause;
	/**严重*/
	private String serious;
	/**一般*/
	private String commonly;
	/**轻微*/
	private String slight;
	/**构件记录*/
	private String executionRecord;
	/**代码负责人*/
	private String person;

	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getSerious() {
		return serious;
	}
	public void setSerious(String serious) {
		this.serious = serious;
	}
	public String getCommonly() {
		return commonly;
	}
	public void setCommonly(String commonly) {
		this.commonly = commonly;
	}
	public String getSlight() {
		return slight;
	}
	public void setSlight(String slight) {
		this.slight = slight;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public String getLoopComplexity() {
		return loopComplexity;
	}
	public void setLoopComplexity(String loopComplexity) {
		this.loopComplexity = loopComplexity;
	}
	public String getSecurityVulnerabilities() {
		return securityVulnerabilities;
	}
	public void setSecurityVulnerabilities(String securityVulnerabilities) {
		this.securityVulnerabilities = securityVulnerabilities;
	}
	public String getExecutionRecord() {
		return executionRecord;
	}
	public void setExecutionRecord(String executionRecord) {
		this.executionRecord = executionRecord;
	}
	
	
}
