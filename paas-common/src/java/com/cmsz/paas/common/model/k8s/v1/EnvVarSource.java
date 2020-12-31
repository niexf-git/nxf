package com.cmsz.paas.common.model.k8s.v1;

public class EnvVarSource {

	private ObjectFieldSelector fieldRef;
	
	private ResourceFieldSelector resourceFieldRef;
	
	private ConfigMapKeySelector configMapKeyRef;
	
	private SecretKeySelector secretKeyRef;

	public ObjectFieldSelector getFieldRef() {
		return fieldRef;
	}

	public void setFieldRef(ObjectFieldSelector fieldRef) {
		this.fieldRef = fieldRef;
	}

	public ResourceFieldSelector getResourceFieldRef() {
		return resourceFieldRef;
	}

	public void setResourceFieldRef(ResourceFieldSelector resourceFieldRef) {
		this.resourceFieldRef = resourceFieldRef;
	}

	public ConfigMapKeySelector getConfigMapKeyRef() {
		return configMapKeyRef;
	}

	public void setConfigMapKeyRef(ConfigMapKeySelector configMapKeyRef) {
		this.configMapKeyRef = configMapKeyRef;
	}

	public SecretKeySelector getSecretKeyRef() {
		return secretKeyRef;
	}

	public void setSecretKeyRef(SecretKeySelector secretKeyRef) {
		this.secretKeyRef = secretKeyRef;
	}

	@Override
	public String toString() {
		return "EnvVarSource "
				+ "[fieldRef=" + fieldRef 
				+ ", resourceFieldRef=" + resourceFieldRef
				+ ", configMapKeyRef=" + configMapKeyRef
				+ ", secretKeyRef=" + secretKeyRef +"]";
	}
}
