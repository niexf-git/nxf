package com.cmsz.paas.common.model.k8s.v1;

public class SELinuxOptions {

	private String user;

	private String role;

	private String type;

	private String level;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "SELinuxOptions "
				+ "[user=" + user 
				+ ", role=" + role 
				+ ", type=" + type 
				+ ", level=" + level + "]";
	}
}
