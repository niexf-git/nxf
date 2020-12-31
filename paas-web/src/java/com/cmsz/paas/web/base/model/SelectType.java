package com.cmsz.paas.web.base.model;

/**
 * 下拉框数据
 * 
 * @author liaohw
 */
public class SelectType {

	/** 页面下拉框中的value */
	private String value;

	/** 页面下拉框中的text */
	private String text;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
