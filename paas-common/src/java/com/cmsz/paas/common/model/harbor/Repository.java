package com.cmsz.paas.common.model.harbor;

public class Repository {

	private String project_id;
	
	private String project_name;
	
	private String project_public;
	
	private String repository_name;

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getProject_public() {
		return project_public;
	}

	public void setProject_public(String project_public) {
		this.project_public = project_public;
	}

	public String getRepository_name() {
		return repository_name;
	}

	public void setRepository_name(String repository_name) {
		this.repository_name = repository_name;
	}

	@Override
	public String toString() {
		return "Repository [project_id=" + project_id + ", project_name="
				+ project_name + ", project_public=" + project_public
				+ ", repository_name=" + repository_name + "]";
	}

}
