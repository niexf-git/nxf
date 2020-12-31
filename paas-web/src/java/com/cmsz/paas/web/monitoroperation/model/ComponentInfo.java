package com.cmsz.paas.web.monitoroperation.model;

/**
 * 组件model.
 * 
 * @author ccl
 */
public class ComponentInfo {
	/** 组件名称 */
	private String name;

	/** 组件状态 */
	private String status;
	/** 组件ID */
	private long id;
	/** 节点ID */
	private long nodeId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
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
}
