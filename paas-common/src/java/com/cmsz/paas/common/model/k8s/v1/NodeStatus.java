package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;
import java.util.Map;

public class NodeStatus {

	private Map<String,String> capacity;
	
	private Map<String,String> allocatable;

	private String phase;

	private NodeCondition[] conditions;

	private NodeAddress[] addresses;
	
	private NodeDaemonEndpoints daemonEndpoints;

	private NodeSystemInfo nodeInfo;
	
	private ContainerImage[] images;
	
	private String[] volumesInUse;
	
	private AttachedVolume[] volumesAttached;

	public Map<String, String> getCapacity() {
		return capacity;
	}

	public void setCapacity(Map<String, String> capacity) {
		this.capacity = capacity;
	}

	public Map<String, String> getAllocatable() {
		return allocatable;
	}

	public void setAllocatable(Map<String, String> allocatable) {
		this.allocatable = allocatable;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public NodeCondition[] getConditions() {
		return conditions;
	}

	public void setConditions(NodeCondition[] conditions) {
		this.conditions = conditions;
	}

	public NodeAddress[] getAddresses() {
		return addresses;
	}

	public void setAddresses(NodeAddress[] addresses) {
		this.addresses = addresses;
	}

	public NodeDaemonEndpoints getDaemonEndpoints() {
		return daemonEndpoints;
	}

	public void setDaemonEndpoints(NodeDaemonEndpoints daemonEndpoints) {
		this.daemonEndpoints = daemonEndpoints;
	}

	public NodeSystemInfo getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(NodeSystemInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

	public ContainerImage[] getImages() {
		return images;
	}

	public void setImages(ContainerImage[] images) {
		this.images = images;
	}

	public String[] getVolumesInUse() {
		return volumesInUse;
	}

	public void setVolumesInUse(String[] volumesInUse) {
		this.volumesInUse = volumesInUse;
	}

	public AttachedVolume[] getVolumesAttached() {
		return volumesAttached;
	}

	public void setVolumesAttached(AttachedVolume[] volumesAttached) {
		this.volumesAttached = volumesAttached;
	}

	@Override
	public String toString() {
		return "NodeStatus "
				+ "[capacity=" + capacity 
				+ ", allocatable=" + allocatable
				+ ", phase=" + phase
				+ ", conditions=" + Arrays.toString(conditions)
				+ ", addresses=" + addresses 
				+ ", daemonEndpoints=" + daemonEndpoints 
				+ ", nodeInfo=" + nodeInfo 
				+ ", images=" + images 
				+ ", volumesInUse=" + volumesInUse 
				+ ", volumesAttached=" + volumesAttached + "]";
	}
}
