package com.cmsz.paas.common.model.k8s.v1;

public class PersistentVolumeClaim {

    private String kind;
    
    private String apiVersion;
    
    private ObjectMeta metadata;
    
    private PersistentVolumeClaimSpec spec;
    
    private PersistentVolumeClaimStatus status;

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

	public PersistentVolumeClaimSpec getSpec() {
		return spec;
	}

	public void setSpec(PersistentVolumeClaimSpec spec) {
		this.spec = spec;
	}

	public PersistentVolumeClaimStatus getStatus() {
		return status;
	}

	public void setStatus(PersistentVolumeClaimStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "PersistentVolumeClaim "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata 
				+ ", spec=" + spec 
				+ ", status=" + status + "]";
	}
}
