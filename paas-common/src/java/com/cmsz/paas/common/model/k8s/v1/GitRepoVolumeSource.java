package com.cmsz.paas.common.model.k8s.v1;

public class GitRepoVolumeSource {

	private String repository;

	private String revision;

	private String directory;
	
	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	@Override
	public String toString() {
		return "GitRepoVolumeSource "
				+ "[repository=" + repository
				+ ", revision=" + revision 
				+ ", directory="+ directory + "]";
	}
}
