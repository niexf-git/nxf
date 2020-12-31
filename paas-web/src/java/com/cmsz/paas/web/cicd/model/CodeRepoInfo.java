package com.cmsz.paas.web.cicd.model;

public class CodeRepoInfo {

	/**
	 * 供应链管理路径
	 */
	private String scmUrl;
	
	/**
	 * 修改版本号
	 */
	private String scmRevisionNumber;
	
	/**
	 * 提交消息
	 */
	private String scmCommitLog;
	
	/**
	 * 提交人
	 */
	private String scmAuthor;
	
	/**
	 * 提交时间
	 */
	private String scmCommitTimestamp;
	
	/**
	 * 修改个数
	 */
	private String numberOfChanges;
	
	private String id;

	/**
	 * 采集项目id
	 */
	private String collectorItemId;
	
	/**
	 * 时间
	 */
	private String timestamp;

	public String getScmUrl() {
		return scmUrl;
	}

	public void setScmUrl(String scmUrl) {
		this.scmUrl = scmUrl;
	}

	public String getScmRevisionNumber() {
		return scmRevisionNumber;
	}

	public void setScmRevisionNumber(String scmRevisionNumber) {
		this.scmRevisionNumber = scmRevisionNumber;
	}

	public String getScmCommitLog() {
		return scmCommitLog;
	}

	public void setScmCommitLog(String scmCommitLog) {
		this.scmCommitLog = scmCommitLog;
	}

	public String getScmAuthor() {
		return scmAuthor;
	}

	public void setScmAuthor(String scmAuthor) {
		this.scmAuthor = scmAuthor;
	}

	public String getScmCommitTimestamp() {
		return scmCommitTimestamp;
	}

	public void setScmCommitTimestamp(String scmCommitTimestamp) {
		this.scmCommitTimestamp = scmCommitTimestamp;
	}

	public String getNumberOfChanges() {
		return numberOfChanges;
	}

	public void setNumberOfChanges(String numberOfChanges) {
		this.numberOfChanges = numberOfChanges;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectorItemId() {
		return collectorItemId;
	}

	public void setCollectorItemId(String collectorItemId) {
		this.collectorItemId = collectorItemId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
