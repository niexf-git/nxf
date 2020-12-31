package com.cmsz.paas.common.model.harbor;

public class Project {

	private int project_id;
	
	private int owner_id;
	
	private String name;
	
	private String project_name;
	
	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	private String creation_time;
	
	private String update_time;
	
	private int deleted;
	
	private String owner_name;
	
	private int publics;
	
	private boolean Togglable;
	
	private int current_user_role_id;
	
	private int repo_count;

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public int getPublics() {
		return publics;
	}

	public void setPublics(int publics) {
		this.publics = publics;
	}

	public boolean isTogglable() {
		return Togglable;
	}

	public void setTogglable(boolean togglable) {
		Togglable = togglable;
	}

	public int getCurrent_user_role_id() {
		return current_user_role_id;
	}

	public void setCurrent_user_role_id(int current_user_role_id) {
		this.current_user_role_id = current_user_role_id;
	}

	public int getRepo_count() {
		return repo_count;
	}

	public void setRepo_count(int repo_count) {
		this.repo_count = repo_count;
	}
	
	public String toString(){
		return "Project [project_id=" + project_id + ", owner_id=" + owner_id
				+ ", name="+ name +", creation_time="+creation_time 
				+ ", update_time=" + update_time+ ", deleted=" + deleted
				+ ", owner_name=" + owner_name
				+ ",publics="+publics + ", Togglable="+ Togglable 
				+ ", current_user_role_id=" + current_user_role_id
				+ ", repo_count="+repo_count+"]";
	}
	
}
