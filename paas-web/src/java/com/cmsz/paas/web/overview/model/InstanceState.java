package com.cmsz.paas.web.overview.model;

/**
 * 实例状态实体
 * 
 * @author zhuwei
 * @date 2018年5月8日
 */
public class InstanceState {
	// 运行中
	private String running;
	// 停止
	private String stopped;
	// 未知
	private String unknow;
	// 未调度
	private String unassigned;
	// 等待中
	private String waiting;

	public String getRunning() {
		return running;
	}

	public void setRunning(String running) {
		this.running = running;
	}

	public String getStopped() {
		return stopped;
	}

	public void setStopped(String stopped) {
		this.stopped = stopped;
	}

	public String getUnknow() {
		return unknow;
	}

	public void setUnknow(String unknow) {
		this.unknow = unknow;
	}

	public String getUnassigned() {
		return unassigned;
	}

	public void setUnassigned(String unassigned) {
		this.unassigned = unassigned;
	}

	public String getWaiting() {
		return waiting;
	}

	public void setWaiting(String waiting) {
		this.waiting = waiting;
	}

}
