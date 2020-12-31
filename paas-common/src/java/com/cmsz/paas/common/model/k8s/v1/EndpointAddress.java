package com.cmsz.paas.common.model.k8s.v1;

public class EndpointAddress {

	private String ip;
	
	private String hostname;
	
	private String nodeName;

	private ObjectReference targetRef;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ObjectReference getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(ObjectReference targetRef) {
		this.targetRef = targetRef;
	}

	@Override
	public String toString() {
		return "EndpointAddress "
				+ "[ip=" + ip 
				+ ", hostname=" + hostname 
				+ ", nodeName=" + nodeName 
				+ ", targetRef=" + targetRef + "]";
	}
}
