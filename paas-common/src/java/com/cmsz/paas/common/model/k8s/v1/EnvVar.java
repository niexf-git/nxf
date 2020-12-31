package com.cmsz.paas.common.model.k8s.v1;

public class EnvVar {

	private String name;

	private String value;

	private EnvVarSource valueFrom;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValueFrom(EnvVarSource valueFrom) {
		this.valueFrom = valueFrom;
	}

	public EnvVarSource getValueFrom() {
		return valueFrom;
	}

	@Override
	public String toString() {
		return "EnvVar "
				+ "[name=" + name 
				+ ", value=" + value 
				+ ", valueFrom=" + valueFrom + "]";
	}
}
