package com.cmsz.paas.common.model.k8s.v1;

public class WeightedPodAffinityTerm {

	private int weight;
	
	private PodAffinityTerm podAffinityTerm;
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public PodAffinityTerm getPodAffinityTerm() {
		return podAffinityTerm;
	}

	public void setPodAffinityTerm(PodAffinityTerm podAffinityTerm) {
		this.podAffinityTerm = podAffinityTerm;
	}

	@Override
	public String toString() {
		return "WeightedPodAffinityTerm"
				+ " [weight=" + weight
				+ ", podAffinityTerm=" + podAffinityTerm + "]";
	}
}
