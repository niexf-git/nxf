package com.cmsz.paas.common.model.k8s.v1;

public class Affinity {

	private NodeAffinity nodeAffinity;
	
	private PodAffinity podAffinity;
	
	private PodAntiAffinity podAntiAffinity;
	
	public NodeAffinity getNodeAffinity() {
		return nodeAffinity;
	}

	public void setNodeAffinity(NodeAffinity nodeAffinity) {
		this.nodeAffinity = nodeAffinity;
	}

	public PodAffinity getPodAffinity() {
		return podAffinity;
	}

	public void setPodAffinity(PodAffinity podAffinity) {
		this.podAffinity = podAffinity;
	}

	public PodAntiAffinity getPodAntiAffinity() {
		return podAntiAffinity;
	}

	public void setPodAntiAffinity(PodAntiAffinity podAntiAffinity) {
		this.podAntiAffinity = podAntiAffinity;
	}

	@Override
	public String toString() {
		return "Affinity"
				+ " [nodeAffinity=" + nodeAffinity 
				+ ", podAffinity=" + podAffinity
				+ ", podAntiAffinity=" + podAntiAffinity + "]";
	}
}
