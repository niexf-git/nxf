package com.cmsz.paas.common.model.agent.request;

import java.util.ArrayList;
import java.util.List;

import com.cmsz.paas.common.model.agent.entity.ComponentEntity;

public class ComponentList {
	
	private List<ComponentEntity> componentList;

	public ComponentList(ComponentList com){
		componentList = com.getComponentList();
	}
	
	public ComponentList(){
	}
	public List<ComponentEntity> getComponentList() {
		return componentList;
	}
	
	public void setComponent(String componentName,int port,String status){
		if(componentList == null){
			componentList = new ArrayList<ComponentEntity>();
			ComponentEntity entity = new ComponentEntity();
			entity.setComponentName(componentName);
			entity.setPort(port);
			entity.setStatus(status);
			componentList.add(entity);
		}else{
			int i=0;
			for(i=0;i<componentList.size();i++){
				if(componentList.get(i).getComponentName().equals(componentName) &&
						componentList.get(i).getPort()==port){
					componentList.get(i).setStatus(status);
					break;
				}
			}
			
			if(i>=componentList.size()){
				ComponentEntity entity = new ComponentEntity();
				entity.setComponentName(componentName);
				entity.setPort(port);
				entity.setStatus(status);
				componentList.add(entity);
			}
		}
	}
	
	public void deleteComponent(String componentName) {
		if(componentList == null){
			return;
		}
		for (int i = 0; i < componentList.size(); i++) {
			String name = componentList.get(i).getComponentName();			
			if(name.equals(componentName)){
				componentList.remove(i);
			}
		}
	}

	public void setComponentList(List<ComponentEntity> componentList) {
		this.componentList = componentList;
	}

	@Override
	public String toString() {
		return "ComponentList [componentList=" + componentList + "]";
	}	
}
