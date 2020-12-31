package com.cmsz.paas.common.model.k8s.v1;

public class CPUTargetUtilization {
	
	private int targetPercentage;

	public int getTargetPercentage() {
		return targetPercentage;
	}

	public void setTargetPercentage(int targetPercentage) {
		this.targetPercentage = targetPercentage;
	}
	
}
