package com.cmsz.paas.common.model.k8s.v1;

public class ConfigMapVolumeSource {

	private String name;
	
	private KeyToPath[] items;
	
	private int defaultMode;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public KeyToPath[] getItems() {
		return items;
	}

	public void setItems(KeyToPath[] items) {
		this.items = items;
	}

	public int getDefaultMode() {
		return defaultMode;
	}

	public void setDefaultMode(int defaultMode) {
		this.defaultMode = defaultMode;
	}

	@Override
	public String toString() {
		return "ConfigMapVolumeSource"
				+ " [name=" + name
				+ ", items=" + items
				+ ", defaultMode=" + defaultMode + "]";
	}
	
}
