package com.cmsz.paas.web.monitoroperation.service.impl;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.monitor.entity.DeployEntity;
import com.cmsz.paas.common.model.monitor.entity.NodeEntity;
import com.cmsz.paas.common.model.monitor.entity.SchemeEntity;
import com.cmsz.paas.common.model.monitor.response.DeployList;
import com.cmsz.paas.common.model.monitor.response.NodeList;
import com.cmsz.paas.common.model.monitor.response.SchemeList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.dao.HostDao;
import com.cmsz.paas.web.monitoroperation.model.DeployComponentInfo;
import com.cmsz.paas.web.monitoroperation.model.DeploySchemeInfo;
import com.cmsz.paas.web.monitoroperation.model.Host;
import com.cmsz.paas.web.monitoroperation.model.HostHA;
import com.cmsz.paas.web.monitoroperation.service.HostService;

import net.sf.json.JSONObject;

@Service("hostService")
public class HostServiceImpl implements HostService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(HostServiceImpl.class);
	
	@Autowired
	private HostDao hostDao;

	@Override
	public List<Host> queryHostList(String clusterId) {
		List<Host> list = new ArrayList<Host>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl("queryHostListUrl", clusterId);
			logger.info("开始调用Rest接口：" + url);
			
			ResponseInfo responseInfo = hostDao.get(url, NodeList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			NodeList nodeList = (NodeList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if( nodeList != null && nodeList.getNodeList() != null){
					for(NodeEntity nodeEntity : nodeList.getNodeList()){
						Host host = translateToHost(nodeEntity);
						list.add(host);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询主机列表错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_HOST_LIST_ERROR,ex.getMessage());
			}
		}
		return list;
	}
	
	/*
	 * 把监控中心的主机bean转为前台Host对象
	 */
	private Host translateToHost(NodeEntity nodeEntity) {
		Host host = new Host();
		host.setId(nodeEntity.getId()+"");
		host.setHostIP(nodeEntity.getHostIp());
		host.setDeployId(nodeEntity.getDeployId()+"");
		host.setPassword(nodeEntity.getPasswd());
		host.setStatus(nodeEntity.getStatus());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nodeEntity.getInsertTime());
		host.setCreateTime(dateStr);
		return host;
	}

	@Override
	public RestResult deleteHost(String hostId) {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl("deleteHostUrl", hostId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = hostDao.delete(url, ResponseInfo.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(retCode);
				result.setMsg(msg);
			}else {
				throw new PaasWebException(retCode, msg);
			}
			
		}catch (Exception ex) {
			logger.error("删除主机错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_HOST_ERROR,ex.getMessage());
			}
		}
		return result;
	}
	
	@Override
	public RestResult deleteAllHost(String clusterId) {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl("deleteAllHostUrl", clusterId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = hostDao.delete(url, ResponseInfo.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(retCode);
				result.setMsg(msg);
			}else {
				throw new PaasWebException(retCode, msg);
			}
			
		}catch (Exception ex) {
			logger.error("删除集群下所有主机错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_HOST_ERROR,ex.getMessage());
			}
		}
		return result;
	}

	@Override
	public RestResult createHost(String clusterId,List<Host> hostList,List<HostHA> hostListHA) {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			NodeList nodeList = translateToControllerNodeList(hostList,hostListHA);
			if(nodeList!=null&&nodeList.getNodeList()!=null&&nodeList.getNodeList().size()>0){
				for(NodeEntity ne:nodeList.getNodeList()){
					if(ne!=null){
						ne.setClusterId(Long.parseLong(clusterId));
					}
				}
			}
			String url = RestUtils.monitorOperationRestUrl("createHostUrl", clusterId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = hostDao.create(url, nodeList, ResponseInfo.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				result.setMsg(msg);
				result.setRetCode(retCode);
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("新增主机错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.CREATE_HOST_ERROR,ex.getMessage());
			}
		}
		return result;
	}
	
	/*
	 * 把前台数据转换成监控中心需要的bean
	 */
	private NodeList translateToControllerNodeList(List<Host> hostList,List<HostHA> hostListHA){
		NodeList list = new NodeList();
		List<NodeEntity> nodeList = new ArrayList<NodeEntity>();
		if(hostListHA!=null&&hostListHA.size()>0){
			for(HostHA hostHa:hostListHA){
				if(hostHa!=null){
					String mainIp = hostHa.getMainIp();
					if(mainIp!=null&&!"".equals(mainIp)){
						if(hostList!=null&&hostList.size()>0){
							for(Host host : hostList){
								if(host != null){
									if(mainIp.equals(host.getHostIP())){
										host.setFloatIp(hostHa.getFloatIp());//将floatIp set进来。
									}
								}
							}
						}
					}
				}
			}
		}
		if(hostList!=null&&hostList.size()>0){
			for(Host host : hostList){
				if(host != null){
					NodeEntity nodeEntity = new NodeEntity();
					nodeEntity.setHostIp(host.getHostIP());
					nodeEntity.setPasswd(host.getPassword());
					nodeEntity.setDeployId(Long.parseLong(host.getDeployId()));
					nodeEntity.setFloatIp(host.getFloatIp());
					nodeEntity.setIsMain("master");
					nodeList.add(nodeEntity);
				}
			}
		}
		if(hostListHA!=null&&hostListHA.size()>0){
			for(HostHA hostHa:hostListHA){
				if(hostHa!=null&&hostHa.getDeployId()!=null){
					NodeEntity nodeEntity = new NodeEntity();
					nodeEntity.setHostIp(hostHa.getHostIP());
					nodeEntity.setPasswd(hostHa.getPassword());
					nodeEntity.setDeployId(Long.parseLong(hostHa.getDeployId()));
					nodeEntity.setFloatIp(hostHa.getFloatIp());
					nodeEntity.setIsMain("slave");
					nodeList.add(nodeEntity);
				}
			}
		}
		Collections.sort(nodeList);//根据IP排序
		list.setNodeList(nodeList);
		return list;
	}

	@Override
	public List<DeploySchemeInfo> queryDeploySchemeList(String dataCenterType,
			String clusterType) {
		List<DeploySchemeInfo> deploySchemeInfoList = new ArrayList<DeploySchemeInfo>();
		String retCode = "";
		String msg = "";
		String isPlatform = "";
		try {
			if(Constants.IPAAS_CLUSTER.equals(clusterType)||Constants.APAAS_CLUSTER.equals(clusterType)){
				isPlatform = "0";
			}else if(Constants.PAAS_PLATFORM.equals(clusterType)){
				isPlatform = "1";
			}else{
				logger.error("集群类型错误：clusterType=" + clusterType);
				throw new PaasWebException(Constants.QUERY_DEPLOY_SCHEME_LIST_ERROR,"集群类型错误：clusterType=" + clusterType);
			}
			String url = RestUtils.monitorOperationRestUrl("queryDeploySchemeListUrl", dataCenterType, isPlatform);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = hostDao.get(url, SchemeList.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			SchemeList schemeList = (SchemeList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(schemeList != null && schemeList.getSchemeList() != null){
					for(SchemeEntity schemeEntity : schemeList.getSchemeList()){
						DeploySchemeInfo deploySchemeInfo = translateToDeploySchemeInfo(schemeEntity);
						deploySchemeInfoList.add(deploySchemeInfo);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("根据集群类型查询组件列表错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_DEPLOY_SCHEME_LIST_ERROR,ex.getMessage());
			}
		}
		return deploySchemeInfoList;
	}
	
	//把监控中心部署方案实体转化为前台bean
	private DeploySchemeInfo translateToDeploySchemeInfo(SchemeEntity schemeEntity){
		DeploySchemeInfo deploySchemeInfo = new DeploySchemeInfo();
		deploySchemeInfo.setId(schemeEntity.getId()+"");
		deploySchemeInfo.setName(schemeEntity.getSchemeName());
		deploySchemeInfo.setDescription(schemeEntity.getDescription());
		return deploySchemeInfo;
	}

	@Override
	public List<DeployComponentInfo> queryDeployComponentList(String parameter,
			String flag) {
		List<DeployComponentInfo> deployComponentInfoList = new ArrayList<DeployComponentInfo>();
		String retCode = "";
		String msg = "";
		String url = "";
		try {
			if("0".equals(flag)){//node主机，parameter表示集群类型
				url = RestUtils.monitorOperationRestUrl("queryDeployComponentListForNodeUrl", parameter);
			}else{//paas平台主机，parameter表示部署方案id
				url = RestUtils.monitorOperationRestUrl("queryDeployComponentListForPaasUrl", parameter);
			}
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = hostDao.get(url, DeployList.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			DeployList deployList = (DeployList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(deployList != null && deployList.getDeploylist() != null){
					for(DeployEntity deployEntity : deployList.getDeploylist()){
						DeployComponentInfo deployComponentInfo = translateToDeployComponentInfo(deployEntity);
						deployComponentInfoList.add(deployComponentInfo);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				if("0".equals(flag)){
					logger.error("根据集群类型查询部署组件列表错误",ex);
					throw new PaasWebException(Constants.QUERY_DEPLOY_COMPONENT_LIST_FOR_NODE_ERROR,ex.getMessage());
				}else{
					logger.error("根据部署方案id查询部署组件列表错误",ex);
					throw new PaasWebException(Constants.QUERY_DEPLOY_COMPONENT_LIST_FOR_PAAS_ERROR,ex.getMessage());
				}
			}
		}
		return deployComponentInfoList;
	}
	
	//把监控中心实体转化为前台bean
	private DeployComponentInfo translateToDeployComponentInfo(DeployEntity deployEntity){
		DeployComponentInfo deployComponentInfo = new DeployComponentInfo();
		deployComponentInfo.setId(deployEntity.getId()+"");
		deployComponentInfo.setComponents(deployEntity.getComponents());
		deployComponentInfo.setDescription(deployEntity.getDescription());
		deployComponentInfo.setHainfo(deployEntity.getHainfo());
		return deployComponentInfo;
	}

	@Override
	public String queryHostAndCompDetails(String nodeIp, String compName) {
		String str = "";
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl("queryHostAndCompDetailsUrl", nodeIp,compName);
			logger.info("开始调用Rest接口：" + url);
			BufferedReader br= hostDao.get(url);
			String readLane=null;
			String readData = "";
			while((readLane=br.readLine())!=null){
				readData+=readLane;
			}
			JSONObject jsonObject = JSONObject.fromObject(readData);
			ResponseInfo responseInfo = (ResponseInfo) JSONObject.toBean(
					jsonObject, ResponseInfo.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				str = (String)responseInfo.getData();
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询主机，组件操作详情",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_HOST_AND_COMP_DETAILS_ERROR,ex.getMessage());
			}
		}
		return str;
	}

}
