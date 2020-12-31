package com.cmsz.paas.web.cicd.model;
/**
 * 问题详情实体
 * 
 * @author ccl
 * @date 2017-8-31
 */
public class CodeProblemInfo {
	/** 问题种类  */
	private String problemType;
	/** 问题具体行数  */
	private int line;
	/** 问题描述  */
	private String description;
	/** 问题标题  */
	private String message;
	/** 代码路径  */
	private String codepath;
	
	public String getProblemType() {
		return problemType;
	}
	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	
	public String getDescription() {
		return description;
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
	public String getCodepath() {
		return codepath;
	}
	public void setCodepath(String codepath) {
		this.codepath = codepath;
	}

}
