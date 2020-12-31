package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class Endpoints {

	private String kind;

	private String apiVersion;

	private ObjectMeta metadata;

	private EndpointSubset[] subsets;

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

	public EndpointSubset[] getSubsets() {
		return subsets;
	}

	public void setSubsets(EndpointSubset[] subsets) {
		this.subsets = subsets;
	}

	@Override
	public String toString() {
		return "Endpoints "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata 
				+ ", subsets=" + Arrays.toString(subsets) + "]";
	}
}
