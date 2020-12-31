package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class CodeProblemDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String titleName;// 问题种类
	private int line;// 问题具体行数
	private String description;// 问题信息
	private String message;// 问题描述

	public String getDescription() {
		return description;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

}
