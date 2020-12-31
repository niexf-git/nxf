package com.cmsz.paas.common.model.k8s.v1;

public class ConfigMapKeySelector {

	private String name;
	
	private String key;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "ConfigMapKeySelector "
				+ "[name=" + name 
				+ ", key=" + key +"]";
	}
}
