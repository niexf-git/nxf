package com.cmsz.paas.web.appservice.service;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;
import com.cmsz.paas.common.model.controller.request.AppIdList;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.monitoroperation.model.Cluster;

/**
 * 应用管理Service.
 * 
 * @author fubl
 */
public interface ApplicationService {

	/**
	 * 查询应用服务列表.
	 * 
	 * @param 应用列表
	 * @return 应用服务列表
	 */
	public List<AppService> queryAppServiceList(AppIdList appIdList,
			String token) throws PaasWebException;

	/**
	 * 查询应用服务详情.
	 * 
	 * @param 应用服务id
	 * @return 应用服务详情
	 */
	public AppService queryAppServiceById(String appServiceId)
			throws PaasWebException;
	
	/***
	 * 启动应用服务
	 * @param appServiceId
	 * @throws PaasWebException
	 */
	public void startAppService(String appServiceId) throws PaasWebException;
	
	public void allStartAppService(String appServiceId) throws PaasWebException;
	
	public void selfStartAppService(String appServiceId) throws PaasWebException;
	
	public void checkAppServiceRelaIpaas(String appServiceId) throws PaasWebException;
	
	/***
	 * 批量启动应用服务
	 * 
	 * @param appServiceIds
	 *            应用服务Id
	 * @throws PaasWebException
	 */
	public void batchStartAppServices(String appServiceIds)
			throws PaasWebException;

	/***
	 * 批量停止应用服务
	 * 
	 * @param appServiceIds
	 *            应用服务Id
	 * @throws PaasWebException
	 */
	public void batchStopAppServices(String appServiceIds)
			throws PaasWebException;

	/***
	 * 批量重启应用服务
	 * 
	 * @param appServiceIds
	 *            应用服务Id
	 * @throws PaasWebException
	 */
	public void forcedRestartAppServices(String appServiceIds)
			throws PaasWebException;

	/***
	 * 批量删除应用服务
	 * 
	 * @param appServiceIds
	 *            应用服务Id
	 * @throws PaasWebException
	 */
	public void batchDeleteAppServices(String appServiceIds)
			throws PaasWebException;

	/***
	 * 发送信号
	 * 
	 * @param appServiceIds
	 *            应用服务Id
	 * @param signal
	 *            信息量
	 * @throws PaasWebException
	 */
	public RestResult sendSignal2AppService(String appServiceIds, String signal)
			throws PaasWebException;

	/***
	 * 删除应用服务
	 * 
	 * @param appServiceId
	 *            应用服务ID
	 * @throws PaasWebException
	 */
	public void deleteAppService(String appServiceId) throws PaasWebException;

	/***
	 * 创建应用服务
	 * 
	 * @param appService
	 *            应用服务model
	 * @throws PaasWebException
	 */
	public IdValue createAppService(AppService appService)
			throws PaasWebException;

	/**
	 * 根据界面传值的应用服务model转换成控制中心需要的应用服务entity
	 * 
	 * @param appService
	 * @return
	 */
	public AppServiceEntity genAppServiceEntity(AppService a);
	
	/**
	 * 通过应用id和操作类型查询集群列表
	 * 
	 * @param appID   应用id
	 * @param operType   操作类型
	 * @param type   查询类型 type为app时以应用Id为准否则以数据中心id为准
	 * @param appSelectedId 
	 * @return 集群列表
	 * @throws PaasWebException
	 */
	public List<Cluster> queryClusterList(String appId,
			String operType,String type, String appSelectedId,String operTypes) throws PaasWebException;
	
	/**
	 * 通过节点ip查询应用服务列表（包括实例）
	 * 
	 * @param minionIp  节点ip
	 * @return 服务列表
	 * @throws PaasWebException
	 */
	public List<AppService> queryAppServiceAndInstance(String minionIp)
			throws PaasWebException;
	
	/**
	 * 通过节点ip查询应用服务列表（包括实例）
	 * 
	 * @param minionIp  节点ip
	 * @param appIds  应用ids
	 * @param operType  操作类型
	 * @return 服务列表
	 * @throws PaasWebException
	 */
	public List<AppService> queryNewAppServiceAndInstance(String minionIp,String appIds,String operType)
			throws PaasWebException;
	
	public List<String> queryCluserHostIpList(long cluserId)
			throws PaasWebException;
	
	
	/***
	 * 一键升级服务版本
	 * @param ids
	 * @throws PaasWebException
	 */
	public void batchUpgradeServiceVersions(String ids) throws PaasWebException;
	
	
	/***
	 * 校验Ip是否有冲突
	 * @param ip
	 * @return
	 * @throws PaasWebException
	 */
	public boolean isCheckIp(String appServiceId,String ip) throws PaasWebException;
	
	/***
	 * 校验CPU\内存是否超额
	 * @param type 校验类型
	 * @param instNumber 实例个数
	 * @param appIds 选中应用Id
	 * @param types 选中操作类型
	 * @param cpuNumber CPU最小值
	 * @param MemNumber 内存最小值
	 * @return
	 * @throws PaasWebException
	 */
	public String isExcess(String type,String instNumber,String appIds,String types,String cpuNumber,String MemNumber,String appServiceId) throws PaasWebException;
	
}