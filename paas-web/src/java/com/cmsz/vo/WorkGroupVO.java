package com.cmsz.vo;

import java.io.Serializable;
/**
 * 工作组定义（相当于角色）
 * 
 * @author 林绵炎 
 * @version 创建时间：2016年11月18日 下午5:25:55
 */
public class WorkGroupVO implements Serializable {

	private static final long serialVersionUID = 5948064172035811543L;

	private String groupId; // 工作组标识

	private String name; // 名称

	private String groupType; // 类型

	private String validBegin; // 有效起始时间

	private String validEnd; // 有效终止时间

	private String note; // 备注

	private String safeMode; // （权限属性)

	public String getSafeMode() {
		return safeMode;
	}

	public void setSafeMode(String safeMode) {
		this.safeMode = safeMode;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getValidBegin() {
		return validBegin;
	}

	public void setValidBegin(String validBegin) {
		this.validBegin = validBegin;
	}

	public String getValidEnd() {
		return validEnd;
	}

	public void setValidEnd(String validEnd) {
		this.validEnd = validEnd;
	}

}
