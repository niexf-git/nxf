package com.cmsz.paas.common.model.k8s.v1;

public class PreferredSchedulingTerm {

	private int weight;
	
	private NodeSelectorTerm preference;
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public NodeSelectorTerm getPreference() {
		return preference;
	}

	public void setPreference(NodeSelectorTerm preference) {
		this.preference = preference;
	}

	@Override
	public String toString() {
		return "PreferredSchedulingTerm"
				+ " [weight=" + weight
				+ ", preference=" + preference + "]";
	}
	
}
