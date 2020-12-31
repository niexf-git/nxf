package com.cmsz.paas.common.model.k8s.v1;

public class NodeSystemInfo {

	private String machineID;

	private String systemUUID;

	private String bootID;

	private String kernelVersion;

	private String osImage;

	private String containerRuntimeVersion;

	private String kubeletVersion;

	private String kubeProxyVersion;
	
	private String operatingSystem;
	
	private String architecture;

	public String getMachineID() {
		return machineID;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	public String getSystemUUID() {
		return systemUUID;
	}

	public void setSystemUUID(String systemUUID) {
		this.systemUUID = systemUUID;
	}

	public String getBootID() {
		return bootID;
	}

	public void setBootID(String bootID) {
		this.bootID = bootID;
	}

	public String getKernelVersion() {
		return kernelVersion;
	}

	public void setKernelVersion(String kernelVersion) {
		this.kernelVersion = kernelVersion;
	}

	public String getOsImage() {
		return osImage;
	}

	public void setOsImage(String osImage) {
		this.osImage = osImage;
	}

	public String getContainerRuntimeVersion() {
		return containerRuntimeVersion;
	}

	public void setContainerRuntimeVersion(String containerRuntimeVersion) {
		this.containerRuntimeVersion = containerRuntimeVersion;
	}

	public String getKubeletVersion() {
		return kubeletVersion;
	}

	public void setKubeletVersion(String kubeletVersion) {
		this.kubeletVersion = kubeletVersion;
	}

	public String getKubeProxyVersion() {
		return kubeProxyVersion;
	}

	public void setKubeProxyVersion(String kubeProxyVersion) {
		this.kubeProxyVersion = kubeProxyVersion;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getArchitecture() {
		return architecture;
	}

	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}

	@Override
	public String toString() {
		return "NodeSystemInfo "
				+ "[machineID=" + machineID 
				+ ", systemUUID=" + systemUUID 
				+ ", bootID=" + bootID 
				+ ", kernelVersion=" + kernelVersion 
				+ ", osImage=" + osImage
				+ ", containerRuntimeVersion=" + containerRuntimeVersion
				+ ", kubeletVersion=" + kubeletVersion
				+ ", kubeProxyVersion=" + kubeProxyVersion
				+ ", operatingSystem=" + operatingSystem
				+ ", architecture=" + architecture+ "]";
	}
}
