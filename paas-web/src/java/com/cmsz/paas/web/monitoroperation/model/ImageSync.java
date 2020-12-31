package com.cmsz.paas.web.monitoroperation.model;

/**
 * @author lin.my
 * @version 创建时间：2017年1月9日 下午7:15:31
 */
public class ImageSync {

	private String id; // harbor生成的job id

	private String status; // job状态

	private String repository; // 镜像名

	private String policyId; // 策略Id

	private String operation; // 操作

	// private Tags [] tags; //镜像版本
	private String imageVersion;
	
	private String dataCenter;

	private String creationTime; // 创建时间

	private String updateTime; // 更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public String getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(String dataCenter) {
		this.dataCenter = dataCenter;
	}

}
