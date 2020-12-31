package com.cmsz.paas.web.overview.model;

/**
 * 实际使用资源利用率
 * 
 * @author zhuwei
 * @date 2018年5月8日
 */
public class ActualResourceRate {
	// 实际使用CPU
	private String usedCpu;
	// 总CPU
	private String totalCpu;
	// 实际使用内存
	private String usedMemory;
	// 总内存
	private String totalMemory;
	// 实际使用磁盘
	private String usedDisk;
	// 总磁盘大小
	private String totalDisk;

	public String getUsedCpu() {
		return usedCpu;
	}

	public void setUsedCpu(String usedCpu) {
		this.usedCpu = usedCpu;
	}

	public String getTotalCpu() {
		return totalCpu;
	}

	public void setTotalCpu(String totalCpu) {
		this.totalCpu = totalCpu;
	}

	public String getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(String usedMemory) {
		this.usedMemory = usedMemory;
	}

	public String getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}

	public String getUsedDisk() {
		return usedDisk;
	}

	public void setUsedDisk(String usedDisk) {
		this.usedDisk = usedDisk;
	}

	public String getTotalDisk() {
		return totalDisk;
	}

	public void setTotalDisk(String totalDisk) {
		this.totalDisk = totalDisk;
	}

}
