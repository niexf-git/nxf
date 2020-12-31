package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class TestEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String id;//和代码下载 账户验证的接口返回的id一样
	private String repoType;//svn/git
	private String url;//svn地址
	private String userName;//用户名
	private String password;//密码
	private String testCommand;//测试命令
	private String relationName;//引用集成名称

	
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRepoType() {
		return repoType;
	}
	public void setRepoType(String repoType) {
		this.repoType = repoType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTestCommand() {
		return testCommand;
	}
	public void setTestCommand(String testCommand) {
		this.testCommand = testCommand;
	}
	@Override
	public String toString() {
		return "TestEntity [id=" + id + ", repoType=" + repoType + ", url="
				+ url + ", userName=" + userName + ", password=" + password
				+ ", testCommand=" + testCommand + ", relationName="
				+ relationName + "]";
	}
}
