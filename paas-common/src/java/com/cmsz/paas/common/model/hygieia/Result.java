package com.cmsz.paas.common.model.hygieia;

public class Result {

	private String collectorItemId;
	
	private String id;
	
	private long numberOfChanges;
	
	private String scmAuthor;
	
	private String scmCommitLog;
	
	private long scmCommitTimestamp;
	
	private String scmRevisionNumber;
	
	private String scmUrl;
	
	private long timestamp;

	public String getCollectorItemId() {
		return collectorItemId;
	}

	public void setCollectorItemId(String collectorItemId) {
		this.collectorItemId = collectorItemId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getNumberOfChanges() {
		return numberOfChanges;
	}

	public void setNumberOfChanges(long numberOfChanges) {
		this.numberOfChanges = numberOfChanges;
	}

	public String getScmAuthor() {
		return scmAuthor;
	}

	public void setScmAuthor(String scmAuthor) {
		this.scmAuthor = scmAuthor;
	}

	public String getScmCommitLog() {
		return scmCommitLog;
	}

	public void setScmCommitLog(String scmCommitLog) {
		this.scmCommitLog = scmCommitLog;
	}

	public long getScmCommitTimestamp() {
		return scmCommitTimestamp;
	}

	public void setScmCommitTimestamp(long scmCommitTimestamp) {
		this.scmCommitTimestamp = scmCommitTimestamp;
	}

	public String getScmRevisionNumber() {
		return scmRevisionNumber;
	}

	public void setScmRevisionNumber(String scmRevisionNumber) {
		this.scmRevisionNumber = scmRevisionNumber;
	}

	public String getScmUrl() {
		return scmUrl;
	}

	public void setScmUrl(String scmUrl) {
		this.scmUrl = scmUrl;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Result [collectorItemId=" + collectorItemId + ", id=" + id + ", numberOfChanges=" + numberOfChanges
				+ ", scmAuthor=" + scmAuthor + ", scmCommitLog=" + scmCommitLog + ", scmCommitTimestamp="
				+ scmCommitTimestamp + ", scmRevisionNumber=" + scmRevisionNumber + ", scmUrl=" + scmUrl
				+ ", timestamp=" + timestamp + "]";
	}
}
