package com.cmsz.paas.common.model.k8s.v1;

public class NamespaceStatus {
	
	private String phase;

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	@Override
	public String toString(){
		return " NamespaceStatus"
				+ "[phase =" + phase + "]";
		
	}
}
