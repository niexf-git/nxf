/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File ResponseInfo.java
 */
package com.cmsz.paas.common.model.monitor.response;

/**
 * REST返回信息
 * 
 * @author hehm
 *
 */
public class ResponseInfo {

	/**
	 * 返回码
	 */
	private String retCode;

	/**
	 * 返回描述信息
	 */
	private String msg;

	/**
	 * 返回数据
	 */
	private Object data;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseInfo [retCode=" + retCode + ", msg=" + msg + ", data="
				+ data + "]";
	}
}
