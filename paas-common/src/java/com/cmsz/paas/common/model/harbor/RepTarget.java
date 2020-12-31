package com.cmsz.paas.common.model.harbor;

public class RepTarget {

	private int id;
	
	private String endpoint;
	
	private String name;
	
	private String username;
	
	private String password;
	
	private int type;
	
	private String creation_time;
	
	private String update_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

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
		return "RepTarget [id=" + id + ", endpoint=" + endpoint + ", name="
				+ name + ", username=" + username
				+ ", password=" + password
				+",type="+type + ", creation_time="
				+ creation_time + ", update_time=" + update_time + "]";
	}
	
	}
