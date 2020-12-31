package com.cmsz.paas.common.model.hygieia;

import java.util.Arrays;

import com.cmsz.paas.common.utils.JsonUtil;

public class CollectorItems {
	
	private String [] collectorItemIds;
	
	private String componentId;
	
	private String name;
	
	private CommitOptions options;

	public String[] getCollectorItemIds() {
		return collectorItemIds;
	}

	public void setCollectorItemIds(String[] collectorItemIds) {
		this.collectorItemIds = collectorItemIds;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommitOptions getOptions() {
		return options;
	}

	public void setOptions(CommitOptions options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "CollectorItems [collectorItemIds=" + Arrays.toString(collectorItemIds) + ", componentId=" + componentId
				+ ", name=" + name + ", options=" + options + "]";
	}
	
}
