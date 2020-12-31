package com.cmsz.paas.web.newmonitor.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.newmonitor.model.NewCluster;

/** 
 * @author lin.my
 * @version 创建时间：2016年12月20日 下午2:04:54
 */
public interface NewClusterService {

	/**
	 * Service层查询集群列表
	 * @param operType 
	 * @param appIds 
	 * @return
	 * @throws PaasWebException
	 */
	public List<NewCluster> queryClusterList(String dataCenterId, String appIds, String operType) throws PaasWebException;

	
}
