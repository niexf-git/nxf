package com.cmsz.paas.common.model.controller.entity;

import java.util.List;

public class SonarProblemEntity {

	private String sonarkeyName; // sonar唯一标识

	private String problemType;// 问题类型

	private int problemNum;// 问题数量

	private List<CodeProblemPathEntity> sonarProblemResult;// sonar检查问题列表

	public String getSonarkeyName() {
		return sonarkeyName;
	}

	public void setSonarkeyName(String sonarkeyName) {
		this.sonarkeyName = sonarkeyName;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public List<CodeProblemPathEntity> getSonarProblemResult() {
		return sonarProblemResult;
	}

	public void setSonarProblemResult(
			List<CodeProblemPathEntity> sonarProblemResult) {
		this.sonarProblemResult = sonarProblemResult;
	}

	@Override
	public String toString() {
		return "SonarProblemEntity [sonarkeyName=" + sonarkeyName
				+ ", projectName=" + ", problemType=" + problemType
				+ ", problemNum=" + problemNum + ", sonarProblemResult="
				+ sonarProblemResult + "]";
	}

	public int getProblemNum() {
		return problemNum;
	}

	public void setProblemNum(int problemNum) {
		this.problemNum = problemNum;
	}

}
