package com.cmsz.paas.common.model.k8s.v1;

public class PodStatusResult {

	private String kind;
    
    private String apiVersion;
	
	private ObjectMeta metadata;
	
	private PodStatus status;
	
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

	public PodStatus getStatus() {
		return status;
	}

	public void setStatus(PodStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PodStatusResult"
				+ " [kind=" + kind 
				+ ", apiVersion=" + apiVersion 
				+", metadata="+ metadata 
				+", status="+ status + "]";
	}
}
