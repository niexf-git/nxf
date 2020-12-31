package com.cmsz.paas.web.appservicegray.model;

public class GrayRelease {

	private String id;

	private String imageUrl;

	private String imageversion;

	private String instanceCount;

	private String runInstance;

	private String state;

	private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageversion() {
		return imageversion;
	}

	public void setImageversion(String imageversion) {
		this.imageversion = imageversion;
	}

	public String getInstanceCount() {
		return instanceCount;
	}

	public void setInstanceCount(String instanceCount) {
		this.instanceCount = instanceCount;
	}

	public String getRunInstance() {
		return runInstance;
	}

	public void setRunInstance(String runInstance) {
		this.runInstance = runInstance;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
