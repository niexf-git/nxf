package com.cmsz.paas.common.model.k8s.v1;

public class PodTemplateSpec {

	private ObjectMeta metadata;

	private PodSpec spec;

	public void setMetadata(ObjectMeta metadata) {
		this.metadata = metadata;
	}

	public ObjectMeta getMetadata() {
		return metadata;
	}

	public void setSpec(PodSpec spec) {
		this.spec = spec;
	}

	public PodSpec getSpec() {
		return spec;
	}

	@Override
	public String toString() {
		return "PodTemplateSpec "
				+ "[metadata=" + metadata 
				+ ", spec=" + spec + "]";
	}
}
