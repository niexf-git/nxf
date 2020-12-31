package com.cmsz.paas.common.model.k8s.v1;

public class OwnerReference {
	
	private String apiVersion;
	
	private String kind;
	
	private String name;
	
	private String uid;
	
	private boolean controller;
	
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
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

	public boolean isController() {
		return controller;
	}

	public void setController(boolean controller) {
		this.controller = controller;
	}

	@Override
	public String toString() {
		return "OwnerReference"
				+ " [apiVersion=" + apiVersion 
				+ ", kind=" + kind
				+ ", name=" + name 
				+ ", uid=" + uid 
				+ ", controller=" + controller + "]";
	}
}
