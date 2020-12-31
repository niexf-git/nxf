package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

public class ResourceRequirements {

	private Map<String,String> limits;

	private Map<String,String> requests;

	public void setLimits(Map<String,String> limits) {
		this.limits = limits;
	}

	public Map<String,String> getLimits() {
		return limits;
	}

	public void setRequests(Map<String,String> requests) {
		this.requests = requests;
	}

	public Map<String,String> getRequests() {
		return requests;
	}

	@Override
	public String toString() {
		return "ResourceRequirements"
				+ " [limits=" + limits 
				+ ", requests=" + requests + "]";
	}
}
