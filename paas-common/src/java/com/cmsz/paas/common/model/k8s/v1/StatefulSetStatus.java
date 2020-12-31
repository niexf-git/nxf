package com.cmsz.paas.common.model.k8s.v1;

public class StatefulSetStatus {

	private int observedGeneration;
	
	private int replicas;

	public int getObservedGeneration() {
		return observedGeneration;
	}

	public void setObservedGeneration(int observedGeneration) {
		this.observedGeneration = observedGeneration;
	}

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}
	
	@Override
	public String toString(){
		return "StatefulSetStatus [observedGeneration=" + observedGeneration 
				+"replicas"+replicas+ "]";
	}
}
