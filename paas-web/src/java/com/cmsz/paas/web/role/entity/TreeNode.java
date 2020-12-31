package com.cmsz.paas.web.role.entity;

public class TreeNode {

	/** id */
	public String id;

	/** 父类ID */
	public String pId;

	/** 名称 */
	public String name;

	/** 标题 */
	public String title;

	/** 是否打开 */
	public boolean open;

	/** 是否选中 */
	public boolean checked;

	/** 是否禁用 */
	public boolean chkDisabled;

//	public String iconOpen;
//
//	public String iconClose;
//
//	public String icon;

//	public String getIconOpen() {
//		return iconOpen;
//	}
//
//	public void setIconOpen(String iconOpen) {
//		this.iconOpen = iconOpen;
//	}
//
//	public String getIconClose() {
//		return iconClose;
//	}
//
//	public void setIconClose(String iconClose) {
//		this.iconClose = iconClose;
//	}
//
//	public String getIcon() {
//		return icon;
//	}
//
//	public void setIcon(String icon) {
//		this.icon = icon;
//	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

//	@Override
//	public String toString() {
//		return "TreeNode [id=" + id + ", pId=" + pId + ", name=" + name
//				+ ", title=" + title + ", open=" + open + ", checked="
//				+ checked + ", chkDisabled=" + chkDisabled + ", iconOpen="
//				+ iconOpen + ", iconClose=" + iconClose + ", icon=" + icon
//				+ "]";
//	}

}
