package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class LoadBalancerStatus {

	private LoadBalancerIngress[] ingress;

	public void setIngress(LoadBalancerIngress[] ingress) {
		this.ingress = ingress;
	}

	public LoadBalancerIngress[] getIngress() {
		return ingress;
	}

	@Override
	public String toString() {
		return "LoadBalancerStatus "
				+ "[ingress=" + Arrays.toString(ingress) + "]";
	}
}
