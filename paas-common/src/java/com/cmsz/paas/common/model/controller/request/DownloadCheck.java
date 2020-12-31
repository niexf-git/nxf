package com.cmsz.paas.common.model.controller.request;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.CodePathEntity;

public class DownloadCheck {
	private String flowId;// 流水Id
	private int codeType;// 代码库类型（1-svn，2-git）
	private int triggerType;// 触发类型（0-手动，1-自动,2-定时）
	private String pollScm;// 定时配置信息,
	
	private String isCheck;// 是否代码检查（0-是，1-否）
	
	private List<CodePathEntity> codes;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public int getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(int triggerType) {
		this.triggerType = triggerType;
	}

	public String getPollScm() {
		return pollScm;
	}

	public void setPollScm(String pollScm) {
		this.pollScm = pollScm;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public List<CodePathEntity> getCodes() {
		return codes;
	}

	public void setCodes(List<CodePathEntity> codes) {
		this.codes = codes;
	}

	@Override
	public String toString() {
		return "DownloadCheck [flowId=" + flowId + ", codeType=" + codeType
				+ ", triggerType=" + triggerType + ", pollScm=" + pollScm
				+ ", isCheck=" + isCheck + ", codes=" + codes + "]";
	}
	
}
