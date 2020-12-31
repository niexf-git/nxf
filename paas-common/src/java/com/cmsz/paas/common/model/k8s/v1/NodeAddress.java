package com.cmsz.paas.common.model.k8s.v1;

public class NodeAddress {

	private String type;

	private String address;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "NodeAddress"
				+ " [type=" + type
				+ ", address=" + address + "]";
	}
}
