package com.cmsz.paas.web.monitoroperation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.monitor.entity.ClusterEntity;
import com.cmsz.paas.common.model.monitor.response.ClusterList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.dao.Cluster4ControlDao;
import com.cmsz.paas.web.monitoroperation.dao.ClusterDao;
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.cmsz.paas.web.monitoroperation.service.ClusterService;

@Service("clusterService")
public class ClusterServiceImpl implements ClusterService {

	private static final Logger logger = LoggerFactory.getLogger(ClusterServiceImpl.class);

	@Autowired
	private ClusterDao clusterDao;
	
	@Autowired
	private Cluster4ControlDao cluster4ControlDao;

	private String retCode = ""; // rest接口返回码
	private String msg = ""; // rest接口返回信息

	@UnLogging
	@Override
	public List<Cluster> queryClusterList(String dataCenterId) throws PaasWebException {
		// 接收监控运维返回转换数据
		List<Cluster> clusterList = new ArrayList<Cluster>();
		ClusterList listJson = new ClusterList(); // 接收rest接口返回数据

		// 拼接接口请求地址
		String url = RestUtils.monitorOperationRestUrl("queryClusterListUrl", dataCenterId);

		try {
			logger.debug("开始调用查询集群列表rest接口：" + url);
			ResponseInfo responseInfo = clusterDao.get(url, ClusterList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			listJson = (ClusterList) responseInfo.getData();

			logger.debug("调用查询集群列表rest接口返回码：" + retCode + ", 返回信息：" + msg);

			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!listJson.equals(null)) {
					// if (null != listJson || !"".equals(listJson)) {
					for (ClusterEntity clusterEntity : listJson.getClusterList()) {
						clusterList.add(transformCluster(clusterEntity));
					}
				}
				logger.info("调用查询集群列表rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询集群出错", ex);
				throw new PaasWebException(retCode, msg);
			}
			else {
				logger.error("[" + Constants.QUERY_CLUSTERS_LIST_ERROR + "]查询集群出错", ex);
				throw new PaasWebException(Constants.QUERY_CLUSTERS_LIST_ERROR, ex.getMessage());
			}
		}
		return clusterList;
	}

	/**
	 * 监控运维返回数据转换成前台所需数据
	 * 
	 * @param privateImageEntity
	 * @return
	 */
	private Cluster transformCluster(ClusterEntity clusterEntity) {

		Cluster cluster = new Cluster();

		cluster.setId(clusterEntity.getId() + "");
		cluster.setName(clusterEntity.getName());
		cluster.setLabel(clusterEntity.getLabel());
		cluster.setType(clusterEntity.getType() + "");
		cluster.setDescription(clusterEntity.getDescription());
		
		// 由Date类型转换为String
		cluster.setInsertTime(DateUtil.tranformDate(clusterEntity.getInsertTime().toString()));

		return cluster;
	}
	
	/**
	 * Service层新增集群
	 * @param dataCenter
	 * @throws PaasWebException
	 */
	@Override
	public void createCluster(Cluster cluster) throws PaasWebException {
		try {
			ClusterEntity entity = new ClusterEntity();
			entity.setDataCenterId(Long.parseLong(cluster.getDataCenterId()));
			entity.setName(cluster.getName());
			entity.setType(Integer.parseInt(cluster.getType()));
			entity.setLabel(cluster.getLabel());
			entity.setDescription(cluster.getDescription());
			
			//组装访问监控运维的链接，以及参数
			String url = RestUtils.monitorOperationRestUrl("createClusterUrl");
			logger.debug("Rest url:"+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = clusterDao.create(url, entity, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("Rest responseInfo:"+retCode+msg);
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("新增集群异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("新增集群异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.CREATE_CLUSTERS_ERROR, ex.getMessage());
			}
		}
	}
	
	/**
	 * Service层删除集群
	 */
	@Override
	public void deleteCluster(String dataCenterId, String clusterId) throws PaasWebException {
		try {
			//组装访问监控运维的链接，以及参数
//			String url = RestUtils.monitorOperationRestUrl("deleteClusterUrl", dataCenterId, clusterId);
			String url = RestUtils.monitorOperationRestUrl("deleteClusterUrl", clusterId);
			logger.debug("删除集群rest接口：" + url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = clusterDao.delete(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("删除集群rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("删除集群异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("删除集群异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_CLUSTERS_ERROR, ex.getMessage());
			}
		}
	}
	
	/**
	 * 检查集群中是否有应用，有的话则不能删除集群
	 * @param clusterId
	 * @return
	 */
	@UnLogging
	public void checkAppsInCluster(String clusterId) throws PaasWebException {
//		boolean isOr = true;
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("checkAppsInClusterUrl", clusterId);
			
			logger.debug("检查集群中是否有应用rest接口：" + url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = cluster4ControlDao.get(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("检查集群中是否有应用rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
//				isOr = false;
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("检查集群中是否有应用异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_CLUSTERS_ERROR, ex.getMessage());
			}
		}
//		return isOr;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 修改集群描述字段并保存
	 */
	@Override
	public void updateCluster(Cluster cluster) throws PaasWebException {
		try {
			ClusterEntity entity = new ClusterEntity();
			entity.setId(Long.parseLong(cluster.getId()));
			entity.setDescription(cluster.getDescription());
			
			String url = RestUtils.monitorOperationRestUrl("updateClusterDescUrl", cluster.getId());
			logger.debug("修改集群信息rest接口："+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = clusterDao.update(url, entity, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("修改集群信息rest接口返回码："+retCode+", 返回信息："+msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("修改集群信息异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改集群信息异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_CLUSTERS_DESC_ERROR, ex.getMessage());
			}
		}
	}

}
