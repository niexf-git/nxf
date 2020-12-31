package com.cmsz.paas.web.base.util;

import java.util.List;
/**
 * 
 * Easy ui 树
 * @author yzw
 *
 */
public class EasyUiTree {
	
	private String id;
	private String text;
	private List<EasyUiTree> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<EasyUiTree> getChildren() {
		return children;
	}
	public void setChildren(List<EasyUiTree> children) {
		this.children = children;
	}
	

}
