package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class ReleaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String flowId;
	
	private String versionNum;//正式版本号
	
	private int type;//发布类型，2-测试 3-生产
	
	private String imageName;//镜像名
	
	private int execMode;//0-手动 1-自动
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getExecMode() {
		return execMode;
	}

	public void setExecMode(int execMode) {
		this.execMode = execMode;
	}
}
