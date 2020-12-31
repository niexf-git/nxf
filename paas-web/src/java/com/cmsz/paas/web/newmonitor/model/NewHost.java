package com.cmsz.paas.web.newmonitor.model;

/**
 * 主机实体类
 * 
 * @author liaohw
 * @date 2016-12-21
 */
public class NewHost {

	/** 主机id */
	private String id;

	/** 主机IP */
	private String hostIP;

	/** 部署方案id */
	private String deployId;

	/** root密码 */
	private String password;

	/** 状态 */
	private String status;

	public String getFloatIp() {
		return floatIp;
	}

	public void setFloatIp(String floatIp) {
		this.floatIp = floatIp;
	}

	/** 创建时间 */
	private String createTime;
	
	//浮动IP
	private String floatIp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Host [id=" + id + ", hostIP=" + hostIP + ", deployId="
				+ deployId + ", password=" + password + ", status=" + status
				+ ", createTime=" + createTime + "]";
	}

}
