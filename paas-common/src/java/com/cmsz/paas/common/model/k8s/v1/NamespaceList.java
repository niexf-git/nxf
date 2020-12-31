/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File NamespaceList.java
 */
package com.cmsz.paas.common.model.k8s.v1;

/**
 * @author hehm
 * 2016-4-5
 */
public class NamespaceList {

	private String kind;

	private String apiVersion;

	private ListMeta metadata;

	private Namespace[] items;

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getKind() {
		return kind;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setMetadata(ListMeta metadata) {
		this.metadata = metadata;
	}

	public ListMeta getMetadata() {
		return metadata;
	}

	public void setItems(Namespace[] items) {
		this.items = items;
	}

	public Namespace[] getItems() {
		return items;
	}
	
	@Override
	public String toString() {
		return "NamespaceList "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata
				+ ", items=" + items + "]";
	}
}
