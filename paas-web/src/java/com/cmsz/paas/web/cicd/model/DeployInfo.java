package com.cmsz.paas.web.cicd.model;

/***
 * 部署信息
 * @author jiangwei
 *
 */
public class DeployInfo {

	/***
	 * 状态
	 */
	private String statu;
	
	/***
	 * 镜像版本
	 */
	private String imageVersion;
	
	/***
	 * 时间
	 */
	private String time;

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
