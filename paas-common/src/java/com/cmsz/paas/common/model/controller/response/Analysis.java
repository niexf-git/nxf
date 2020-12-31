package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;

/**
 * @ClassName: Analysis.java
 * @Description: 
 *
 *
 * @author zhongmg
 * @date: 2017年11月23日 上午9:37:35
 * @version: v1.0
 */
public class Analysis implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**代码审查崩溃问题数*/
	private Integer checkBlocker;
	
	/**代码审查严重问题数*/
	private Integer checkCritical;
	
	/**代码审查主要问题数*/
	private String checkMajor;
	
	/**代码审查警告数*/
	private Integer checkIssues;

	public Integer getCheckBlocker() {
		return checkBlocker;
	}

	public void setCheckBlocker(Integer checkBlocker) {
		this.checkBlocker = checkBlocker;
	}

	public Integer getCheckCritical() {
		return checkCritical;
	}

	public void setCheckCritical(Integer checkCritical) {
		this.checkCritical = checkCritical;
	}

	public String getCheckMajor() {
		return checkMajor;
	}

	public void setCheckMajor(String checkMajor) {
		this.checkMajor = checkMajor;
	}

	public Integer getCheckIssues() {
		return checkIssues;
	}

	public void setCheckIssues(Integer checkIssues) {
		this.checkIssues = checkIssues;
	}
	
}
