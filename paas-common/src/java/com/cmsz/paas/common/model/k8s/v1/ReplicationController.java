package com.cmsz.paas.common.model.k8s.v1;

public class ReplicationController {

	private String kind;

	private String apiVersion;

	private ObjectMeta metadata;

	private ReplicationControllerSpec spec;

	private ReplicationControllerStatus status;


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

	public ReplicationControllerSpec getSpec() {
		return spec;
	}

	public void setSpec(ReplicationControllerSpec spec) {
		this.spec = spec;
	}

	public ReplicationControllerStatus getStatus() {
		return status;
	}

	public void setStatus(ReplicationControllerStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReplicationController "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion 
				+ ", metadata=" + metadata 
				+ ", spec=" + spec
				+ ", status=" + status + "]";
	}

}
