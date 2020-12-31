package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

public class ReplicationControllerSpec {

	private int replicas;
	
	private int minReadySeconds;

	private Map<String,String> selector;

	private PodTemplateSpec template;

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}

	public int getMinReadySeconds() {
		return minReadySeconds;
	}

	public void setMinReadySeconds(int minReadySeconds) {
		this.minReadySeconds = minReadySeconds;
	}

	public Map<String, String> getSelector() {
		return selector;
	}

	public void setSelector(Map<String, String> selector) {
		this.selector = selector;
	}

	public PodTemplateSpec getTemplate() {
		return template;
	}

	public void setTemplate(PodTemplateSpec template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "ReplicationControllerSpec "
				+ "[replicas=" + replicas
				+ ", minReadySeconds=" +minReadySeconds
				+ ", selector=" + selector 
				+ ", template=" + template + "]";
	}
}
