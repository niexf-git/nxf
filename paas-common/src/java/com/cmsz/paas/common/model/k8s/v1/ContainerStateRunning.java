package com.cmsz.paas.common.model.k8s.v1;

public class ContainerStateRunning {
	private String startedAt;

	public String getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}

	@Override
	public String toString() {
		return "ContainerStateRunning "
				+ "[startedAt=" + startedAt + "]";
	}
}
