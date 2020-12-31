package com.cmsz.paas.common.model.agent.response;

import java.util.List;

import com.cmsz.paas.common.model.agent.entity.ComponentEntity;

public class HostInfo {
	
	//单位:MB
	private String nodeIp;
	
	private long totalMemory;
	
	private long usageMemory;
	
	private int cpu;
	
	private long totalFs;
	
	private long usageFs;
	
	private List<ComponentEntity> componentList;

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getUsageMemory() {
		return usageMemory;
	}

	public void setUsageMemory(long usageMemory) {
		this.usageMemory = usageMemory;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public long getTotalFs() {
		return totalFs;
	}

	public void setTotalFs(long totalFs) {
		this.totalFs = totalFs;
	}

	public long getUsageFs() {
		return usageFs;
	}

	public void setUsageFs(long usageFs) {
		this.usageFs = usageFs;
	}
	
	
	public List<ComponentEntity> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<ComponentEntity> componentList) {
		this.componentList = componentList;
	}

	@Override
	public String toString() {
		return "HostInfo:"+"[ "
	            +"nodeIp"+nodeIp
	            +", totalMemory"+ totalMemory
	            +", usageMemory"+ usageMemory
	            +", cpu"+ cpu
	            +", totalFs"+ totalFs
	            +", usageFs"+ usageFs
	            +", componentList"+ componentList + " ]";
	}
	

}
