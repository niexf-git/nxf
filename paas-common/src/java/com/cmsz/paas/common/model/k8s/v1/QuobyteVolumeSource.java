package com.cmsz.paas.common.model.k8s.v1;

public class QuobyteVolumeSource {
      
	private String registry;
	
	private String volume;
	
	private boolean readOnly;
	
	private String user;
	
	private String group;
	
	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	@Override
	public String toString() {
		return "QuobyteVolumeSource "
				+ "[registry=" + registry 
				+ ", volume=" + volume 
				+", readOnly="+ readOnly 
				+", user="+user
				+ ", group="+group +"]";
	}

}
