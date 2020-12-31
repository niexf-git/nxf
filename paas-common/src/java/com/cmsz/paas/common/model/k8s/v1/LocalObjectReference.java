package com.cmsz.paas.common.model.k8s.v1;

public class LocalObjectReference {

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "LocalObjectReference "
				+ "[name=" + name + "]";
	}
}
