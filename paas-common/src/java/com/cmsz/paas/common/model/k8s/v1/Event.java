package com.cmsz.paas.common.model.k8s.v1;

import java.util.Date;

public class Event {

	private String kind;
	
	private String apiVersion;
	
	private ObjectMeta metadata;
	
	private ObjectReference involvedObject;
	
	private String reason;
	
	private String message;
	
	private EventSource source;
	
	private Date firstTimestamp;
	
	private Date lastTimestamp;
	
	private int count;
	
	private String type;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public ObjectMeta getMetadata() {
		return metadata;
	}

	public void setMetadata(ObjectMeta metadata) {
		this.metadata = metadata;
	}

	public ObjectReference getInvolvedObject() {
		return involvedObject;
	}

	public void setInvolvedObject(ObjectReference involvedObject) {
		this.involvedObject = involvedObject;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EventSource getSource() {
		return source;
	}

	public void setSource(EventSource source) {
		this.source = source;
	}

	public Date getFirstTimestamp() {
		return firstTimestamp;
	}

	public void setFirstTimestamp(Date firstTimestamp) {
		this.firstTimestamp = firstTimestamp;
	}

	public Date getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(Date lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event [kind=" + kind + ", apiVersion=" + apiVersion + ", metadata=" + metadata + ", involvedObject="
				+ involvedObject + ", reason=" + reason + ", message=" + message + ", source=" + source
				+ ", firstTimestamp=" + firstTimestamp + ", lastTimestamp=" + lastTimestamp + ", count=" + count
				+ ", type=" + type + "]";
	}
}
