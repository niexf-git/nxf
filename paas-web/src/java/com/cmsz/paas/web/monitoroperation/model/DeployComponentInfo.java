package com.cmsz.paas.web.monitoroperation.model;

/**
 * 部署组件
 * 
 * @author liaohw
 * @date 2016-01-03
 */
public class DeployComponentInfo {
	// 唯一编号
	private String id;

	// 组件名称用逗号分隔
	private String components;

	// 描述
	private String description;
	
	//ha
	private long hainfo;

	public long getHainfo() {
		return hainfo;
	}

	public void setHainfo(long hainfo) {
		this.hainfo = hainfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
