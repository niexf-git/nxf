package com.cmsz.paas.web.cicd.model;

/**
 * 卡片公共属性实体
 * 
 * @author ccl
 * @date 2017-8-28
 */
public class StepDetailInfo {
	/** 状态，0-未执行，1-成功，2-失败 ，3-执行中，4-等待执行*/
	private String Status;
	/** 时间(单位:秒) */
	private String time;
	/** 是否选中，0-未选择，1-选中 */
	private String isChoise;

	public String getIsChoise() {
		return isChoise;
	}

	public void setIsChoise(String isChoise) {
		this.isChoise = isChoise;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
