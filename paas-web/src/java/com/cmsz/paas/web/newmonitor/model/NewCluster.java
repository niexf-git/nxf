package com.cmsz.paas.web.newmonitor.model;

/**
 * 集群
 * 
 * @author lin.my
 * @version 创建时间：2016年12月21日 上午11:20:28
 */
public class NewCluster {

	private String id; // 集群Id

	private String name; // 集群名称

	private String label; // 集群label

	private String description;

	private String type; // 集群类型，包括1.ipaas集群，2.apaas集群，3.paas平台（3.副中心，其它：主中心）
	
	private String dataCenterId; // 数据中心Id
	
	private String insertTime; // 创建时间

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

}
