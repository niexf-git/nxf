package com.cmsz.paas.web.cicd.service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.DepScanEntity;

/***
 * 部署service
 * @author jiangwei
 *
 */
public interface DeploymentService {
	
	/***
	 * 保存安全状态
	 */
	public void saveCheckSecurity(String flowId,int isCheck) throws PaasWebException;
	
	
	/***
	 * 查询部署&扫描详情
	 * 和查询部署共用
	 * @return
	 */
	public DepScanEntity queryDepScanEntity(String flowId,String type,String url) throws PaasWebException;
	
	
	/***
	 * 修改部署、部署&扫描共用方法
	 * @param flowId 流水id
	 * @param depScanEntity 部署实体
	 * @param type dep:部署 depScan:部署&扫描
	 * @throws PaasWebException  
	 */
	public void updateDepScan(DepScanEntity depScanEntity,String type,String url) throws PaasWebException;
	
	/***
	 * 灰度修改部署方式
	 * @param 服务ID，部署类型，灰度实例数量
	 * @throws PaasWebException  
	 */
	public void modifyDeploymentType(String appServiceId,String deploymentType) throws PaasWebException;
}
