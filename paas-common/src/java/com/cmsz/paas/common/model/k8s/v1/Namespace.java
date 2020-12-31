package com.cmsz.paas.common.model.k8s.v1;

public class Namespace {
	
	private String kind;
	
	private String apiVersion;
	
	private ObjectMeta metadata;
	
	private NamespaceSpec spec;
	
	private NamespaceStatus status;

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

	public NamespaceSpec getSpec() {
		return spec;
	}

	public void setSpec(NamespaceSpec spec) {
		this.spec = spec;
	}

	public NamespaceStatus getStatus() {
		return status;
	}

	public void setStatus(NamespaceStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return " Namespace"
				+ "[kind =" + kind 
				+",apiVersion =" + apiVersion 
				+",metadata ="+ metadata 
				+",spec=" + spec 
				+",status" + status + "]";
		
	}
}
