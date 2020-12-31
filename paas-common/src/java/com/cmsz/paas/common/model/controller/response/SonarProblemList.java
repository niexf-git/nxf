package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.SonarProblemEntity;

public class SonarProblemList {
	
	private SonarProblemEntity sonarProblem;

	public SonarProblemEntity getSonarProblem() {
		return sonarProblem;
	}
	
	public void setSonarProblem(SonarProblemEntity sonarProblem) {
		this.sonarProblem = sonarProblem;
	}
  
}
