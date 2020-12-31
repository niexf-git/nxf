package com.cmsz.paas.common.model.k8s.v1;

public class ObjectReference {

	private String kind;

	private String namespace;

	private String name;

	private String uid;

	private String apiVersion;

	private String resourceVersion;

	private String fieldPath;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	@Override
	public String toString() {
		return "ObjectReference "
				+ "[kind=" + kind 
				+ ", namespace=" + namespace
				+ ", name=" + name 
				+ ", uid=" + uid 
				+ ", apiVersion=" + apiVersion 
				+ ", resourceVersion=" + resourceVersion
				+ ", fieldPath=" + fieldPath + "]";
	}
}
