package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;
import java.util.Map;

public class LabelSelector {
	
	private Map<String,String> matchLabels;
	
	private LabelSelectorRequirement[] matchExpressions;

	public Map<String, String> getMatchLabels() {
		return matchLabels;
	}

	public void setMatchLabels(Map<String, String> matchLabels) {
		this.matchLabels = matchLabels;
	}

	public LabelSelectorRequirement[] getMatchExpressions() {
		return matchExpressions;
	}

	public void setMatchExpressions(LabelSelectorRequirement[] matchExpressions) {
		this.matchExpressions = matchExpressions;
	}
	
	@Override
	public String toString() {
		return "LabelSelector "
				+ "[matchLabels="+ matchLabels
				+", matchExpressions="+Arrays.toString(matchExpressions) + "]";
	}
}
