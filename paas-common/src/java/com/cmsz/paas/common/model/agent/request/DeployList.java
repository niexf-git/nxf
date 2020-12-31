package com.cmsz.paas.common.model.agent.request;

import java.util.List;

import com.cmsz.paas.common.model.agent.entity.ComponentEntity;
import com.cmsz.paas.common.model.agent.entity.HostnameEntity;

public class DeployList {
	private List<ComponentEntity> componentList;

	private List<HostnameEntity> hostnameList;

	public List<ComponentEntity> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<ComponentEntity> componentList) {
		this.componentList = componentList;
	}

	public List<HostnameEntity> getHostnameList() {
		return hostnameList;
	}

	public void setHostnameList(List<HostnameEntity> hostnameList) {
		this.hostnameList = hostnameList;
	}
	
	@Override
	public String toString() {
		return "DeployList [componentList=" + componentList 
				+ "hostnameList=" + hostnameList + "]";
	}

}
