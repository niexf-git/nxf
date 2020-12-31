package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * NodeMonitorEntity
 * 
 * @author 
 * 
 * @date 2017-01-01
 */

public class NodeMonitorEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	private String ip;
	private String totalMemory;
	private String usageMemory;
	private String totalFs;
	private String usageFs;
	private String cpu;
	private String totalCpu;
	private String usageCpu;
	private Date time;
	
	private long hostCount;//主机数求和
	private long cpuCount;//CPU求和
	private long memoryCount;//内存求和
	private long diskCount;//磁盘求和
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public long getHostCount() {
		return hostCount;
	}
	public void setHostCount(long hostCount) {
		this.hostCount = hostCount;
	}
	public long getCpuCount() {
		return cpuCount;
	}
	public void setCpuCount(long cpuCount) {
		this.cpuCount = cpuCount;
	}
	public long getMemoryCount() {
		return memoryCount;
	}
	public void setMemoryCount(long memoryCount) {
		this.memoryCount = memoryCount;
	}
	public long getDiskCount() {
		return diskCount;
	}
	public void setDiskCount(long diskCount) {
		this.diskCount = diskCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "NodeMonitorEntity [id=" + id + ", ip=" + ip + ", totalMemory=" + totalMemory + ", usageMemory="
				+ usageMemory + ", totalFs=" + totalFs + ", usageFs=" + usageFs + ", cpu=" + cpu + ", totalCpu="
				+ totalCpu + ", usageCpu=" + usageCpu + ", time=" + time + ", hostCount=" + hostCount + ", cpuCount="
				+ cpuCount + ", memoryCount=" + memoryCount + ", diskCount=" + diskCount + "]";
	}
	
	
	
	

	
}
