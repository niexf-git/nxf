package com.cmsz.paas.common.model.k8s.v1;

public class ObjectFieldSelector {

	private String apiVersion;

	private String fieldPath;

	@Override
	public String toString() {
		return "ObjectFieldSelector "
				+ "[apiVersion=" + apiVersion 
				+ ", fieldPath=" + fieldPath + "]";
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}
}
