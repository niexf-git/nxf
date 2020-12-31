package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class CodeAccountStatEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String id;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "CodeAccountStatEntity [path=" + path + ", id=" + id + "]";
	}	

}
