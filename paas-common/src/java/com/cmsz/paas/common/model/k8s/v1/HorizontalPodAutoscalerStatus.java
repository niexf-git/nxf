package com.cmsz.paas.common.model.k8s.v1;

import java.util.Date;

public class HorizontalPodAutoscalerStatus {
	private Long observedGeneration;
	 
	private Date lastScaleTime;
	
	private int currentReplicas;
	
    private int desiredReplicas;
	
	private int currentCPUUtilizationPercentage;
	
	public Long getObservedGeneration() {
		return observedGeneration;
	}

	public void setObservedGeneration(Long observedGeneration) {
		this.observedGeneration = observedGeneration;
	}

	public Date getLastScaleTime() {
		return lastScaleTime;
	}

	public void setLastScaleTime(Date lastScaleTime) {
		this.lastScaleTime = lastScaleTime;
	}

	public int getCurrentReplicas() {
		return currentReplicas;
	}

	public void setCurrentReplicas(int currentReplicas) {
		this.currentReplicas = currentReplicas;
	}

	public int getDesiredReplicas() {
		return desiredReplicas;
	}

	public void setDesiredReplicas(int desiredReplicas) {
		this.desiredReplicas = desiredReplicas;
	}

	public int getCurrentCPUUtilizationPercentage() {
		return currentCPUUtilizationPercentage;
	}

	public void setCurrentCPUUtilizationPercentage(
			int currentCPUUtilizationPercentage) {
		this.currentCPUUtilizationPercentage = currentCPUUtilizationPercentage;
	}
	
	public String toString(){
		return "HorizontalPodAutoscalerStatus[observedGeneration ="+ observedGeneration + ",lastScaleTime =" + lastScaleTime 
				+ ",currentReplicas =" + currentReplicas + ",desiredReplicas =" + desiredReplicas 
				+ ",currentCPUUtilizationPercentage ="+ currentCPUUtilizationPercentage +"]";
		
	}
	
}
