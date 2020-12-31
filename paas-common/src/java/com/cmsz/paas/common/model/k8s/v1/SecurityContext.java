package com.cmsz.paas.common.model.k8s.v1;

public class SecurityContext {

	private Capabilities capabilities;

	private boolean privileged;

	private SELinuxOptions seLinuxOptions;

	private Long runAsUser;

	private boolean runAsNonRoot;
	
	private boolean readOnlyRootFilesystem;

	public Capabilities getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Capabilities capabilities) {
		this.capabilities = capabilities;
	}

	public boolean isPrivileged() {
		return privileged;
	}

	public void setPrivileged(boolean privileged) {
		this.privileged = privileged;
	}

	public SELinuxOptions getSeLinuxOptions() {
		return seLinuxOptions;
	}

	public void setSeLinuxOptions(SELinuxOptions seLinuxOptions) {
		this.seLinuxOptions = seLinuxOptions;
	}

	public Long getRunAsUser() {
		return runAsUser;
	}

	public void setRunAsUser(Long runAsUser) {
		this.runAsUser = runAsUser;
	}

	public boolean isRunAsNonRoot() {
		return runAsNonRoot;
	}

	public void setRunAsNonRoot(boolean runAsNonRoot) {
		this.runAsNonRoot = runAsNonRoot;
	}

	public boolean isReadOnlyRootFilesystem() {
		return readOnlyRootFilesystem;
	}

	public void setReadOnlyRootFilesystem(boolean readOnlyRootFilesystem) {
		this.readOnlyRootFilesystem = readOnlyRootFilesystem;
	}

	@Override
	public String toString() {
		return "SecurityContext "
				+ "[capabilities=" + capabilities
				+ ", privileged=" + privileged 
				+ ", seLinuxOptions=" + seLinuxOptions
				+ ", runAsUser=" + runAsUser 
				+ ", runAsNonRoot=" + runAsNonRoot
				+ ", readOnlyRootFilesystem=" + readOnlyRootFilesystem + "]";
	}
}
