package com.cmsz.paas.web.appserviceinst.service;

import java.util.List;

import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.model.InstanceEntity;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;

/**
 * 实例管理Service.
 *
 * @author liaohw
 */
public interface InstanceService {
	
	/**
	 * 通过应用服务id查询实例
	 *
	 * @param appServiceId 应用id
	 * @return 实例列表
	 * @throws PaasWebException
	 */
	public List<Instance> queryInstByAppServiceId(String appServiceId) throws PaasWebException;
	
	/**
	 * 重启实例
	 *
	 * @param appServiceId 应用服务ID
	 * @param instanceId 实例id
	 * @param type 
	 * @return RestResult
	 * @throws PaasWebException 
	 */
	public RestResult restartInst(String appServiceId,String instanceId, String type) throws PaasWebException;
	
	/**
	 * 把控制中心的bean转为前台使用的bean
	 *
	 * @param 控制中心实例bean
	 * @return 前台实例bean
	 */
	public Instance translateInstance(com.cmsz.paas.common.model.controller.entity.Instance instance);
	
	/**
	 * 把控制中心的bean转为前台使用的bean
	 *
	 * @param 控制中心实例bean
	 * @return 前台实例bean
	 */
	public Instance translateInsts(com.cmsz.paas.common.model.controller.entity.Instance instance);
	
	/**
	 * 诊断状态非正常的实例（实际是诊断镜像）
	 * @param type 
	 *
	 * @param 应用服务id
	 * @return 错误信息
	 */
	public String diagnosisAppService(String appServiceId, String type) throws PaasWebException;
	
	/**
	 * 查询实例容器详情
	 * @param containerId
	 * @param hostIP
	 * @return
	 * @throws PaasWebException
	 */
	public String dialogContainerDetails(String containerId, String hostIP) throws PaasWebException;
	
	/**
	 * 查询实例详情
	 * @param containerId
	 * @param hostIP
	 * @return
	 * @throws PaasWebException
	 */
	public InstanceEntity queryPodDetail(String namespace, String podName) throws PaasWebException;
	
}
