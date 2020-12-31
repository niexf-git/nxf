package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;
import java.util.Map;

public class ServiceSpec {

	private ServicePort[] ports;

	private Map<String,String> selector;

	private String clusterIP;

	private String type;
	
	private String[] externalIPs;

	private String[] deprecatedPublicIPs;

	private String sessionAffinity;
	
	private String loadBalancerIP;
	
	private String[] loadBalancerSourceRanges;
	
	private String externalName;

	public ServicePort[] getPorts() {
		return ports;
	}

	public void setPorts(ServicePort[] ports) {
		this.ports = ports;
	}

	public Map<String, String> getSelector() {
		return selector;
	}

	public void setSelector(Map<String, String> selector) {
		this.selector = selector;
	}

	public String getClusterIP() {
		return clusterIP;
	}

	public void setClusterIP(String clusterIP) {
		this.clusterIP = clusterIP;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getExternalIPs() {
		return externalIPs;
	}

	public void setExternalIPs(String[] externalIPs) {
		this.externalIPs = externalIPs;
	}

	public String[] getDeprecatedPublicIPs() {
		return deprecatedPublicIPs;
	}

	public void setDeprecatedPublicIPs(String[] deprecatedPublicIPs) {
		this.deprecatedPublicIPs = deprecatedPublicIPs;
	}

	public String getSessionAffinity() {
		return sessionAffinity;
	}

	public void setSessionAffinity(String sessionAffinity) {
		this.sessionAffinity = sessionAffinity;
	}

	public String getLoadBalancerIP() {
		return loadBalancerIP;
	}

	public void setLoadBalancerIP(String loadBalancerIP) {
		this.loadBalancerIP = loadBalancerIP;
	}

	public String[] getLoadBalancerSourceRanges() {
		return loadBalancerSourceRanges;
	}

	public void setLoadBalancerSourceRanges(String[] loadBalancerSourceRanges) {
		this.loadBalancerSourceRanges = loadBalancerSourceRanges;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	@Override
	public String toString() {
		return "ServiceSpec "
				+ "[ports=" + Arrays.toString(ports) 
				+ ", selector=" + selector 
				+ ", clusterIP=" + clusterIP 
				+ ", type=" + type
				+ ", externalIPs=" + externalIPs
				+ ", deprecatedPublicIPs=" + Arrays.toString(deprecatedPublicIPs) 
				+ ", sessionAffinity=" + sessionAffinity
				+ ", loadBalancerIP=" + loadBalancerIP
				+ ", loadBalancerSourceRanges=" + Arrays.toString(loadBalancerSourceRanges)
				+ ", externalName=" + externalName + "]";
	}

}
