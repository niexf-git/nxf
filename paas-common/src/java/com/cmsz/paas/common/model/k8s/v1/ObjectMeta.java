package com.cmsz.paas.common.model.k8s.v1;

import java.util.Map;

public class ObjectMeta {

	private String name;

	private String generateName;

	private String namespace;

	private String selfLink;

	private String uid;

	private String resourceVersion;

	private Long generation;

	private String creationTimestamp;
	
	private String deletionTimestamp;
	
	private Long deletionGracePeriodSeconds;

	private Map<String,String> labels;

	private Map<String,String> annotations;
	
	private OwnerReference[] ownerReferences;
	
	private String[] finalizers;
	
	private String clusterName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenerateName() {
		return generateName;
	}

	public void setGenerateName(String generateName) {
		this.generateName = generateName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public Long getGeneration() {
		return generation;
	}

	public void setGeneration(Long generation) {
		this.generation = generation;
	}

	public String getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(String creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getDeletionTimestamp() {
		return deletionTimestamp;
	}

	public void setDeletionTimestamp(String deletionTimestamp) {
		this.deletionTimestamp = deletionTimestamp;
	}

	public Long getDeletionGracePeriodSeconds() {
		return deletionGracePeriodSeconds;
	}

	public void setDeletionGracePeriodSeconds(Long deletionGracePeriodSeconds) {
		this.deletionGracePeriodSeconds = deletionGracePeriodSeconds;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public Map<String, String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<String, String> annotations) {
		this.annotations = annotations;
	}

	public OwnerReference[] getOwnerReferences() {
		return ownerReferences;
	}

	public void setOwnerReferences(OwnerReference[] ownerReferences) {
		this.ownerReferences = ownerReferences;
	}

	public String[] getFinalizers() {
		return finalizers;
	}

	public void setFinalizers(String[] finalizers) {
		this.finalizers = finalizers;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	@Override
	public String toString() {
		return "ObjectMeta "
				+ "[name=" + name 
				+ ", generateName=" + generateName
				+ ", namespace=" + namespace 
				+ ", selfLink=" + selfLink
				+ ", uid=" + uid 
				+ ", resourceVersion=" + resourceVersion
				+ ", generation=" + generation 
				+ ", creationTimestamp=" + creationTimestamp 
				+ ", deletionTimestamp="+ deletionTimestamp
				+ ", deletionGracePeriodSeconds="+ deletionGracePeriodSeconds
				+ ", labels=" + labels 
				+ ", annotations=" + annotations 
				+", ownerReferences="+ ownerReferences
				+", finalizers="+ finalizers
				+", clusterName="+ clusterName + "]";
	}
}
