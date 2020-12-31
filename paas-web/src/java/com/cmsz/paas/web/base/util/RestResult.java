package com.cmsz.paas.web.base.util;

import java.io.Serializable;

/**
 * rest接口返回消息基类
 * 
 * @author fubl
 * 
 */
public class RestResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -775540268295206392L;
	public static final String SUCCESS_CODE = "0";
	public static final String COMPLETE_CODE = "1";



	/** 返回码 */
	private String retCode;

	/** 返回错误信息 */
	private String msg;

	/** 返回数据 */
	private String data;

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}

	public String getRetCode() {
		return retCode;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	@Override
	public String toString() {
		return "RestResult [retCode=" + retCode + ", msg=" + msg + "]";
	}
} 
