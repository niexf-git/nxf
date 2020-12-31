package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;
import java.util.Map;

public class PodSpec {
	private Volume[] volumes;

	private Container[] containers;

	private String restartPolicy;

	private Long terminationGracePeriodSeconds;

	private Long activeDeadlineSeconds;

	private String dnsPolicy;

	private Map<String,String> nodeSelector;

	private String serviceAccountName;
	
	private String serviceAccount;

	private String nodeName;

	private boolean hostNetwork;
	
	private boolean hostPID;
	
	private boolean hostIPC;
	
	private PodSecurityContext securityContext;
	
	private LocalObjectReference[] imagePullSecrets;
	
	private String hostname;
	
	private String subdomain;

	public Volume[] getVolumes() {
		return volumes;
	}

	public void setVolumes(Volume[] volumes) {
		this.volumes = volumes;
	}

	public Container[] getContainers() {
		return containers;
	}

	public void setContainers(Container[] containers) {
		this.containers = containers;
	}

	public String getRestartPolicy() {
		return restartPolicy;
	}

	public void setRestartPolicy(String restartPolicy) {
		this.restartPolicy = restartPolicy;
	}

	public Long getTerminationGracePeriodSeconds() {
		return terminationGracePeriodSeconds;
	}

	public void setTerminationGracePeriodSeconds(Long terminationGracePeriodSeconds) {
		this.terminationGracePeriodSeconds = terminationGracePeriodSeconds;
	}

	public Long getActiveDeadlineSeconds() {
		return activeDeadlineSeconds;
	}

	public void setActiveDeadlineSeconds(Long activeDeadlineSeconds) {
		this.activeDeadlineSeconds = activeDeadlineSeconds;
	}

	public String getDnsPolicy() {
		return dnsPolicy;
	}

	public void setDnsPolicy(String dnsPolicy) {
		this.dnsPolicy = dnsPolicy;
	}

	public Map<String, String> getNodeSelector() {
		return nodeSelector;
	}

	public void setNodeSelector(Map<String, String> nodeSelector) {
		this.nodeSelector = nodeSelector;
	}

	public String getServiceAccountName() {
		return serviceAccountName;
	}

	public void setServiceAccountName(String serviceAccountName) {
		this.serviceAccountName = serviceAccountName;
	}

	public String getServiceAccount() {
		return serviceAccount;
	}

	public void setServiceAccount(String serviceAccount) {
		this.serviceAccount = serviceAccount;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public boolean isHostNetwork() {
		return hostNetwork;
	}

	public void setHostNetwork(boolean hostNetwork) {
		this.hostNetwork = hostNetwork;
	}

	public boolean isHostPID() {
		return hostPID;
	}

	public void setHostPID(boolean hostPID) {
		this.hostPID = hostPID;
	}

	public boolean isHostIPC() {
		return hostIPC;
	}

	public void setHostIPC(boolean hostIPC) {
		this.hostIPC = hostIPC;
	}

	public PodSecurityContext getSecurityContext() {
		return securityContext;
	}

	public void setSecurityContext(PodSecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public LocalObjectReference[] getImagePullSecrets() {
		return imagePullSecrets;
	}

	public void setImagePullSecrets(LocalObjectReference[] imagePullSecrets) {
		this.imagePullSecrets = imagePullSecrets;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	@Override
	public String toString() {
		return "PodSpec "
				+ "[volumes=" + Arrays.toString(volumes)
				+ ", containers=" + Arrays.toString(containers) 
				+ ", restartPolicy=" + restartPolicy 
				+ ", terminationGracePeriodSeconds=" + terminationGracePeriodSeconds 
				+ ", activeDeadlineSeconds=" + activeDeadlineSeconds 
				+ ", dnsPolicy=" + dnsPolicy
				+ ", nodeSelector=" + nodeSelector 
				+ ", serviceAccountName=" + serviceAccountName 
				+ ", serviceAccount=" + serviceAccount
				+ ", nodeName=" + nodeName
				+ ", hostNetwork=" + hostNetwork 
				+ ", hostPID=" + hostPID
				+ ", hostIPC=" + hostIPC
				+ ", securityContext=" + securityContext
				+ ", imagePullSecrets=" + Arrays.toString(imagePullSecrets)
				+ ", hostname=" + hostname 
				+ ", subdomain=" + subdomain + "]";
	}

}
