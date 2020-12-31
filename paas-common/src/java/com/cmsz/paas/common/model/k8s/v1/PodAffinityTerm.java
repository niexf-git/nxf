package com.cmsz.paas.common.model.k8s.v1;

public class PodAffinityTerm {

	private LabelSelector labelSelector;
	
	private String namespaces;
	
	private String topologyKey;
	
	public LabelSelector getLabelSelector() {
		return labelSelector;
	}

	public void setLabelSelector(LabelSelector labelSelector) {
		this.labelSelector = labelSelector;
	}

	public String getNamespaces() {
		return namespaces;
	}

	public void setNamespaces(String namespaces) {
		this.namespaces = namespaces;
	}

	public String getTopologyKey() {
		return topologyKey;
	}

	public void setTopologyKey(String topologyKey) {
		this.topologyKey = topologyKey;
	}

	@Override
	public String toString() {
		return "PodAffinityTerm"
				+ " [labelSelector=" + labelSelector
				+ ", namespaces=" + namespaces
				+ ", topologyKey=" + topologyKey + "]";
	}
}
