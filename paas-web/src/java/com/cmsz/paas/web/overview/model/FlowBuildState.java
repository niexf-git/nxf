package com.cmsz.paas.web.overview.model;

/**
 * 流水构建状态实体
 * 
 * @author zhuwei
 * @date 2018年5月8日
 */
public class FlowBuildState {
	// 正在构建
	private String working;
	// 构建成功
	private String success;
	// 构建失败
	private String fail;
	// 未执行
	private String unexecuted;

	public String getWorking() {
		return working;
	}

	public void setWorking(String working) {
		this.working = working;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getUnexecuted() {
		return unexecuted;
	}

	public void setUnexecuted(String unexecuted) {
		this.unexecuted = unexecuted;
	}

}
