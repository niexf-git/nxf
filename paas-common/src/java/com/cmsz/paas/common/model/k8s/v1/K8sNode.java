package com.cmsz.paas.common.model.k8s.v1;

public class K8sNode {

	private String kind;

	private String apiVersion;

	private ObjectMeta metadata;

	private NodeSpec spec;

	private NodeStatus status;

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

	public NodeSpec getSpec() {
		return spec;
	}

	public void setSpec(NodeSpec spec) {
		this.spec = spec;
	}

	public NodeStatus getStatus() {
		return status;
	}

	public void setStatus(NodeStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Node "
				+ "[kind=" + kind 
				+ ", apiVersion=" + apiVersion
				+ ", metadata=" + metadata 
				+ ", spec=" + spec 
				+ ", status=" + status + "]";
	}
}
