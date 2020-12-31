package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class PodList {

	private String kind;

	private String apiVersion;

	private ListMeta metadata;

	private Pod[] items;

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

	public ListMeta getMetadata() {
		return metadata;
	}

	public void setMetadata(ListMeta metadata) {
		this.metadata = metadata;
	}

	public Pod[] getItems() {
		return items;
	}

	public void setItems(Pod[] items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "PodList"
				+ " [kind=" + kind 
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata 
				+ ", items=" + Arrays.toString(items) + "]";
	}

}
