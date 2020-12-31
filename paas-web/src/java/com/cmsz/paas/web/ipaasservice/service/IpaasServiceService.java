package com.cmsz.paas.web.ipaasservice.service;

import java.util.List;

import com.cmsz.paas.common.model.controller.request.AppIdList;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.ipaasservice.model.IpaasService;

/**
 * 应用管理Service.
 * 
 * @author fubl
 */
public interface IpaasServiceService {

	/**
	 * 查询基础服务列表.
	 * 
	 * @param 应用列表
	 * @return 基础服务服务列表
	 */
	public List<IpaasService> queryIpaasServiceList(AppIdList appList,
			String token, int serviceType) throws PaasWebException;

	/**
	 * 删除基础服务.
	 * 
	 * @param 服务ID
	 * 
	 */
	public void deleteIpaasService(String ipaasServiceId)
			throws PaasWebException;

	/**
	 * 启动或停止基础服务.
	 * 
	 * @param 服务ID
	 *            ，服务状态
	 * 
	 */
	public RestResult startOrStopIpaasService(String ipaasServiceId,
			String operateId) throws PaasWebException;

	public void checkIpaasRelaAppService(String ipaasServiceId) throws PaasWebException;
	
	/**
	 * 查询基础服务详情.
	 * 
	 * @param 基础服务id
	 * @return 基础服务服务详情
	 */
	public IpaasService queryIpaasServiceById(String ipaasServiceId)
			throws PaasWebException;


	/**
	 * 查询基础服务默认配置
	 * 
	 * @param 基础服务类型
	 */
	public RestResult queryDefaultConfigByIpaasServiceTpye(
			String ipaasServiceType) throws PaasWebException;

	/**
	 * 创建基础服务
	 * 
	 * @param 基础服务实体
	 */
	public RestResult createIpaasService(IpaasService ipaasService)
			throws PaasWebException;
	
	/**
	 * 修改基础服务
	 * 
	 * @param 基础服务实体
	 */
	public RestResult modifyIpaasService(IpaasService ipaasService)
			throws PaasWebException;

	/**
	 * 根据基础服务id查询发布的环境变量
	 * 
	 * @param 基础服务id
	 */
	public List<EnvConfig> queryEnvsByIpaasServiceId(String ipaasServiceId)
			throws PaasWebException;
	
	/**
	 * 通过节点ip查询基础服务列表（包括实例）
	 * 
	 * @param minionIp  节点ip
	 * @return 服务列表
	 * @throws PaasWebException
	 */
	public List<IpaasService> queryIpaasServiceAndInstance(String minionIp)
			throws PaasWebException;
	
	/**
	 * 普通用户通过节点ip查询基础服务列表（包括实例）
	 * 
	 * @param minionIp  节点ip
	 * @param appIds  应用ids
	 * @param operType  操作类型
	 * @return 服务列表
	 * @throws PaasWebException
	 */
	public List<IpaasService> queryNewIpaasServiceAndInstance(String minionIp,String appIds,String operType)
			throws PaasWebException;

}