package com.cmsz.paas.web.base.exception;

import java.util.ArrayList;
import java.util.List;

import com.cmsz.paas.common.exception.ApplicationException;
import com.cmsz.paas.web.base.util.PropertiesUtil;

@SuppressWarnings("unchecked")
public class PaasWebException extends ApplicationException {
	/**
     *
     */
	private static final long serialVersionUID = 1L;
	/**
	 * The message key for this message.
	 */
	private String key = null;

	/**
	 * parameters
	 */
	private final List parameters;

	public PaasWebException(String key) {
		super(key);
		setErrorCode(key);
		this.key = key;
		parameters = new ArrayList();
	}

	public PaasWebException(String key, String msg) {
		super(msg);
		setErrorCode(key);
		this.key = key;		
		parameters = new ArrayList();
	}

	public PaasWebException(String key, Throwable t) {
		super(t);
		setErrorCode(key);
		this.key = key;
		parameters = new ArrayList();
	}

	/**
	 * add a parameter
	 * 
	 * @param parameter
	 *            parameter
	 */
	public void addParameter(String parameter) {
		this.parameters.add(parameter);
	}

	/**
	 * get first parameter
	 * 
	 * @return the firstParameter
	 */
	public String getFirstParameter() {
		if (parameters == null || parameters.isEmpty()) {
			return null;
		}
		return (String) parameters.get(0);
	}

	/**
	 * Get the message key for this message.
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * get all parameters
	 * 
	 * @return all parameters
	 */
	public String[] getParameters() {
		String[] parameterArray = null;
		if (!parameters.isEmpty()) {
			parameterArray = new String[parameters.size()];
			for (int i = 0; i < parameterArray.length; i++) {
				parameterArray[i] = (String) parameters.get(i);
			}
		}
		return parameterArray;
	}

	/**
	 * set firstParameter
	 * 
	 * @param firstParameter
	 *            the firstParameter to set
	 */
	public void setFirstParameter(String firstParameter) {
		if (parameters == null) {
			return;
		} else if (parameters.isEmpty()) {
			parameters.add(firstParameter);
		} else {
			parameters.set(0, firstParameter);
		}
	}


	 @Override
	public String getMessage(){
	 return super.getMessage();
	 }
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();		
//		builder.append("[");
//		builder.append(key);
//		builder.append("]:");
		builder.append(PropertiesUtil.getValue(key));
//		builder.append(", sysErrorMsg=");
		builder.append("<br/>");
		builder.append(super.getMessage());			

		return builder.toString();
	}
}
