package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * deploy
 * 
 * @author lixiaofu
 * 
 * @date 2016-12-21
 */

public class DeployEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	private long scheme_id;
	private String components;
	private long hainfo;
	private String description;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getScheme_id() {
		return scheme_id;
	}
	public void setScheme_id(long scheme_id) {
		this.scheme_id = scheme_id;
	}
	public String getComponents() {
		return components;
	}
	public void setComponents(String components) {
		this.components = components;
	}
	public long getHainfo() {
		return hainfo;
	}
	public void setHainfo(long hainfo) {
		this.hainfo = hainfo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "DeployEntity [id=" + id + ", scheme_id=" + scheme_id
				+ ", components=" + components + ", hainfo=" + hainfo
				+ ", description=" + description + "]";
	}

	

}
