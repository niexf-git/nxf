package com.cmsz.paas.web.monitoroperation.model;

/**
 * 告警条件model.
 * 
 * @author ccl
 */
public class SysAlarmConditionInfo {
	/** 内存百分比 */
	private String memory;

	/** cpu百分比 */
	private String cpu;

	/** 系统文件百分比 */
	private String filesystem;


	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getFilesystem() {
		return filesystem;
	}

	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}
}
