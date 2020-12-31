package com.cmsz.paas.common.model.harbor;

public class RepPolicy {

	private int id;
	
	private int project_id;
	
	private String project_name;
	
	private int target_id;
	
	private String name;
	
	private int enabled;
	
	private String description;
	
    private String cron_str;
	
	private String start_time;
	
	private String creation_time;
	
	private String update_time;
	
	private int error_job_count;
	
	private int deleted;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
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

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCron_str() {
		return cron_str;
	}

	public void setCron_str(String cron_str) {
		this.cron_str = cron_str;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
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

	public int getError_job_count() {
		return error_job_count;
	}

	public void setError_job_count(int error_job_count) {
		this.error_job_count = error_job_count;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public String toString(){
	   
		return "RepPolicy [id=" + id + ", project_id=" + project_id + ", project_name="
					+ project_name + ", target_id=" + target_id
					+ ", name=" + name+", enabled="+enabled 
					+", description="+description+", cron_str="+cron_str
					+", start_time="+start_time+", creation_time="
					+ creation_time + ", update_time="+ update_time 
					+", error_job_count="+error_job_count+", deleted="+deleted+"]";
		}
}
