package com.cmsz.paas.common.model.hygieia;

public class CodeReport {

	private String collectorId;
	
	private boolean enabled;
	
	private String id;
	
	private long lastUpdated;
	
	private Options options;
	
	private boolean pushed;

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public boolean getPushed() {
		return pushed;
	}

	public void setPushed(boolean pushed) {
		this.pushed = pushed;
	}

	@Override
	public String toString() {
		return "CodeReport [collectorId=" + collectorId + ", enabled=" + enabled + ", id=" + id + ", lastUpdated="
				+ lastUpdated + ", options=" + options + ", pushed=" + pushed + "]";
	}
}
