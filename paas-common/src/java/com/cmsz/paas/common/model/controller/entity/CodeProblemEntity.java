package com.cmsz.paas.common.model.controller.entity;

import java.util.List;

public class CodeProblemEntity {

	private String code; // 问题代码

	private List<CodeProblemDetailEntity> codeProblemDetail;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<CodeProblemDetailEntity> getCodeProblemDetail() {
		return codeProblemDetail;
	}

	public void setCodeProblemDetail(
			List<CodeProblemDetailEntity> codeProblemDetail) {
		this.codeProblemDetail = codeProblemDetail;
	}

	@Override
	public String toString() {
		return "CodeProblemEntity [code=" + code + ", codeProblemDetail="
				+ codeProblemDetail + "]";
	}

}
