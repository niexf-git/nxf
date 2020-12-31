package com.cmsz.paas.common.model.k8s.v1;

public class PersistentVolume {
    
	private String kind;
    
    private String apiVersion;
    
    private ObjectMeta metadata;
    
    private PersistentVolumeSpec spec;
    
    private PersistentVolumeStatus status;

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

	public PersistentVolumeSpec getSpec() {
		return spec;
	}

	public void setSpec(PersistentVolumeSpec spec) {
		this.spec = spec;
	}

	public PersistentVolumeStatus getStatus() {
		return status;
	}

	public void setStatus(PersistentVolumeStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "PersistentVolume "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion 
				+ ", metadata="+ metadata 
				+ ", spec="+spec
				+ ", status="+ status + "]";
	}
}
