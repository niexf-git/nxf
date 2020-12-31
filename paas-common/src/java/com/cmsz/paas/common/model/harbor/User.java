package com.cmsz.paas.common.model.harbor;

public class User {

	private int user_id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String realname;
	
	private String comment;
	
	private int deleted;
	
	private String role_name;
	
	private int role_id;
	
	private int has_admin_role;
	
	private String reset_uuid;
	
	//private String Salt;
	
	private String creation_time;
	
	private String update_time;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getHas_admin_role() {
		return has_admin_role;
	}

	public void setHas_admin_role(int has_admin_role) {
		this.has_admin_role = has_admin_role;
	}

	public String getReset_uuid() {
		return reset_uuid;
	}

	public void setReset_uuid(String reset_uuid) {
		this.reset_uuid = reset_uuid;
	}

//	public String getSalt() {
//		return Salt;
//	}
//
//	public void setSalt(String salt) {
//		Salt = salt;
//	}

	public String getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(String creation_time) {
		this.creation_time = creation_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	@Override
	public String toString(){
		return "User [user_id=" + user_id + ", username=" + username + ", email="
				+ email + ", username=" + username
				+ ", password=" + password+", comment="+comment 
				+ ", deleted=" + deleted+", role_name="+role_name
				+ ", role_id=" + role_id+", has_admin_role="+has_admin_role
				+ ", reset_uuid=" + reset_uuid
				+ ", creation_time="+ creation_time + ", update_time=" 
				+ update_time + "]";
	}
}
