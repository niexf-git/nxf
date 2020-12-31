package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class EventList {

	private String kind;
	
	private String apiVersion;
	
	private ObjectMeta metadata;
	
	private Event[] items;

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

	public ObjectMeta getMetadata() {
		return metadata;
	}

	public void setMetadata(ObjectMeta metadata) {
		this.metadata = metadata;
	}

	public Event[] getItems() {
		return items;
	}

	public void setItems(Event[] items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "EventList [kind=" + kind + ", apiVersion=" + apiVersion + ", metadata=" + metadata + ", items="
				+ Arrays.toString(items) + "]";
	}
}
