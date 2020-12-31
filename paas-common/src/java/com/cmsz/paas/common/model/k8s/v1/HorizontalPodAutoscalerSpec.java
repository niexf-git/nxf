package com.cmsz.paas.common.model.k8s.v1;

public class HorizontalPodAutoscalerSpec {
	
	private int minReplicas;
  
  	private int maxReplicas;
  
  	private SubresourceReference scaleRef;
  	
  	private CPUTargetUtilization cpuUtilization;
  
	public int getMinReplicas() {
		return minReplicas;
	}

	public void setMinReplicas(int minReplicas) {
		this.minReplicas = minReplicas;
	}
	
	public int getMaxReplicas() {
		return maxReplicas;
	}
	
	public void setMaxReplicas(int maxReplicas) {
		this.maxReplicas = maxReplicas;
	}
	
	public SubresourceReference getScaleRef() {
		return scaleRef;
	}

	public void setScaleRef(SubresourceReference scaleRef) {
		this.scaleRef = scaleRef;
	}

	public CPUTargetUtilization getCpuUtilization() {
		return cpuUtilization;
	}

	public void setCpuUtilization(CPUTargetUtilization cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	
	public String toString(){
		return "HorizontalPodAutoscalerSpec[minReplicas=" + minReplicas + ",maxReplicas="+ maxReplicas 
				+ ",scaleRef=" + scaleRef + ",cpuUtilization=" + cpuUtilization +"]";
	}

}
