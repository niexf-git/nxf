package com.cmsz.paas.common.model.k8s.v1;

public class PodAntiAffinity {
	
	private PodAffinityTerm[] requiredDuringSchedulingIgnoredDuringExecution;
	
	private WeightedPodAffinityTerm[] preferredDuringSchedulingIgnoredDuringExecution;
	
	public PodAffinityTerm[] getRequiredDuringSchedulingIgnoredDuringExecution() {
		return requiredDuringSchedulingIgnoredDuringExecution;
	}

	public void setRequiredDuringSchedulingIgnoredDuringExecution(
			PodAffinityTerm[] requiredDuringSchedulingIgnoredDuringExecution) {
		this.requiredDuringSchedulingIgnoredDuringExecution = requiredDuringSchedulingIgnoredDuringExecution;
	}

	public WeightedPodAffinityTerm[] getPreferredDuringSchedulingIgnoredDuringExecution() {
		return preferredDuringSchedulingIgnoredDuringExecution;
	}

	public void setPreferredDuringSchedulingIgnoredDuringExecution(
			WeightedPodAffinityTerm[] preferredDuringSchedulingIgnoredDuringExecution) {
		this.preferredDuringSchedulingIgnoredDuringExecution = preferredDuringSchedulingIgnoredDuringExecution;
	}

	@Override
	public String toString() {
		return "PodAntiAffinity"
				+ " [requiredDuringSchedulingIgnoredDuringExecution=" + requiredDuringSchedulingIgnoredDuringExecution
				+ ", preferredDuringSchedulingIgnoredDuringExecution=" + preferredDuringSchedulingIgnoredDuringExecution + "]";
	}

}
