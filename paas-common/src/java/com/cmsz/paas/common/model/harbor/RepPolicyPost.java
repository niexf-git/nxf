package com.cmsz.paas.common.model.harbor;

public class RepPolicyPost {

	private int project_id;
	
	private int target_id;
	
	private int enabled;
	
	private String name;
	
	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RepPolicyPost [project_id=" + project_id + ", target_id="
				+ target_id + ", enabled=" + enabled + ", name=" + name + "]";
	}

	
}
