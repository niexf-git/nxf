package com.cmsz.paas.common.model.k8s.v1;

public class ConfigMapList {

	private String kind;

	private String apiVersion;

	private ListMeta metadata;

	private ConfigMap[] items;
	
	@Override
	public String toString() {
		return "ConfigMapList "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata
				+ ", items=" + items + "]";
	}

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

	public ConfigMap[] getItems() {
		return items;
	}

	public void setItems(ConfigMap[] items) {
		this.items = items;
	}
	
}
