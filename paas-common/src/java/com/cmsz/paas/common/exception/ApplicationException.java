package com.cmsz.paas.common.exception;

import java.util.Arrays;

/**
 * ApplicationException is the super class of all the exceptions in the
 * framework.
 * 
 * @author YangYong.
 * 
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -2394147719359810950L;

	/**
	 * 错误消息代码
	 */
	private String errorCode;

	/**
	 * 错误描述信息
	 */
	private String errorMsg;

	/**
	 * 传给错误消息的参数
	 */
	private String[] errorMsgParam;

	public ApplicationException() {

	}

	/**
	 * Simple wrapper of Throwable Object.
	 * 
	 * @param e
	 */
	public ApplicationException(Throwable e) {

		super(e);
	}

	/**
	 * Constructor, exception contents the user-defined error information.
	 * 
	 * @param msg
	 * @param e
	 */
	public ApplicationException(String msg, Throwable e) {

		super(msg, e);
	}

	public ApplicationException(String msg) {

		super(msg);
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {

		return errorCode;
	}

	public void setErrorCode(String errorCode) {

		this.errorCode = errorCode;
	}

	public void setErrorMsgParam(String[] errorMsgParam) {

		this.errorMsgParam = errorMsgParam;
	}

	public String[] getErrorMsgParam() {

		return errorMsgParam;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationException [errorCode=");
		builder.append(errorCode);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append(", errorMsgParam=");
		builder.append(Arrays.toString(errorMsgParam));
		builder.append("]");
		return builder.toString();
	}

}
