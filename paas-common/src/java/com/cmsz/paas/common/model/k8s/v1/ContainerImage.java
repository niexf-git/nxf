package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class ContainerImage {

	private String [] names;
	
	private Long sizeBytes;
	
	public String[] getNames() {
		return names;
	}

	public void setNames(String [] names) {
		this.names = names;
	}

	public Long getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	@Override
	public String toString() {
		return "ContainerImage"
				+ " [names=" +Arrays.toString(names) 
				+ ", sizeBytes=" + sizeBytes + "]";
	}
	
}
