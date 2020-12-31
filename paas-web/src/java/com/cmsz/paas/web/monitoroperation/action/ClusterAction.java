package com.cmsz.paas.web.monitoroperation.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.UrlEscapeUtil;
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.cmsz.paas.web.monitoroperation.service.ClusterService;

/**
 * @author lin.my
 * @version 创建时间：2016年12月20日 上午11:40:40
 */
public class ClusterAction extends AbstractAction {

	private static final Logger logger = LoggerFactory.getLogger(ClusterAction.class);

	@Autowired
	private ClusterService clusterService;

	private String id; // 集群Id

	private String name; // 集群名称

	private String label; // 集群label

	private String description;

	private String type; // 集群类型，包括1.ipaas集群，2.apaas集群，3.paas平台（3.副中心，其它：主中心）
	
	private String dataCenterId = ""; //点击集群查询所需参数：数据中心Id
	
	private String insertTime; // 创建时间

	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void queryClusterList() throws Exception {

		logger.info("开始执行查询集群");
		Page<Cluster> page = this.getJqGridPage("id");
		try {
			List<Cluster> list = clusterService.queryClusterList(dataCenterId);

			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询集群列表完成，返回条数：" + page.getTotalCount());
		} catch (PaasWebException ex) {
			logger.error("查询集群异常", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 新增集群
	 */
	public void createCluster() throws Exception {

		logger.info("新增集群");
		
		Cluster cluster = new Cluster();
		cluster.setDataCenterId(dataCenterId);
		cluster.setName(name);
		cluster.setType(type);
		cluster.setLabel(label);
		cluster.setDescription(description);

		try {
			clusterService.createCluster(cluster);
			logger.info("新增集群：" + cluster.toString());
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("新增集群异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	/**
	 * 删除集群
	 * @throws Exception
	 */
	public void deleteCluster() throws Exception {
		try {
			logger.info("删除集群，id："+id);
			
			// 如果是PaaS集群（即type为3），则不需要调用控制中心接口，
			// 如果是iPaaS和aPaaS集群，则需要调用控制中心接口
			// 接口：集群下是否有应用
//			返回成功就是可以删除
//			报错就是不能
			if(!"3".equals(type)){
				clusterService.checkAppsInCluster(id);
//				boolean isOr = clusterService.checkAppsInCluster(id);
//				if(isOr == false){
//					sendFailReslutl("该集群中有应用,不能删除");
//					return;
//				}
			}
			clusterService.deleteCluster(dataCenterId, id);
			sendSuccessReslutl();
			
		} catch (PaasWebException ex) {
			logger.error("删除集群异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 修改集群描述字段并保存
	 * @throws Exception
	 */
	public void updateDescription() throws Exception {
		try {
			logger.info("查询集群信息，id：" + id);
			
//			// 1、根据ID查询集群
//			cluster = clusterService.queryClusterById(id);
//			logger.info("查询数据中心信息，查询结果：" + cluster.toString());
//			
			Cluster cluster = new Cluster();
			cluster.setId(id);
			
			// 2、修改集群描述信息
			cluster.setDescription(description);
			
			// 3、更新集群实体
			clusterService.updateCluster(cluster);
			logger.info("修改集群描述信息成功");
			
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("修改集群描述信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

}
