package com.cmsz.paas.web.newmonitor.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.newmonitor.model.NewCluster;
import com.cmsz.paas.web.newmonitor.service.NewClusterService;

/**
 * @author lin.my
 * @version 创建时间：2016年12月20日 上午11:40:40
 */
public class NewClusterAction extends AbstractAction {

	private static final Logger logger = LoggerFactory.getLogger(NewClusterAction.class);

	@Autowired
	private NewClusterService clusterService;

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
	@UnLogging
	@SuppressWarnings("unchecked")
	public void queryClusterList() throws Exception {

		logger.info("开始执行查询集群");
		Page<NewCluster> page = this.getJqGridPage("id");
		try {
			String appIds = getSessionMap().get("appPermissionId").toString();
			String operType = getSessionMap().get("opertype").toString();
			List<NewCluster> list = clusterService.queryClusterList(dataCenterId,appIds,operType);

			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询集群列表完成，返回条数：" + page.getTotalCount());
		} catch (PaasWebException ex) {
			logger.error("查询集群异常", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
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
