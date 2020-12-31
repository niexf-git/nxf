package com.cmsz.paas.web.log.service;

import com.cmsz.paas.web.base.exception.PaasWebException;


/**
 * 标准输出日志获取URL接口
 * @author li.lv
 * 2016-4-26	
 */
public interface StdLogService {
	/**
	 * 获取应用服务标准输出日志的URL
	 * @param appServiceId，instanceId,hostIp,since
	 * @return
	 * @throws PaasWebException
	 */
	public String queryAppServiceStdLogUrl(String appServiceId, String instanceId,String intType, String hostIp, String since) throws PaasWebException;
	
	/**
	 * 根据基础服务实例ID获取标准输出日志信息
	 * @param instanceId,ipaasServiceId,hostIp
	 * @return
	 * @throws Exception
	 */
	public String queryIpaasServiceStdLog(String ipaasServiceId,String instanceId,String hostIp,long since) throws Exception;
	
	
	/***
	 * 查询容器历史日志
	 * @param namespace 
	 * @param instanceName
	 * @param line 获取行数 0为全部获取
	 * @return
	 * @throws Exception 
	 */
	public String queryChLogUrl(String namespace,String instanceName,String line) throws Exception;
}
