package com.cmsz.paas.common.model.k8s.v1;

public class K8sService {

	private String kind;

	private String apiVersion;

	private ObjectMeta metadata;

	private ServiceSpec spec;

	private ServiceStatus status;

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

	public ServiceSpec getSpec() {
		return spec;
	}

	public void setSpec(ServiceSpec spec) {
		this.spec = spec;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Service "
				+ "[kind=" + kind
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata 
				+ ", spec=" + spec
				+ ", status=" + status + "]";
	}

}
