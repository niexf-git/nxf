package com.cmsz.vo;

import java.io.Serializable;
/**
 * 部门定义
 * 
 * @author 林绵炎 
 * @version 创建时间：2016年11月18日 下午5:26:53
 */
public class DepartmentVO implements Serializable {

	private static final long serialVersionUID = 5551917933888255793L;

	private String departmentId; // （部门标识）
	
	private String departmentname; // （部门名称）
	
	private String note; // 备注

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}