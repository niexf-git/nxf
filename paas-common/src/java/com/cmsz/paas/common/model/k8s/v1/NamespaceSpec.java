package com.cmsz.paas.common.model.k8s.v1;

public class NamespaceSpec {
	
	private String [] finalizers;
	
	public String[] getFinalizers() {
		return finalizers;
	}
	
	public void setFinalizers(String[] finalizers) {
		this.finalizers = finalizers;
	}
	
	@Override
	public String toString(){
		return " NamespaceSpec"
				+ "[finalizers =" + finalizers + "]";
		
	}
}
