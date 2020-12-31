package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class EndpointsList {

	private String kind;

	private String apiVersion;

	private ListMeta metadata;

	private Endpoints[] items;

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

	public Endpoints[] getItems() {
		return items;
	}

	public void setItems(Endpoints[] items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "EndpointsList"
				+ " [kind=" + kind
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata 
				+ ", items=" + Arrays.toString(items) + "]";
	}
}
