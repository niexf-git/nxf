package com.cmsz.paas.web.group4a.entity;

import java.util.Date;

import com.cmsz.paas.common.orm.id.ID;


/**
 * @author CCL
 * @date 
 * 4A帐号表
 */
public class User4AEntity{

	/**
	 * 
	 */
	
	@ID
	private Integer id;
	private String userName;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
