package com.cmsz.paas.web.overview.model;

/**
 * 已分配资源利用率实体
 * 
 * @author zhuwei
 * @date 2018年5月8日
 */
public class AssignedResourceRate {
	// 已分配CPU
	private String assignedCpu;
	// 总CPU
	private String totalCpu;
	// 已分配内存
	private String assignedMemory;
	// 总内存
	private String totalMemory;

	public String getAssignedCpu() {
		return assignedCpu;
	}

	public void setAssignedCpu(String assignedCpu) {
		this.assignedCpu = assignedCpu;
	}

	public String getTotalCpu() {
		return totalCpu;
	}

	public void setTotalCpu(String totalCpu) {
		this.totalCpu = totalCpu;
	}

	public String getAssignedMemory() {
		return assignedMemory;
	}

	public void setAssignedMemory(String assignedMemory) {
		this.assignedMemory = assignedMemory;
	}

	public String getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}

}
