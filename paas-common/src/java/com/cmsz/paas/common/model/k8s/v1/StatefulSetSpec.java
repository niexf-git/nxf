package com.cmsz.paas.common.model.k8s.v1;

import edu.emory.mathcs.backport.java.util.Arrays;

public class StatefulSetSpec {

	private int replicas;
	
	private LabelSelector selector;
	
	private PodTemplateSpec template;
	
	private PersistentVolumeClaim[] volumeClaimTemplates;
	
	private String serviceName;

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}

	public LabelSelector getSelector() {
		return selector;
	}

	public void setSelector(LabelSelector selector) {
		this.selector = selector;
	}

	public PodTemplateSpec getTemplate() {
		return template;
	}

	public void setTemplate(PodTemplateSpec template) {
		this.template = template;
	}

	public PersistentVolumeClaim[] getVolumeClaimTemplates() {
		return volumeClaimTemplates;
	}

	public void setVolumeClaimTemplates(PersistentVolumeClaim[] volumeClaimTemplates) {
		this.volumeClaimTemplates = volumeClaimTemplates;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Override
	public String toString() {
		return "StatefulSetSpec [replicas=" + replicas + ", selector=" + selector
				+ ", template=" + template + ", volumeClaimTemplates=" 
				+ Arrays.toString(volumeClaimTemplates) + ", serviceName=" + serviceName + "]";
	}
}
