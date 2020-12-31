package com.cmsz.paas.common.model.k8s.v1;

public class StatefulSet {
 
    private String kind;
    
    private String apiVersion;
    
    private ObjectMeta metadata;
    
    private StatefulSetSpec spec;
    
    private StatefulSetStatus status;

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

	public StatefulSetSpec getSpec() {
		return spec;
	}

	public void setSpec(StatefulSetSpec spec) {
		this.spec = spec;
	}

	public StatefulSetStatus getStatus() {
		return status;
	}

	public void setStatus(StatefulSetStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "StatefulSet [kind=" + kind + ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata + ", spec=" + spec + ", status="
				+ status + "]";
	}
    
}
