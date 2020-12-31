package com.cmsz.paas.web.monitoroperation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.base.util.GrafanaJsonFileUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.ipaasservice.model.IpaasService;
import com.cmsz.paas.web.ipaasservice.service.IpaasServiceService;
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.cmsz.paas.web.monitoroperation.model.DataCenter;
import com.cmsz.paas.web.monitoroperation.model.Host;
import com.cmsz.paas.web.monitoroperation.model.AsyncTreeItem;
import com.cmsz.paas.web.monitoroperation.service.ClusterService;
import com.cmsz.paas.web.monitoroperation.service.DataCenterService;
import com.cmsz.paas.web.monitoroperation.service.HostService;
import com.cmsz.paas.web.monitoroperation.service.AsyncTreeService;

@Service("asyncTreeService")
public class AsyncTreeServiceImpl implements AsyncTreeService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(AsyncTreeServiceImpl.class);
	
	@Autowired
	private DataCenterService dataCenterService;
	
	@Autowired
	private ClusterService clusterService;
	
	@Autowired
	private HostService hostService;
	
	/** 应用服务service */
	@Autowired
	private ApplicationService appServiceService;
	
	/** 基础服务service */
	@Autowired
	private IpaasServiceService ipaasServiceService;

	@Override
	public List<AsyncTreeItem> queryAll(String path) {
		List<AsyncTreeItem> list = new ArrayList<AsyncTreeItem>(); 
		AsyncTreeItem asyncTreeItem = new AsyncTreeItem();
		asyncTreeItem.setName("全部");
		asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-all.png");//数据中心
		asyncTreeItem.setUrl(path + "/jsp/monitoroperation/rightMain.jsp");//?
		list.add(asyncTreeItem);
		logger.info("监控运维-左侧树-第一层-默认节点全部加载完成 ！");
		return list;
	}

	@Override
	public List<AsyncTreeItem> queryDataCenter(String path) {
		List<AsyncTreeItem> list = new ArrayList<AsyncTreeItem>(); 
		List<DataCenter> dataCenterList = dataCenterService.queryDataCenterList();
		if(dataCenterList != null ){
			for(DataCenter dataCenter : dataCenterList){
				AsyncTreeItem asyncTreeItem = new AsyncTreeItem();
				asyncTreeItem.setId(dataCenter.getId());
				asyncTreeItem.setName(dataCenter.getName());
				if (dataCenter.getIsMainDataCenter().equals("1")) {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-data.png");//集群
				}else {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-data2.png");//集群
				}
				asyncTreeItem.setUrl(path + "/jsp/monitoroperation/clusterList.jsp?dataCenterId="+dataCenter.getId()+"&dataCenterName="+dataCenter.getName());//?
				asyncTreeItem.setDataCenterType(dataCenter.getIsMainDataCenter()+"");//
				list.add(asyncTreeItem);
			}
		}
		logger.info("监控运维-左侧树-第二层-数据中心加载完成 ！");
		return list;
	}

	@Override
	public List<AsyncTreeItem> queryCluster(String path,
			String dataCenterId, String dataCenterType) {
		List<AsyncTreeItem> list = new ArrayList<AsyncTreeItem>();
		List<Cluster> clusterList = clusterService.queryClusterList(dataCenterId);
		if(clusterList != null){
			for(Cluster cluster : clusterList){
				AsyncTreeItem asyncTreeItem = new AsyncTreeItem();
				asyncTreeItem.setId(cluster.getId());
				asyncTreeItem.setClusterType(cluster.getType());
				asyncTreeItem.setServiceType(cluster.getType());
				asyncTreeItem.setName(cluster.getName());
				if (cluster.getType().equals("3")) {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-cluster.png");//主机
				}else {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-cluster2.png");//主机
				}
				asyncTreeItem.setUrl(path + "/jsp/monitoroperation/hostList.jsp?dataCenterType="+dataCenterType+"&clusterType="+cluster.getType()+"&clusterId="+cluster.getId());//?
				list.add(asyncTreeItem);
			}
		}
		logger.info("监控运维-左侧树-第三层-集群加载完成 ！");
		return list;
	}

	@Override
	public List<AsyncTreeItem> queryHost(String path,
			String clusterId, String serviceType) {
		List<AsyncTreeItem> list = new ArrayList<AsyncTreeItem>();
		List<Host> hostList = hostService.queryHostList(clusterId);
		if(hostList != null){
			for(Host host : hostList){
				AsyncTreeItem asyncTreeItem = new AsyncTreeItem();
				asyncTreeItem.setId(host.getHostIP());
				asyncTreeItem.setName(host.getHostIP());
				asyncTreeItem.setServiceType(serviceType);
				asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-node.png");//服务
				asyncTreeItem.setUrl(path + "/jsp/monitoroperation/nodeDetail.jsp?minionIp="+host.getId()+"&nodeIp="+host.getHostIP()+"&serviceType="+serviceType);//节点详情
				list.add(asyncTreeItem);
			}
		}
		logger.info("监控运维-左侧树-第四层-主机加载完成 ！");
		return list;
	}

	@Override
	public List<AsyncTreeItem> queryService(String path,
			String hostId, String serviceType) {
		List<AsyncTreeItem> list = new ArrayList<AsyncTreeItem>();String pId = "";
		if("1".equals(serviceType)){
			List<IpaasService> ipaasServiceList = ipaasServiceService.queryIpaasServiceAndInstance(hostId);
			if(ipaasServiceList != null){
				for (int i = 0; i < ipaasServiceList.size(); i++) {
					AsyncTreeItem asyncTreeItem = new AsyncTreeItem();
					IpaasService ipaasList=ipaasServiceList.get(i);
//					String title = GrafanaJsonFileUtil.getServiceType("1") + "_"+ipaasList.getApp_name()+"_"+GrafanaJsonFileUtil.getOperType(ipaasList.getOper_type())+"_"+ ipaasList.getName();
//					String url=RestUtils.grafanaRestUrl("queryGrafanaDashboardsUrl",
//							title.toLowerCase());
					String url= path +"/jsp/monitoroperation/ipaasGrafana.jsp?ipaasServiceId="+ipaasList.getId();
					asyncTreeItem.setId(ipaasList.getId());
					asyncTreeItem.setName(ipaasList.getName());
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/app.png");//服务
					asyncTreeItem.setUrl(url);//节点详情
					pId = ipaasList.getId();
					list.add(asyncTreeItem);
					url = path
							+ "/ipaasInstance/queryIpaasServiceInstById.action?index="
							+ i + "&minionIp=" + hostId + "&serviceType="+ serviceType +"&instanceId=";
					List<Instance> instances = ipaasList.getInstances();
					for (int j = 0; j < instances.size(); j++) {
						Instance instance = instances.get(j);
						String instanceId = instance.getInstanceId();
						asyncTreeItem = new AsyncTreeItem();
						asyncTreeItem.setId(instanceId);
						asyncTreeItem.setpId(pId);
						asyncTreeItem.setName(instanceId);
						asyncTreeItem.setIcon(path + "/css/ztree/img/diy/instance.png");//实例
						asyncTreeItem.setUrl(url+instanceId);//节点详情
						list.add(asyncTreeItem);
					} 
				}
			}
		}else if("2".equals(serviceType)){
			List<AppService> AppServiceList = appServiceService.queryAppServiceAndInstance(hostId);
			if(AppServiceList != null){
				for (int i = 0; i < AppServiceList.size(); i++) {
					AsyncTreeItem asyncTreeItem = new AsyncTreeItem();
					AppService appList=AppServiceList.get(i);
//					String title = GrafanaJsonFileUtil.getServiceType("2") + "_"+appList.getApp_name()+"_"+GrafanaJsonFileUtil.getOperType(appList.getOper_type())+"_"+ appList.getName();
//					String url=RestUtils.grafanaRestUrl("queryGrafanaDashboardsUrl",
//							title.toLowerCase());
					String url= path +"/jsp/monitoroperation/appGrafana.jsp?appServiceId="+appList.getId();
					asyncTreeItem.setId(appList.getId());
					asyncTreeItem.setName(appList.getName());
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/app.png");//服务
					asyncTreeItem.setUrl(url);//节点详情
					pId = appList.getId();
					list.add(asyncTreeItem);
					url = path
							+ "/appServiceInstance/queryAppServiceInstById.action?index="
							+ i + "&minionIp=" + hostId + "&serviceType="+serviceType+"&type="+appList.getExistGray()+"&instanceId=";
					List<Instance> instances = appList.getInstances();
					for (int j = 0; j < instances.size(); j++) {
						Instance instance = instances.get(j);
						String instanceId = instance.getInstanceId();
						asyncTreeItem = new AsyncTreeItem();
						asyncTreeItem.setId(instanceId);
						asyncTreeItem.setName(instanceId);
						asyncTreeItem.setpId(pId);
						asyncTreeItem.setIcon(path + "/css/ztree/img/diy/instance.png");//实例
						asyncTreeItem.setUrl(url+instanceId);//节点详情
						list.add(asyncTreeItem);
					} 
				}
			}
		}
		logger.info("监控运维-左侧树-第五（六）层-服务（包括服务下实例）加载完成 ！");
		return list;
	}
}
