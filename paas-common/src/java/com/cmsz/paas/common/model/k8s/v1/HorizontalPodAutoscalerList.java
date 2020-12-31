/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File HorizontalPodAutoscalerList.java
 */
package com.cmsz.paas.common.model.k8s.v1;

/**
 * @author hehm
 * 2016-4-5
 */
public class HorizontalPodAutoscalerList {

	private String kind;

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

	public HorizontalPodAutoscaler[] getItems() {
		return items;
	}

	public void setItems(HorizontalPodAutoscaler[] items) {
		this.items = items;
	}

	private String apiVersion;

	private ListMeta metadata;

	private HorizontalPodAutoscaler[] items;
}
