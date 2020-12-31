package com.cmsz.paas.common.model.k8s.v1;

public class PersistentVolumeClaimSpec {

	private String[] accessModes;
	
	private LabelSelector selector;
	
	private ResourceRequirements resources;

	private String volumeName;

	public String[] getAccessModes() {
		return accessModes;
	}

	public void setAccessModes(String[] accessModes) {
		this.accessModes = accessModes;
	}

	public LabelSelector getSelector() {
		return selector;
	}

	public void setSelector(LabelSelector selector) {
		this.selector = selector;
	}

	public ResourceRequirements getResources() {
		return resources;
	}

	public void setResources(ResourceRequirements resources) {
		this.resources = resources;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	@Override
	public String toString() {
		return "PersistentVolumeClaimSpec [accessModes=" + accessModes + ", selector="
				+ selector +", resources="+resources+", volumeName="+volumeName+ "]";
	}
}
