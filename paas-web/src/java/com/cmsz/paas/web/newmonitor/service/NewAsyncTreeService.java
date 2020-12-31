package com.cmsz.paas.web.newmonitor.service;

import java.util.List;

import com.cmsz.paas.web.newmonitor.model.NewAsyncTreeItem;

/**
 * 监控运维-左侧异步树Service
 * 
 * @author liaohw
 * @date 2016-12-20
 */
public interface NewAsyncTreeService {

	// 查询第一层默认全部节点
	public List<NewAsyncTreeItem> queryAll(String path);

	// 查询所有数据中心
	public List<NewAsyncTreeItem> queryDataCenter(String path,String appIds,String operType);

	// 查询某一个数据中心下的集群列表
	public List<NewAsyncTreeItem> queryCluster(String path, String dataCenterId,
			String dataCenterType,String appIds,String operType);

	// 查询某一个集群下的主机列表
	public List<NewAsyncTreeItem> queryHost(String path, String clusterId,
			String serviceType);

	// 查询某一个主机下的服务列表列表
	public List<NewAsyncTreeItem> queryService(String path, String hostId,
			String serviceType,String appIds,String operType);

}
