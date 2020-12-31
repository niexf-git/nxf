package com.cmsz.paas.common.model.k8s.v1;

public class ServiceStatus {

	private LoadBalancerStatus loadBalancer;

	public void setLoadBalancer(LoadBalancerStatus loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	public LoadBalancerStatus getLoadBalancer() {
		return loadBalancer;
	}

	@Override
	public String toString() {
		return "ServiceStatus "
				+ "[loadBalancer=" + loadBalancer + "]";
	}
}
