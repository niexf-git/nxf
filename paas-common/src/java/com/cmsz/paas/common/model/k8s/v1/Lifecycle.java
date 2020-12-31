package com.cmsz.paas.common.model.k8s.v1;

public class Lifecycle {

	private Handler postStart;

	private Handler preStop;

	public Handler getPostStart() {
		return postStart;
	}

	public void setPostStart(Handler postStart) {
		this.postStart = postStart;
	}

	public Handler getPreStop() {
		return preStop;
	}

	public void setPreStop(Handler preStop) {
		this.preStop = preStop;
	}

	@Override
	public String toString() {
		return "Lifecycle "
				+ "[postStart=" + postStart 
				+ ", preStop=" + preStop + "]";
	}
}
