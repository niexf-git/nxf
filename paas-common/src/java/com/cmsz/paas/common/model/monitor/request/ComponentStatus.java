package com.cmsz.paas.common.model.monitor.request;

import java.util.List;

import com.cmsz.paas.common.model.agent.entity.ComponentEntity;




public class ComponentStatus {
	
	private List<ComponentEntity> componentEntityList;
	private String nodeIP;
	private String totalMemory;
	private String usageMemory;
	private String cpu;
	private String totalCpu;
	private String usageCpu;
	private String totalFs;
	private String usageFs;
	private long  time;
	
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getNodeIP() {
		return nodeIP;
	}
	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}
	
	

	public List<ComponentEntity> getComponentEntityList() {
		return componentEntityList;
	}
	public void setComponentEntityList(List<ComponentEntity> componentEntityList) {
		this.componentEntityList = componentEntityList;
	}
	public String getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}
	public String getUsageMemory() {
		return usageMemory;
	}
	public void setUsageMemory(String usageMemory) {
		this.usageMemory = usageMemory;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getTotalFs() {
		return totalFs;
	}
	public void setTotalFs(String totalFs) {
		this.totalFs = totalFs;
	}
	public String getUsageFs() {
		return usageFs;
	}
	public void setUsageFs(String usageFs) {
		this.usageFs = usageFs;
	}
	public String getTotalCpu() {
		return totalCpu;
	}
	public void setTotalCpu(String totalCpu) {
		this.totalCpu = totalCpu;
	}
	public String getUsageCpu() {
		return usageCpu;
	}
	public void setUsageCpu(String usageCpu) {
		this.usageCpu = usageCpu;
	}
	@Override
	public String toString() {
		return "ComponentStatus [componentEntityList=" + componentEntityList
				+ ", nodeIP=" + nodeIP + ", totalMemory=" + totalMemory
				+ ", usageMemory=" + usageMemory + ", cpu=" + cpu
				+ ", totalCpu=" + totalCpu + ", usageCpu=" + usageCpu
				+ ", totalFs=" + totalFs + ", usageFs=" + usageFs + ", time="
				+ time + "]";
	}
	
	
}
