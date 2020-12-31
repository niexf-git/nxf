package com.cmsz.paas.web.user.entity;

import java.util.List;

public class AdminAppInfo {

	private boolean isAdmin = false;
	
	
	private List<AppInfo> appInfo;


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public List<AppInfo> getAppInfo() {
		return appInfo;
	}


	public void setAppInfo(List<AppInfo> appInfo) {
		this.appInfo = appInfo;
	}
	
	
}
