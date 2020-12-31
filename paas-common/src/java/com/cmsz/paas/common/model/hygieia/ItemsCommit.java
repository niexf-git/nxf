package com.cmsz.paas.common.model.hygieia;

public class ItemsCommit {

	private String id;
	
	private String scmUrl;
	
	private String scmRevisionNumber;
	
	private String scmCommitLog;
	
	private String scmAuthor;
	
	private String scmCommitTimestamp;
	
	private String numberOfChanges;
	
	private String collectorItemId;
	
	private String timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
