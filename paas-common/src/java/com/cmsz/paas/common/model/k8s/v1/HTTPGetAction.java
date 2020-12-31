package com.cmsz.paas.common.model.k8s.v1;

public class HTTPGetAction {

	private String path;

	private String port;

	private String host;

	private String scheme;

	private HTTPHeader[] httpHeaders;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public HTTPHeader[] getHttpHeaders() {
		return httpHeaders;
	}

	public void setHttpHeaders(HTTPHeader[] httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	@Override
	public String toString() {
		return "HTTPGetAction "
				+ "[path=" + path 
				+ ", port=" + port
				+ ", host=" + host 
				+ ", scheme=" + scheme 
				+ ", httpHeaders=" + httpHeaders + "]";
	}
}
