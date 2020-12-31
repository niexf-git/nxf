package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.harbor.JobStatus;

public class JobList {

	private List<JobStatus> jobList;

	public List<JobStatus> getJobList() {
		return jobList;
	}

	public void setJobList(List<JobStatus> jobList) {
		this.jobList = jobList;
	}
}
