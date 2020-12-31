package com.cmsz.paas.web.application.model;

public class ClusterInfo {
	private long id;
	private String name;
	private int type;
	private boolean checked;
	private boolean checkeds;


	public boolean isCheckeds() {
		return checkeds;
	}
	public void setCheckeds(boolean checkeds) {
		this.checkeds = checkeds;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
