package com.cmsz.paas.web.overview.service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.overview.model.ActualResourceRate;
import com.cmsz.paas.web.overview.model.AssignedResourceRate;
import com.cmsz.paas.web.overview.model.FlowBuildState;
import com.cmsz.paas.web.overview.model.InstanceState;
import com.cmsz.paas.web.overview.model.ServiceState;
import com.cmsz.paas.web.overview.model.TotalResource;
import com.cmsz.paas.web.overview.model.AlarmDetailsList;

public interface OverviewService {

	/**
	 * 查询服务状态统计数据
	 * 
	 * @throws PaasWebException
	 */
	public ServiceState queryServiceState(String appIds, String operType) throws PaasWebException;

	/**
	 * 查询实例状态统计数据
	 * 
	 * @param appIds
	 * @param aperType
	 * @throws PaasWebException
	 */
	public InstanceState queryInstanceState(String appIds, String operType) throws PaasWebException;

	/**
	 * 查询已分配资源利用率：包括CPU、内存利用率
	 * 
	 * @param appIds
	 * @param operType
	 * @throws PaasWebException
	 */
	public AssignedResourceRate queryAssignedResourceRate(String appIds, String operType) throws PaasWebException;

	/**
	 * 查询实际使用资源利用率：包括CPU、内存、磁盘利用率
	 * 
	 * @param appIds
	 * @param operType
	 * @throws PaasWebException
	 */
	public ActualResourceRate queryActualResourceRate(String appIds, String operType) throws PaasWebException;

	/**
	 * 查询流水的各种构建状态(成功，失败，构建中)情况
	 * 
	 * @param appIds
	 * @param operType
	 * @throws PaasWebException
	 */
	public FlowBuildState queryFlowBuildState(String appIds, String operType) throws PaasWebException;

	/**
	 * 查询告警统计数据：告警时间、告警次数
	 * 
	 * @param appIds
	 * @param operType
	 * @throws PaasWebException
	 */
	public AlarmDetailsList queryAlarmDetails(String appIds,String operType) throws PaasWebException;
	
	/**
	 * 查询资源统计数据：主机、CPU、内存、磁盘
	 * 
	 * @param appIds
	 * @param operType
	 * @throws PaasWebException
	 */
	public TotalResource queryTotalResource(String appIds,String operType) throws PaasWebException;
	
}
