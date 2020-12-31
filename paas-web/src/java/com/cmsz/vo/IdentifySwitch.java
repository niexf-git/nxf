package com.cmsz.vo;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 应用系统认证切换开关
 * 
 * @author 林绵炎
 * @version 创建时间：2016年11月23日 下午5:30:23
 */
public class IdentifySwitch {

	@ID
	private Long id;
	private int switchValue; // 开关值（0表示从4A平台登录、1表示从PAAS登录）
	private int state; // 状态（0表示有效、1表示无效）
	private String createDate; // 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSwitchValue() {
		return switchValue;
	}

	public void setSwitchValue(int switchValue) {
		this.switchValue = switchValue;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
