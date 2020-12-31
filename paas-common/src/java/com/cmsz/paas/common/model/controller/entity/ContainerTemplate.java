package com.cmsz.paas.common.model.controller.entity;

public class ContainerTemplate {
	private String Id;// 容器id
	private String Warnings;


	public String getWarnings() {
		return Warnings;
	}

	public void setWarnings(String warnings) {
		Warnings = warnings;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@Override
	public String toString() {
		return "ContainerTemplate [Id=" + Id + ", Warnings=" + Warnings + "]";
	}
}
