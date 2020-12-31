package com.cmsz.paas.web.ipaasinstance.model;
/**
 * 实例bean.
 * 
 * @author ccl
 * @date 2016-5-3
 */
public class IpaasInstance {
	/** 实例id(podId) */
	private String instanceId;

	/** 容器id */
	private String containerId;

	/** 状态 */
	private String status;

	/** 创建时间 */
	private String createTime;

	/** 上一次启动时间 */
	private String lastTime;

	/** 节点IP */
	private String hostIP;

	/** 重启次数 */
	private String restartNum;

	/** 状态图片的提示信息 */
	private String suggestMsg;
	
	/** sshUrl*/
	private String websshUrl;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
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

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public String getRestartNum() {
		return restartNum;
	}

	public void setRestartNum(String restartNum) {
		this.restartNum = restartNum;
	}

	public String getSuggestMsg() {
		return suggestMsg;
	}

	public void setSuggestMsg(String suggestMsg) {
		this.suggestMsg = suggestMsg;
	}
	
	public String getWebsshUrl() {
		return websshUrl;
	}

	public void setWebsshUrl(String websshUrl) {
		this.websshUrl = websshUrl;
	}

	@Override
	public String toString() {
		return "Instance [instanceId=" + instanceId + ", containerId="
				+ containerId + ", status=" + status + ", createTime="
				+ createTime + ", lastTime=" + lastTime + ", hostIP=" + hostIP
				+ ", restartNum=" + restartNum + "]";
	}
}
