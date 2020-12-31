package com.cmsz.paas.web.newmonitor.service.impl;

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
import com.cmsz.paas.web.newmonitor.dao.NewHostDao;
import com.cmsz.paas.web.newmonitor.model.NewHost;
import com.cmsz.paas.web.newmonitor.service.NewHostService;

@Service("newHostService")
public class NewHostServiceImpl implements NewHostService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(NewHostServiceImpl.class);
	
	@Autowired
	private NewHostDao newHostDao;

	@Override
	public List<NewHost> queryHostList(String clusterId) {
		List<NewHost> list = new ArrayList<NewHost>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryNewHostListUrl", clusterId);
			logger.info("开始调用Rest接口：" + url);
			
			ResponseInfo responseInfo = newHostDao.get(url, NodeList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			NodeList nodeList = (NodeList)responseInfo.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if( nodeList != null && nodeList.getNodeList() != null){
					for(NodeEntity nodeEntity : nodeList.getNodeList()){
						NewHost host = translateToHost(nodeEntity);
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
	private NewHost translateToHost(NodeEntity nodeEntity) {
		NewHost host = new NewHost();
		host.setId(nodeEntity.getId()+"");
		host.setHostIP(nodeEntity.getHostIp());
		host.setDeployId(nodeEntity.getDeployId()+"");
		host.setPassword(nodeEntity.getPasswd());
		host.setStatus(nodeEntity.getStatus());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nodeEntity.getInsertTime());
		host.setCreateTime(dateStr);
		return host;
	}


}
