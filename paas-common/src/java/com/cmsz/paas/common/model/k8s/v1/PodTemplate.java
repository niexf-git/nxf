package com.cmsz.paas.common.model.k8s.v1;

public class PodTemplate {
	
	private String kind;

	private String apiVersion;
	
	private ObjectMeta metadata;
	
	private PodTemplateSpec template;
	
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

	public PodTemplateSpec getTemplate() {
		return template;
	}

	public void setTemplate(PodTemplateSpec template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "PodTemplate "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion 
				+ ", metadata=" + metadata 
				+ ", template=" + template + "]";
	}

}
