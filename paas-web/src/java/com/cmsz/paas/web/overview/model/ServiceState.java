package com.cmsz.paas.web.overview.model;

/**
 * 服务状态实体
 * 
 * @author liaohw
 * @date 2018-5-8
 */
public class ServiceState {

	// 运行中
	private String working;

	// 已停止
	private String stopped;

	// 操作中
	private String operationing;

	public String getWorking() {
		return working;
	}

	public void setWorking(String working) {
		this.working = working;
	}

	public String getStopped() {
		return stopped;
	}

	public void setStopped(String stopped) {
		this.stopped = stopped;
	}

	public String getOperationing() {
		return operationing;
	}

	public void setOperationing(String operationing) {
		this.operationing = operationing;
	}

}
