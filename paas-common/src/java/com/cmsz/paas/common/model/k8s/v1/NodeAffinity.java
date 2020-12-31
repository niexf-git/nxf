package com.cmsz.paas.common.model.k8s.v1;

public class NodeAffinity {
	
	private NodeSelector requiredDuringSchedulingIgnoredDuringExecution;
	
	private PreferredSchedulingTerm[] preferredDuringSchedulingIgnoredDuringExecution;
	
	public NodeSelector getRequiredDuringSchedulingIgnoredDuringExecution() {
		return requiredDuringSchedulingIgnoredDuringExecution;
	}

	public void setRequiredDuringSchedulingIgnoredDuringExecution(
			NodeSelector requiredDuringSchedulingIgnoredDuringExecution) {
		this.requiredDuringSchedulingIgnoredDuringExecution = requiredDuringSchedulingIgnoredDuringExecution;
	}

	public PreferredSchedulingTerm[] getPreferredDuringSchedulingIgnoredDuringExecution() {
		return preferredDuringSchedulingIgnoredDuringExecution;
	}

	public void setPreferredDuringSchedulingIgnoredDuringExecution(
			PreferredSchedulingTerm[] preferredDuringSchedulingIgnoredDuringExecution) {
		this.preferredDuringSchedulingIgnoredDuringExecution = preferredDuringSchedulingIgnoredDuringExecution;
	}

	@Override
	public String toString() {
		return "NodeAffinity"
				+ " [requiredDuringSchedulingIgnoredDuringExecution=" + requiredDuringSchedulingIgnoredDuringExecution
				+ ", preferredDuringSchedulingIgnoredDuringExecution=" + preferredDuringSchedulingIgnoredDuringExecution + "]";
	}

}
