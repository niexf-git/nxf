package com.cmsz.paas.common.model.k8s.v1;

public class SubresourceReference {
	private String kind;
	  
	private String name;
	  
	private String apiVersion;
	
	private String subresource;
	  
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
	
	public String getApiVersion() {
		return apiVersion;
	}
	
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	public String getSubresource() {
		return subresource;
	}
	
	public void setSubresource(String subresource) {
		this.subresource = subresource;
	}
	
	public String toString(){
		return " SubresourceReference[kind="+ kind + ",name=" + name + ",apiVersion="
	            + apiVersion + ",subresource" + subresource +"]";
	}
}
