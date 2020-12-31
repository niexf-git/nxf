package com.cmsz.paas.common.model.harbor;

import java.util.Arrays;

public class JobStatus {
   
	private int id;
	
	private String status;
	
	private String repository;
	
	private int policy_id;
	
	private String operation;
	
	private String [] tags;
	
	private String creation_time;
	
	private String update_time;
	
	private String dataCenrer_name;

	public String getDataCenrer_name() {
		return dataCenrer_name;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public int getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(int policy_id) {
		this.policy_id = policy_id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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
	
	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public void setDataCenrer_name(String dataCenrer_name) {
		this.dataCenrer_name = dataCenrer_name;
	}

	@Override
	public String toString() {
		return "JobStatus [id=" + id + ", status=" + status + ","
				+ " repository=" + repository + ", policy_id=" + policy_id
				+ ", operation=" + operation + ", tags=" + tags + ", "
				+ "creation_time=" + creation_time
				+ ", update_time=" + update_time + ", dataCenrer_name=" 
				+ dataCenrer_name + "]";
	}

	
}
