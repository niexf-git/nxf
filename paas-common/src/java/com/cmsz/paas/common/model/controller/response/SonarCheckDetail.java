package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.SonarCheckEntity;

public class SonarCheckDetail {
	
	private SonarCheckEntity sonarCheck;

	public SonarCheckEntity getSonarCheck() {
		return sonarCheck;
	}

	public void setSonarCheck(SonarCheckEntity sonarCheck) {
		this.sonarCheck = sonarCheck;
	}
	
}
