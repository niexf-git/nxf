package com.cmsz.paas.common.model.k8s.v1;

public class Probe {

	private ExecAction exec;

	private HTTPGetAction httpGet;

	private TCPSocketAction tcpSocket;

	private Long initialDelaySeconds;

	private Long timeoutSeconds;
	
	private Long periodSeconds;
	
	private Long successThreshold;
	
	private Long failureThreshold;

	public ExecAction getExec() {
		return exec;
	}

	public void setExec(ExecAction exec) {
		this.exec = exec;
	}

	public HTTPGetAction getHttpGet() {
		return httpGet;
	}

	public void setHttpGet(HTTPGetAction httpGet) {
		this.httpGet = httpGet;
	}

	public TCPSocketAction getTcpSocket() {
		return tcpSocket;
	}

	public void setTcpSocket(TCPSocketAction tcpSocket) {
		this.tcpSocket = tcpSocket;
	}

	public Long getInitialDelaySeconds() {
		return initialDelaySeconds;
	}

	public void setInitialDelaySeconds(Long initialDelaySeconds) {
		this.initialDelaySeconds = initialDelaySeconds;
	}

	public Long getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public void setTimeoutSeconds(Long timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}

	public Long getPeriodSeconds() {
		return periodSeconds;
	}

	public void setPeriodSeconds(Long periodSeconds) {
		this.periodSeconds = periodSeconds;
	}

	public Long getSuccessThreshold() {
		return successThreshold;
	}

	public void setSuccessThreshold(Long successThreshold) {
		this.successThreshold = successThreshold;
	}

	public Long getFailureThreshold() {
		return failureThreshold;
	}

	public void setFailureThreshold(Long failureThreshold) {
		this.failureThreshold = failureThreshold;
	}

	@Override
	public String toString() {
		return "Probe "
				+ "[exec=" + exec 
				+ ", httpGet=" + httpGet 
				+ ", tcpSocket=" + tcpSocket 
				+ ", initialDelaySeconds=" + initialDelaySeconds
				+ ", timeoutSeconds=" + timeoutSeconds 
				+ ", periodSeconds=" + periodSeconds 
				+ ", successThreshold=" + successThreshold 
				+ ", failureThreshold=" + failureThreshold + "]";
	}
}
