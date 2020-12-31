package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

public class Secret {

	private String kind;

	private String apiVersion;

	private ObjectMeta metadata;
	
	private Map<String,String> data;
	
	private Map<String,String> stringData;
	
	private String type;
	
	@Override
	public String toString() {
		return "Secret "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata 
				+ ", data=" + data 
				+ ", stringData=" + stringData 
				+ ", type=" + type + "]";
	}

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

	public Map<String, String> getStringData() {
		return stringData;
	}

	public void setStringData(Map<String, String> stringData) {
		this.stringData = stringData;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
