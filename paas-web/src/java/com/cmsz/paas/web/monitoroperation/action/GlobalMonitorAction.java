package com.cmsz.paas.web.monitoroperation.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.monitor.entity.GlobalMonitorEntity;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.monitoroperation.service.GlobalMonitorService;
/**
 * 全局监控Action.
 * 
 * @author jiayz
 */
@UnLogging
public class GlobalMonitorAction extends AbstractAction{
	private static final Logger logger = LoggerFactory
			.getLogger(GlobalMonitorAction.class);
	@Autowired
	private GlobalMonitorService globalMonitorService;
	
	private String dataCenterId;
	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	private String clusterId;
	private String nodeId;

	/**
	 * 查询全局监控
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void queryGlobalMonitorList() throws Exception {
		Page<GlobalMonitorEntity> page = this.getJqGridPage("dataCentername");
		logger.info("开始执行查询全局监控，dataCenterId：" + dataCenterId + "clusterId：" + clusterId
				+ "nodeId" + nodeId);
		try {
			List<GlobalMonitorEntity> gList = globalMonitorService
					.queryGlobalMonitorList(dataCenterId,clusterId,nodeId);
			page.setResult(gList);
			page.setTotalCount(gList.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询全局监控成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询全局监控出错", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}
}
