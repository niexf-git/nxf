package com.cmsz.paas.common.model.monitor.request;

import java.util.ArrayList;
import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.ComponentAssemblyEntity;

public class ComponentAssemblyList {
	private List<ComponentAssemblyEntity>  componentList = new ArrayList<ComponentAssemblyEntity>();

	public List<ComponentAssemblyEntity> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<ComponentAssemblyEntity> componentList) {
		this.componentList = componentList;
	}
}
