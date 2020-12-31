package com.cmsz.paas.web.overview.model;

/**
 * 资源统计详细实体
 * @author heym 2018-05-08
 *
 */

public class TotalResource {
	
	/** 主机 */
	private String host;
	
	/** cpu */
	private String cpu;
	
	/** 内存 */
	private String memory;
	
	/** 磁盘 */
	private String disk;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}
	


	
}
