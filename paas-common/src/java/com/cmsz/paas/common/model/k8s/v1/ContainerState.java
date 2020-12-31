package com.cmsz.paas.common.model.k8s.v1;

public class ContainerState {

	private ContainerStateWaiting waiting;

	private ContainerStateRunning running;

	private ContainerStateTerminated terminated;

	public ContainerStateWaiting getWaiting() {
		return waiting;
	}

	public void setWaiting(ContainerStateWaiting waiting) {
		this.waiting = waiting;
	}

	public ContainerStateRunning getRunning() {
		return running;
	}

	public void setRunning(ContainerStateRunning running) {
		this.running = running;
	}

	public void setTerminated(ContainerStateTerminated terminated) {
		this.terminated = terminated;
	}

	public ContainerStateTerminated getTerminated() {
		return terminated;
	}

	@Override
	public String toString() {
		return "ContainerState "
				+ "[waiting=" + waiting 
				+ ", running=" + running
				+ ", terminated=" + terminated + "]";
	}
}
