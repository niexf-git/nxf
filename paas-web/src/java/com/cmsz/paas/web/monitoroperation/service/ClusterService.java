package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.monitoroperation.model.Cluster;

/** 
 * @author lin.my
 * @version 创建时间：2016年12月20日 下午2:04:54
 */
public interface ClusterService {

	/**
	 * Service层查询集群列表
	 * @return
	 * @throws PaasWebException
	 */
	public List<Cluster> queryClusterList(String dataCenterId) throws PaasWebException;

	/**
	 * Service层新增集群
	 * @param cluster
	 * @throws PaasWebException
	 */
	public void createCluster(Cluster cluster) throws PaasWebException;

	/**
	 * Service层删除集群
	 * @param id
	 * @param id2 
	 * @throws PaasWebException
	 */
	public void deleteCluster(String dataCenterId, String clusterId) throws PaasWebException;

	public void checkAppsInCluster(String clusterId) throws PaasWebException;

	public void updateCluster(Cluster cluster) throws PaasWebException;
	
}
