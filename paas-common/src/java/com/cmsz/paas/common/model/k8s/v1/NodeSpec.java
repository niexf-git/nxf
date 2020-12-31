package com.cmsz.paas.common.model.k8s.v1;

public class NodeSpec {

	private String podCIDR;

	private String externalID;

	private String providerID;

	private boolean unschedulable;

	public String getPodCIDR() {
		return podCIDR;
	}

	public void setPodCIDR(String podCIDR) {
		this.podCIDR = podCIDR;
	}

	public String getExternalID() {
		return externalID;
	}

	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	public String getProviderID() {
		return providerID;
	}

	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}

	public boolean isUnschedulable() {
		return unschedulable;
	}

	public void setUnschedulable(boolean unschedulable) {
		this.unschedulable = unschedulable;
	}

	@Override
	public String toString() {
		return "NodeSpec"
				+ " [podCIDR=" + podCIDR 
				+ ", externalID=" + externalID
				+ ", providerID=" + providerID 
				+ ", unschedulable=" + unschedulable + "]";
	}
}
