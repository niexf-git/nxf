package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.ReleaseEntity;

public class Release {
	
	private ReleaseEntity release;

	public ReleaseEntity getRelease() {
		return release;
	}

	public void setRelease(ReleaseEntity release) {
		this.release = release;
	}
}
