package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.monitoroperation.model.DeployComponentInfo;
import com.cmsz.paas.web.monitoroperation.model.DeploySchemeInfo;
import com.cmsz.paas.web.monitoroperation.model.Host;
import com.cmsz.paas.web.monitoroperation.model.HostHA;

public interface HostService {

	// 查询主机列表
	public List<Host> queryHostList(String clusterId);

	// 删除主机
	public RestResult deleteHost(String hostId);
	
	// 删除集群下所有主机
	public RestResult deleteAllHost(String clusterId);

	// 创建主机
	public RestResult createHost(String clusterId, List<Host> hostList,List<HostHA> hostListHA);

	// 查询部署方案列表
	public List<DeploySchemeInfo> queryDeploySchemeList(String dataCenterType,
			String clusterType);

	// 查询部署组件列表，flag=0表示node节点主机，parameter就是集群类型；flag=1表示paas平台主机，parameter就是部署方案id
	public List<DeployComponentInfo> queryDeployComponentList(String parameter,
			String flag);
	// 查询主机，组件操作详情
	public String queryHostAndCompDetails(String nodeIp, String compName);

}
