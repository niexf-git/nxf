package com.cmsz.paas.common.model.k8s.v1;

public class NodeSelectorTerm {
	
	private NodeSelectorRequirement[] matchExpressions;
	
	public NodeSelectorRequirement[] getMatchExpressions() {
		return matchExpressions;
	}

	public void setMatchExpressions(NodeSelectorRequirement[] matchExpressions) {
		this.matchExpressions = matchExpressions;
	}

	@Override
	public String toString() {
		return "NodeSelectorTerm "
				+ "[matchExpressions=" + matchExpressions + "]";
	}

}
