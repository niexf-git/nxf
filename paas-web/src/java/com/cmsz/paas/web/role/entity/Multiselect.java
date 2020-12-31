package com.cmsz.paas.web.role.entity;

/**
 * 左右文本选择对象
 * 
 * @author zhouyunxia
 * 
 */
public class Multiselect {
	/** id */
	private String id;
	
	/** 名称 */
	private String name;
	
	/** 是否选择 */
	private boolean select;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	@Override
	public String toString() {
		return "Multiselect [id=" + id + ", name=" + name + ", select="
				+ select + "]";
	}
}
