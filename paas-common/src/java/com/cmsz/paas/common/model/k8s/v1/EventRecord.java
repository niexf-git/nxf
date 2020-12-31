package com.cmsz.paas.common.model.k8s.v1;

public class EventRecord {

	private String type;

	private Object object;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "Event "
				+ "[type=" + type 
				+ ", object=" + object + "]";
	}
}
