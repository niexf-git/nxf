package com.cmsz.paas.common.model.k8s.v1;

public class NodeSelector {
	
	private NodeSelectorTerm[] nodeSelectorTerms;
	
	public NodeSelectorTerm[] getNodeSelectorTerms() {
		return nodeSelectorTerms;
	}

	public void setNodeSelectorTerms(NodeSelectorTerm[] nodeSelectorTerms) {
		this.nodeSelectorTerms = nodeSelectorTerms;
	}

	@Override
	public String toString() {
		return "NodeSelector "
				+ "[nodeSelectorTerms=" + nodeSelectorTerms + "]";
	}

}
