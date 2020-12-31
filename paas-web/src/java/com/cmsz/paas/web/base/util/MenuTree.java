package com.cmsz.paas.web.base.util;

import java.util.List;

/**
 * 数据结构:菜单树
 * 
 * @author pengfan
 * 
 */
public class MenuTree {

	private String id;// 节点标识
	private String name;// 节点名称
	private String icon;// 图标
	private boolean selected;// 是否选中
	private List<MenuTree> children;// 子节点
	private String parentId;
	private String action;
	private Integer sort;
	private String menuInfo;
	private Integer acitonType;

	public MenuTree() {

		super();
	}

	public MenuTree(String id, String name, boolean selected) {

		this.id = id;
		this.name = name;
		this.selected = selected;
	}
	
	public MenuTree(String id, String name, boolean selected,String parentId) {

		this.id = id;
		this.name = name;
		this.selected = selected;
		this.parentId = parentId;
	}

	public Integer getAcitonType() {

		return acitonType;
	}

	public String getAction() {

		return action;
	}

	public List<MenuTree> getChildren() {

		return children;
	}

	public String getIcon() {

		return icon;
	}

	public String getId() {

		return id;
	}

	public String getMenuInfo() {

		return menuInfo;
	}

	public String getName() {

		return name;
	}

	public String getParentId() {

		return parentId;
	}

	public boolean getSelected() {

		return selected;
	}

	public Integer getSort() {

		return sort;
	}

	public void setAcitonType(Integer acitonType) {

		this.acitonType = acitonType;
	}

	public void setAction(String action) {

		this.action = action;
	}

	public void setChildren(List<MenuTree> children) {

		this.children = children;
	}

	public void setIcon(String icon) {

		this.icon = icon;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setMenuInfo(String menuInfo) {

		this.menuInfo = menuInfo;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setParentId(String parentId) {

		this.parentId = parentId;
	}

	public void setSelected(boolean selected) {

		this.selected = selected;
	}

	public void setSort(Integer sort) {

		this.sort = sort;
	}
}
