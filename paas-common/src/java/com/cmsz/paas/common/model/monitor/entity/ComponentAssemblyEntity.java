package com.cmsz.paas.common.model.monitor.entity;

import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author jiayz
 * 2016-12-20
 */
public class ComponentAssemblyEntity {
	private static final long serialVersionUID = 1L;
	@ID
	private long id;
	private long nodeId; 
	private String componentName;
	private String componentStatus;
	private Date time;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ComponentEntity [id=" + id + ", nodeId=" + nodeId
				+ ", componentName=" + componentName + ", componentStatus="
				+ componentStatus + ", time=" + time + "]";
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getComponentStatus() {
		return componentStatus;
	}
	public void setComponentStatus(String componentStatus) {
		this.componentStatus = componentStatus;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
 