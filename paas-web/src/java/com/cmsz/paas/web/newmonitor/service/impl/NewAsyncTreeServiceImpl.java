package com.cmsz.paas.web.newmonitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.ipaasservice.model.IpaasService;
import com.cmsz.paas.web.ipaasservice.service.IpaasServiceService;
import com.cmsz.paas.web.newmonitor.model.NewAsyncTreeItem;
import com.cmsz.paas.web.newmonitor.model.NewCluster;
import com.cmsz.paas.web.newmonitor.model.NewDataCenter;
import com.cmsz.paas.web.newmonitor.model.NewHost;
import com.cmsz.paas.web.newmonitor.service.NewAsyncTreeService;
import com.cmsz.paas.web.newmonitor.service.NewClusterService;
import com.cmsz.paas.web.newmonitor.service.NewDataCenterService;
import com.cmsz.paas.web.newmonitor.service.NewHostService;

@Service("newAsyncTreeService")
public class NewAsyncTreeServiceImpl implements NewAsyncTreeService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(NewAsyncTreeServiceImpl.class);
	
	@Autowired
	private NewDataCenterService newDataCenterService;
	
	@Autowired
	private NewClusterService clusterService;
	
	@Autowired
	private NewHostService newHostService;
	
	/** 应用服务service */
	@Autowired
	private ApplicationService appServiceService;
	
	/** 基础服务service */
	@Autowired
	private IpaasServiceService ipaasServiceService;

	@Override
	public List<NewAsyncTreeItem> queryAll(String path) {
		List<NewAsyncTreeItem> list = new ArrayList<NewAsyncTreeItem>(); 
		NewAsyncTreeItem asyncTreeItem = new NewAsyncTreeItem();
		asyncTreeItem.setName("全部");
		asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-all.png");//数据中心
		asyncTreeItem.setUrl(path + "/jsp/newmonitor/newRightMain.jsp");//?
		list.add(asyncTreeItem);
		logger.info("监控运维-左侧树-第一层-默认节点全部加载完成 ！");
		return list;
	}

	@Override
	public List<NewAsyncTreeItem> queryDataCenter(String path,String appIds,String operType) {
		List<NewAsyncTreeItem> list = new ArrayList<NewAsyncTreeItem>(); 
		List<NewDataCenter> dataCenterList = newDataCenterService.queryDataCenterList(appIds,operType);
		if(dataCenterList != null ){
			for(NewDataCenter dataCenter : dataCenterList){
				NewAsyncTreeItem asyncTreeItem = new NewAsyncTreeItem();
				asyncTreeItem.setId(dataCenter.getId());
				asyncTreeItem.setName(dataCenter.getName());
				if (dataCenter.getIsMainDataCenter().equals("1")) {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-data.png");//集群
				}else {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-data2.png");//集群
				}
				asyncTreeItem.setUrl(path + "/jsp/newmonitor/newClusterList.jsp?dataCenterId="+dataCenter.getId()+"&dataCenterName="+dataCenter.getName());//?
				asyncTreeItem.setDataCenterType(dataCenter.getIsMainDataCenter()+"");//
				list.add(asyncTreeItem);
			}
		}
		logger.info("监控运维-左侧树-第二层-数据中心加载完成 ！");
		return list;
	}

	@Override
	public List<NewAsyncTreeItem> queryCluster(String path,
			String dataCenterId, String dataCenterType,String appIds,String operType) {
		List<NewAsyncTreeItem> list = new ArrayList<NewAsyncTreeItem>();
		List<NewCluster> clusterList = clusterService.queryClusterList(dataCenterId,appIds,operType);
		if(clusterList != null){
			for(NewCluster cluster : clusterList){
				NewAsyncTreeItem asyncTreeItem = new NewAsyncTreeItem();
				asyncTreeItem.setId(cluster.getId());
				asyncTreeItem.setClusterType(cluster.getType());
				asyncTreeItem.setServiceType(cluster.getType());
				asyncTreeItem.setName(cluster.getName());
				if (cluster.getType().equals("3")) {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-cluster.png");//主机
				}else {
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-cluster2.png");//主机
				}
				asyncTreeItem.setUrl(path + "/jsp/newmonitor/newHostList.jsp?dataCenterType="+dataCenterType+"&clusterType="+cluster.getType()+"&clusterId="+cluster.getId());//?
				list.add(asyncTreeItem);
			}
		}
		logger.info("监控运维-左侧树-第三层-集群加载完成 ！");
		return list;
	}

	@Override
	public List<NewAsyncTreeItem> queryHost(String path,
			String clusterId, String serviceType) {
		List<NewAsyncTreeItem> list = new ArrayList<NewAsyncTreeItem>();
		List<NewHost> hostList = newHostService.queryHostList(clusterId);
		if(hostList != null){
			for(NewHost host : hostList){
				NewAsyncTreeItem asyncTreeItem = new NewAsyncTreeItem();
				asyncTreeItem.setId(host.getHostIP());
				asyncTreeItem.setName(host.getHostIP());
				asyncTreeItem.setServiceType(serviceType);
				asyncTreeItem.setIcon(path + "/css/ztree/img/diy/asyncTree-node.png");//服务
				asyncTreeItem.setUrl(path + "/jsp/newmonitor/newNodeDetail.jsp?minionIp="+host.getId()+"&nodeIp="+host.getHostIP()+"&serviceType="+serviceType);//节点详情
				list.add(asyncTreeItem);
			}
		}
		logger.info("监控运维-左侧树-第四层-主机加载完成 ！");
		return list;
	}

	@Override
	public List<NewAsyncTreeItem> queryService(String path,
			String hostId, String serviceType,String appIds,String operType) {
		List<NewAsyncTreeItem> list = new ArrayList<NewAsyncTreeItem>();
		String pId = "";
		if("1".equals(serviceType)){
			List<IpaasService> ipaasServiceList = ipaasServiceService.queryNewIpaasServiceAndInstance(hostId,appIds,operType);
			if(ipaasServiceList != null){
				for (int i = 0; i < ipaasServiceList.size(); i++) {
					NewAsyncTreeItem asyncTreeItem = new NewAsyncTreeItem();
					IpaasService ipaasList=ipaasServiceList.get(i);
//					String title = GrafanaJsonFileUtil.getServiceType("1") + "_"+ipaasList.getApp_name()+"_"+GrafanaJsonFileUtil.getOperType(ipaasList.getOper_type())+"_"+ ipaasList.getName();
//					String url=RestUtils.grafanaRestUrl("queryGrafanaDashboardsUrl",
//							title.toLowerCase());
					String url= path +"/jsp/newmonitor/newIpaasGrafana.jsp?ipaasServiceId="+ipaasList.getId();
					asyncTreeItem.setId(ipaasList.getId());
					asyncTreeItem.setName(ipaasList.getName());
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/app.png");//服务
					asyncTreeItem.setUrl(url);//节点详情
					pId = ipaasList.getId();
					list.add(asyncTreeItem);
					url = path
							+ "/ipaasInstance/queryNewIpaasServiceInstById.action?index="
							+ i + "&minionIp=" + hostId + "&serviceType="+ serviceType +"&instanceId=";
					List<Instance> instances = ipaasList.getInstances();
					for (int j = 0; j < instances.size(); j++) {
						Instance instance = instances.get(j);
						String instanceId = instance.getInstanceId();
						asyncTreeItem = new NewAsyncTreeItem();
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
			List<AppService> AppServiceList = appServiceService.queryNewAppServiceAndInstance(hostId,appIds,operType);
			if(AppServiceList != null){
				for (int i = 0; i < AppServiceList.size(); i++) {
					NewAsyncTreeItem asyncTreeItem = new NewAsyncTreeItem();
					AppService appList=AppServiceList.get(i);
//					String title = GrafanaJsonFileUtil.getServiceType("2") + "_"+appList.getApp_name()+"_"+GrafanaJsonFileUtil.getOperType(appList.getOper_type())+"_"+ appList.getName();
//					String url=RestUtils.grafanaRestUrl("queryGrafanaDashboardsUrl",
//							title.toLowerCase());
					String url= path +"/jsp/newmonitor/newAppGrafana.jsp?appServiceId="+appList.getId();
					asyncTreeItem.setId(appList.getId());
					asyncTreeItem.setName(appList.getName());
					asyncTreeItem.setIcon(path + "/css/ztree/img/diy/app.png");//服务
					asyncTreeItem.setUrl(url);//节点详情
					pId = appList.getId();
					list.add(asyncTreeItem);
					url = path
							+ "/appServiceInstance/queryNewAppServiceInstById.action?index="
							+ i + "&minionIp=" + hostId + "&serviceType="+serviceType+"&type="+appList.getExistGray()+"&instanceId=";
					List<Instance> instances = appList.getInstances();
					for (int j = 0; j < instances.size(); j++) {
						Instance instance = instances.get(j);
						String instanceId = instance.getInstanceId();
						asyncTreeItem = new NewAsyncTreeItem();
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
