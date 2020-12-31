package com.cmsz.paas.web.cicd.model;

import java.util.List;

/**
 * 问题详情实体
 * 
 * @author ccl
 * @date 2017-8-31
 */
public class CodeProblemDetailsInfo {
	/** 问题代码  */
	private String code; 
	/** 问题代码详情集合  */
	private List<CodeProblemInfo> codeProblemList;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<CodeProblemInfo> getCodeProblemList() {
		return codeProblemList;
	}
	public void setCodeProblemList(List<CodeProblemInfo> codeProblemList) {
		this.codeProblemList = codeProblemList;
	}
	
	

}
