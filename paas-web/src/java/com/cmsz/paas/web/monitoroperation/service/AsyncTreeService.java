package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.web.monitoroperation.model.AsyncTreeItem;

/**
 * 监控运维-左侧异步树Service
 * 
 * @author liaohw
 * @date 2016-12-20
 */
public interface AsyncTreeService {

	// 查询第一层默认全部节点
	public List<AsyncTreeItem> queryAll(String path);

	// 查询所有数据中心
	public List<AsyncTreeItem> queryDataCenter(String path);

	// 查询某一个数据中心下的集群列表
	public List<AsyncTreeItem> queryCluster(String path, String dataCenterId,
			String dataCenterType);

	// 查询某一个集群下的主机列表
	public List<AsyncTreeItem> queryHost(String path, String clusterId,
			String serviceType);

	// 查询某一个主机下的服务列表列表
	public List<AsyncTreeItem> queryService(String path, String hostId,
			String serviceType);

}
