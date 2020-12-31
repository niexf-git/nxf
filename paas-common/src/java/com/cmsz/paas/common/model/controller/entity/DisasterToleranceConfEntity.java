package com.cmsz.paas.common.model.controller.entity;

import com.cmsz.paas.common.orm.id.ID;
import java.io.Serializable;

public class DisasterToleranceConfEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ID
	private long id;
	
	private long appId;
	
	private String domainName;
	
	private String masterIp;
	
	private String minionIp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getMasterIp() {
		return masterIp;
	}

	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}

	public String getMinionIp() {
		return minionIp;
	}

	public void setMinionIp(String minionIp) {
		this.minionIp = minionIp;
	}


	@Override
	public String toString() {
		return "DisasterToleranceConfEntity [id=" + id + ", appId=" + appId
				+ ", domainName=" + domainName + ", masterIp=" + masterIp
				+ ", minionIp=" + minionIp + "]";
	}
}
