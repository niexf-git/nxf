package com.cmsz.paas.common.model.k8s.v1;

public class ContainerStatus {

	private String name;

	private ContainerState state;

	private ContainerState lastState;

	private boolean ready;

	private int restartCount;

	private String image;

	private String imageID;

	private String containerID;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContainerState getState() {
		return state;
	}

	public void setState(ContainerState state) {
		this.state = state;
	}

	public ContainerState getLastState() {
		return lastState;
	}

	public void setLastState(ContainerState lastState) {
		this.lastState = lastState;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getRestartCount() {
		return restartCount;
	}

	public void setRestartCount(int restartCount) {
		this.restartCount = restartCount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	public String getContainerID() {
		return containerID;
	}

	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}

	@Override
	public String toString() {
		return "ContainerStatus "
				+ "[name=" + name 
				+ ", state=" + state
				+ ", lastState=" + lastState 
				+ ", ready=" + ready
				+ ", restartCount=" + restartCount 
				+ ", image=" + image
				+ ", imageID=" + imageID 
				+ ", containerID=" + containerID + "]";
	}
}
