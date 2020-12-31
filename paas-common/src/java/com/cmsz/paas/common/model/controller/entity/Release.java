package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

/**
 * @time 2017-8-24
 * @author guyj
 *
 */
public class Release  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int isChoise; //是否选中，0-未选择，1-选中
	
	private int status; //状态，空-未执行，0-成功。1-未成功
	
	private double time; //时间,秒
	
	private String imageName;//镜像名称	
	
	private String imageVersion;//镜像版本
	
	private String versionNum;//正式版本号
	
	private int type;//发布类型，2-测试 3-生产
	
	private int execMode;//0-手动 1-自动
	
	public int getIsChoise() {
		return isChoise;
	}
	public void setIsChoise(int isChoise) {
		this.isChoise = isChoise;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageVersion() {
		return imageVersion;
	}
	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}
	
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getExecMode() {
		return execMode;
	}
	public void setExecMode(int execMode) {
		this.execMode = execMode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
