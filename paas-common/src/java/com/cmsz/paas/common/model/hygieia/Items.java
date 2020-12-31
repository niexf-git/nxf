package com.cmsz.paas.common.model.hygieia;

public class Items {

	private String collectorId;
	
	private Options options;

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Items [collectorId=" + collectorId + ", options=" + options + "]";
	}
}
