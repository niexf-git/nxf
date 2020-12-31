package com.cmsz.paas.web.application.model;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.ClusterToAppEntity;
import com.cmsz.paas.common.model.controller.entity.DisasterToleranceConfEntity;
import com.cmsz.paas.web.role.entity.Role;

/**
 * @author 鏇惧獩
 *
 */
public class createAppInfo {
	
	//应用ID
	private long id;
	
	//应用名称
	private String name;
	
	//数据中心
	private List<List<DataCenterInfo>> dataCenterInfos;
	
	//描述
	private String desc;
	
	//角色
	private List<List<Role>> roles;
	
	//集群信息
	private List<List<ClusterInfo>> clusterInfos;
	
	//DNS
	private List<DisasterToleranceConfEntity> dnsInfo;
	
	public List<DisasterToleranceConfEntity> getDnsInfo() {
		return dnsInfo;
	}

	public void setDnsInfo(List<DisasterToleranceConfEntity> dnsInfo) {
		this.dnsInfo = dnsInfo;
	}
	
	//选中集群Ids
	private String productClusterIds;

	//仓库用户名
	private String dockerRegistryUser;
	
	//仓库密码
	private String dockerRegistryPwd;
	
	//标题复选框
	private boolean[] checkeds;
	
	private boolean[] roleCheckeds;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<List<DataCenterInfo>> getDataCenterInfos() {
		return dataCenterInfos;
	}

	public void setDataCenterInfos(List<List<DataCenterInfo>> dataCenterInfos) {
		this.dataCenterInfos = dataCenterInfos;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<List<ClusterInfo>> getClusterInfos() {
		return clusterInfos;
	}

	public void setClusterInfos(List<List<ClusterInfo>> clusterInfos) {
		this.clusterInfos = clusterInfos;
	}

	public List<List<Role>> getRoles() {
		return roles;
	}

	public void setRoles(List<List<Role>> roles) {
		this.roles = roles;
	}

	public String getDockerRegistryUser() {
		return dockerRegistryUser;
	}

	public void setDockerRegistryUser(String dockerRegistryUser) {
		this.dockerRegistryUser = dockerRegistryUser;
	}

	public String getDockerRegistryPwd() {
		return dockerRegistryPwd;
	}

	public void setDockerRegistryPwd(String dockerRegistryPwd) {
		this.dockerRegistryPwd = dockerRegistryPwd;
	}

	public boolean[] getCheckeds() {
		return checkeds;
	}

	public void setCheckeds(boolean[] checkeds) {
		this.checkeds = checkeds;
	}

	public boolean[] getRoleCheckeds() {
		return roleCheckeds;
	}

	public void setRoleCheckeds(boolean[] roleCheckeds) {
		this.roleCheckeds = roleCheckeds;
	}


	
	private List<ClusterToAppEntity> clusters;

	public List<ClusterToAppEntity> getClusters() {
		return clusters;
	}

	public void setClusters(List<ClusterToAppEntity> clusters) {
		this.clusters = clusters;
	}

	public String getProductClusterIds() {
		return productClusterIds;
	}

	public void setProductClusterIds(String productClusterIds) {
		this.productClusterIds = productClusterIds;
	}
	
	
	
	
}
