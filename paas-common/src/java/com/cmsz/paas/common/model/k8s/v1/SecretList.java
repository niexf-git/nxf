package com.cmsz.paas.common.model.k8s.v1;

public class SecretList {
	private String kind;

	private String apiVersion;

	private ListMeta metadata;

	private Secret[] items;

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

	public Secret[] getItems() {
		return items;
	}

	public void setItems(Secret[] items) {
		this.items = items;
	}
	
	
}
