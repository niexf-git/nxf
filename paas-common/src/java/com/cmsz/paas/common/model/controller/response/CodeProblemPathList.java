package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.CodeProblemPathEntity;

public class CodeProblemPathList {

	private List<CodeProblemPathEntity> codeProblemPathList;

	public List<CodeProblemPathEntity> getCodeProblemPathList() {
		return codeProblemPathList;
	}

	public void setCodeProblemPathList(
			List<CodeProblemPathEntity> codeProblemPathList) {
		this.codeProblemPathList = codeProblemPathList;
	}

	@Override
	public String toString() {
		return "CodeProblemPathList [codeProblemPathList="
				+ codeProblemPathList + "]";
	}

}
