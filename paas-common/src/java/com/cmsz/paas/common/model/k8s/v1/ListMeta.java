package com.cmsz.paas.common.model.k8s.v1;

public class ListMeta {

	private String selfLink;

	private String resourceVersion;

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	public String getSelfLink() {
		return selfLink;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	@Override
	public String toString() {
		return "ListMeta "
				+ "[selfLink=" + selfLink 
				+ ", resourceVersion=" + resourceVersion + "]";
	}
}
