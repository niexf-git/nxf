package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class EndpointSubset {

	private EndpointAddress[] addresses;
	
	private EndpointAddress[] notReadyAddresses;

	private EndpointPort[] ports;

	public EndpointAddress[] getAddresses() {
		return addresses;
	}

	public void setAddresses(EndpointAddress[] addresses) {
		this.addresses = addresses;
	}

	public EndpointAddress[] getNotReadyAddresses() {
		return notReadyAddresses;
	}

	public void setNotReadyAddresses(EndpointAddress[] notReadyAddresses) {
		this.notReadyAddresses = notReadyAddresses;
	}

	public EndpointPort[] getPorts() {
		return ports;
	}

	public void setPorts(EndpointPort[] ports) {
		this.ports = ports;
	}

	@Override
	public String toString() {
		return "EndpointSubset"
				+ " [addresses=" + Arrays.toString(addresses)
				+ ", notReadyAddresses=" + Arrays.toString(notReadyAddresses) 
				+ ", ports=" + Arrays.toString(ports) + "]";
	}
}
