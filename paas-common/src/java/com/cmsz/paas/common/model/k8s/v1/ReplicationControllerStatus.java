package com.cmsz.paas.common.model.k8s.v1;

public class ReplicationControllerStatus {

	private int replicas;
	
	private int fullyLabeledReplicas;
	
	private int readyReplicas;
	
	private int availableReplicas;

	private Long observedGeneration;
	
	private ReplicationControllerCondition conditions;

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}

	public int getFullyLabeledReplicas() {
		return fullyLabeledReplicas;
	}

	public void setFullyLabeledReplicas(int fullyLabeledReplicas) {
		this.fullyLabeledReplicas = fullyLabeledReplicas;
	}

	public int getReadyReplicas() {
		return readyReplicas;
	}

	public void setReadyReplicas(int readyReplicas) {
		this.readyReplicas = readyReplicas;
	}

	public int getAvailableReplicas() {
		return availableReplicas;
	}

	public void setAvailableReplicas(int availableReplicas) {
		this.availableReplicas = availableReplicas;
	}

	public Long getObservedGeneration() {
		return observedGeneration;
	}

	public void setObservedGeneration(Long observedGeneration) {
		this.observedGeneration = observedGeneration;
	}

	public ReplicationControllerCondition getConditions() {
		return conditions;
	}

	public void setConditions(ReplicationControllerCondition conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toString() {
		return "ReplicationControllerStatus "
				+ "[replicas=" + replicas
				+ ", fullyLabeledReplicas=" + fullyLabeledReplicas
				+ ", readyReplicas=" + readyReplicas
				+ ", availableReplicas=" + availableReplicas
				+ ", observedGeneration=" + observedGeneration
				+ ", conditions=" + conditions + "]";
	}
}
