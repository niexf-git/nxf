package com.cmsz.paas.web.newmonitor.service.impl;

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
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.cmsz.paas.web.newmonitor.dao.NewClusterDao;
import com.cmsz.paas.web.newmonitor.model.NewCluster;
import com.cmsz.paas.web.newmonitor.service.NewClusterService;

@Service("newClusterService")
public class NewClusterServiceImpl implements NewClusterService {

	private static final Logger logger = LoggerFactory.getLogger(NewClusterServiceImpl.class);

	@Autowired
	private NewClusterDao clusterDao;
	

	private String retCode = ""; // rest接口返回码
	private String msg = ""; // rest接口返回信息

	@UnLogging
	@Override
	public List<NewCluster> queryClusterList(String dataCenterId,String appIds, String operType) throws PaasWebException {
		// 接收监控运维返回转换数据
		List<NewCluster> clusterList = new ArrayList<NewCluster>();
		ClusterList listJson = new ClusterList(); // 接收rest接口返回数据

		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform("queryNewClusterListUrl", dataCenterId,appIds,operType);
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
	private NewCluster transformCluster(ClusterEntity clusterEntity) {

		NewCluster cluster = new NewCluster();

		cluster.setId(clusterEntity.getId() + "");
		cluster.setName(clusterEntity.getName());
		cluster.setLabel(clusterEntity.getLabel());
		cluster.setType(clusterEntity.getType() + "");
		cluster.setDescription(clusterEntity.getDescription());
		
		// 由Date类型转换为String
		cluster.setInsertTime(DateUtil.tranformDate(clusterEntity.getInsertTime().toString()));

		return cluster;
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


}
