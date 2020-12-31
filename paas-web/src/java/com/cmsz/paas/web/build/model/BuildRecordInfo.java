package com.cmsz.paas.web.build.model;

/**
 * 构建记录信息，主要用于构建记录列表展示
 * 
 * @author liaohw
 * @date 2016-4-1
 */
public class BuildRecordInfo {

	/** 唯一标识 */
	private String id;

	/** 构建名称，从参数中取数据 */
	private String name;

	/** 状态 */
	private String status;

	/** 镜像版本 */
	private String imageVersion;

	/** svn代码版本 */
	private String svnVersion;

	/** 构建开始时间 */
	private String startTime;

	/** 构建结束时间 */
	private String endTime;

	/** 操作用户 */
	private String operator;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public String getSvnVersion() {
		return svnVersion;
	}

	public void setSvnVersion(String svnVersion) {
		this.svnVersion = svnVersion;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "BuildRecordInfo [id=" + id + ", name=" + name + ", status="
				+ status + ", imageVersion=" + imageVersion + ", svnVersion="
				+ svnVersion + ", startTime=" + startTime + ", endTime="
				+ endTime + ", operator=" + operator + "]";
	}

}
