package com.cmsz.paas.common.model.k8s.v1;

public class PodSecurityContext {
	
	private SELinuxOptions seLinuxOptions;
	
	private int runAsUser;
	
	private boolean runAsNonRoot;
	
	private int[] supplementalGroups;
	
	private int fsGroup;
	
	public SELinuxOptions getSeLinuxOptions() {
		return seLinuxOptions;
	}

	public void setSeLinuxOptions(SELinuxOptions seLinuxOptions) {
		this.seLinuxOptions = seLinuxOptions;
	}

	public int getRunAsUser() {
		return runAsUser;
	}

	public void setRunAsUser(int runAsUser) {
		this.runAsUser = runAsUser;
	}

	public boolean isRunAsNonRoot() {
		return runAsNonRoot;
	}

	public void setRunAsNonRoot(boolean runAsNonRoot) {
		this.runAsNonRoot = runAsNonRoot;
	}

	public int[] getSupplementalGroups() {
		return supplementalGroups;
	}

	public void setSupplementalGroups(int[] supplementalGroups) {
		this.supplementalGroups = supplementalGroups;
	}

	public int getFsGroup() {
		return fsGroup;
	}

	public void setFsGroup(int fsGroup) {
		this.fsGroup = fsGroup;
	}

	@Override
	public String toString() {
		return "PodSecurityContext"
				+ " [seLinuxOptions=" + seLinuxOptions
				+ ", runAsUser=" + runAsUser
				+ ", runAsNonRoot=" + runAsNonRoot
				+ ", supplementalGroups=" + runAsNonRoot
				+ ", supplementalGroups=" + supplementalGroups
				+ ", fsGroup=" + fsGroup + "]";
	}

}
