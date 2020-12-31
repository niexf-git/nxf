package com.cmsz.paas.common.model.k8s.v1;

public class ResourceFieldSelector {

	private String containerName;
	
	private String resource;
	
	//private Quantity divisor;
	
	@Override
	public String toString() {
		return "ResourceFieldSelector "
				+ "[containerName=" + containerName 
				+ ", resource=" + resource  + "]";
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
}
