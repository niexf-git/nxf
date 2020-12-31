package com.cmsz.paas.common.model.k8s.v1;

public class Handler {

	private ExecAction exec;

	private HTTPGetAction httpGet;

	private TCPSocketAction tcpSocket;

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

	@Override
	public String toString() {
		return "Handler "
				+ "[exec=" + exec 
				+ ", httpGet=" + httpGet
				+ ", tcpSocket=" + tcpSocket + "]";
	}
}
