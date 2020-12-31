package com.cmsz.paas.common.model.harbor;

import java.util.List;

public class PublicRepository {

	private List<Repository> repository;
	

	public List<Repository> getRepository() {
		return repository;
	}

	public void setRepository(List<Repository> repository) {
		this.repository = repository;
	}

	@Override
	public String toString() {
		return "PublicRepository [repository=" + repository + "]";
	}


	
	
}
