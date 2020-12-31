package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.monitoroperation.model.AppServiceDetail;
import com.cmsz.paas.web.monitoroperation.model.ComponentInfo;
import com.cmsz.paas.web.monitoroperation.model.IpaasServiceDetail;

/**
 * 监控运维模块Service.
 * 
 * @author ccl
 */
public interface ComponentService {
	/**
	 * 查询组件列表
	 * 
	 * @throws Exception
	 */
	public List<ComponentInfo> queryComponentList(String minionIp)
			throws PaasWebException;
	/**
	 * 操作组件
	 * 
	 * @throws Exception
	 */
	public void operationComponent(String nodeId, String name,String command)
			throws PaasWebException;
//	/**
//	 * 通过节点ip查询基础服务列表（包括实例）
//	 * 
//	 * @param minionIp
//	 *            节点ip
//	 * @return 基础服务列表
//	 * @throws PaasWebException
//	 */
//	public List<IpaasServiceDetail> queryIpaasServiceByMinionIp(String minionIp)
//			throws PaasWebException;
//
//	/**
//	 * 通过节点ip查询应用服务列表（包括实例）
//	 * 
//	 * @param minionIp
//	 *            节点ip
//	 * @return 服务列表
//	 * @throws PaasWebException
//	 */
//	public List<AppServiceDetail> queryAppServiceByMinionIp(String minionIp)
//			throws PaasWebException;
//	/**
//	 * 把控制中心的bean转为前台使用的bean
//	 *
//	 * @param 控制中心实例bean
//	 * @return 前台实例bean
//	 */
//	public Instance translateInsts(com.cmsz.paas.common.model.controller.entity.Instance instance);

}
