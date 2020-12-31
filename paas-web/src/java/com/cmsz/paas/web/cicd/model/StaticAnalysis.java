package com.cmsz.paas.web.cicd.model;

/***
 * 静态代码分析指标
 * @author jiangwei
 *
 */
public class StaticAnalysis {

	/**
	 * 代码审查崩溃问题数
	 */
	private String checkBlocker;
	
	/**
	 * 代码审查严重问题数
	 */
	private String checkCritical;
	
	/**
	 * 代码审查主要问题数
	 */
	private String checkMajor;
	
	/**
	 * 代码审查警告数
	 */
	private String checkIssues;

	public String getCheckBlocker() {
		return checkBlocker;
	}

	public void setCheckBlocker(String checkBlocker) {
		this.checkBlocker = checkBlocker;
	}

	public String getCheckCritical() {
		return checkCritical;
	}

	public void setCheckCritical(String checkCritical) {
		this.checkCritical = checkCritical;
	}

	public String getCheckMajor() {
		return checkMajor;
	}

	public void setCheckMajor(String checkMajor) {
		this.checkMajor = checkMajor;
	}

	public String getCheckIssues() {
		return checkIssues;
	}

	public void setCheckIssues(String checkIssues) {
		this.checkIssues = checkIssues;
	}
	
	
}
