package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

public class ConfigMap {

	private String kind;
	
	private String apiVersion;
	
    private ObjectMeta metadata;
	
	private Map<String,String> data;
	
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

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	@Override
	public String toString(){
		return "ConfigMap "
				+ "[kind="+kind
				+",apiVersion="+apiVersion
				+",metadata="+ metadata
				+",data="+data;
	}
	
}
